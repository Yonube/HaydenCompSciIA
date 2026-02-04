package src.OOPBackEnd;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ListOfMatches implements Serializable {
    private static final long serialVersionUID = 2L; // Ensure compatibility during serialization
    public static final String fileName = "Matches.data";
    private List<Matches> MatchesList;

    public ListOfMatches() {
        try {
            ListOfMatches deserializedData = deserialize();
            if (deserializedData != null) {
                this.MatchesList = deserializedData.MatchesList;
                for(Matches m : this.MatchesList){
                    for (int i = 0; i < Matches.getAllMatches().length; i++) {
                        if (Matches.getAllMatches()[i] == null || (Matches.getAllMatches()[i].getBlue1() == null
                         && Matches.getAllMatches()[i].getBlue2() == null
                         && Matches.getAllMatches()[i].getBlue3() == null
                         && Matches.getAllMatches()[i].getRed1() == null 
                         && Matches.getAllMatches()[i].getRed2() == null 
                         && Matches.getAllMatches()[i].getRed3() == null)) { // Assuming 0 indicates an empty index
                            Matches.getAllMatches()[i] = m; // Assign the team number to the first empty index
                            break; // Exit the loop after filling the first empty index
                        }
                    }
                    System.out.println("Pulled Match " + m.getMatchNumber());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No existing data found or error during deserialization. Starting with an empty list.");
            this.MatchesList = new ArrayList<>();
        }
        // Ensure MatchesList is initialized even if deserialized data was malformed
        if (this.MatchesList == null) {
            this.MatchesList = new ArrayList<>();
        }
    }

    /**
     * Recompute wins/losses/draws and match scores for all teams from the
     * stored per-team per-match data. This method is idempotent.
     */
    public static void recomputeAllTeamRecords() {
        // Build a map from teamNumber -> stats [wins, losses, draws, matchesPlayed]
        Map<Integer, int[]> statsByNumber = new HashMap<>();
        // Also build canonical lookup teamNumber -> RobotTeam
        Map<Integer, RobotTeam> canonical = new HashMap<>();
        for (RobotTeam rt : RobotTeam.AllTeams) {
            if (rt != null) {
                canonical.put(rt.getTeamNumber(), rt);
                statsByNumber.put(rt.getTeamNumber(), new int[4]);
            }
        }

        // Iterate matches and compute outcomes (use team numbers so deserialized instances match)
        for (Matches m : Matches.getAllMatches()) {
            if (m == null) continue;
            int idx = m.getMatchNumber();

            RobotTeam[] blue = new RobotTeam[]{m.getBlue1(), m.getBlue2(), m.getBlue3()};
            RobotTeam[] red = new RobotTeam[]{m.getRed1(), m.getRed2(), m.getRed3()};

            // skip matches with no teams
            boolean any = false;
            for (RobotTeam t : blue) if (t != null) any = true;
            for (RobotTeam t : red) if (t != null) any = true;
            if (!any) continue;

            int blueTotal = 0;
            int redTotal = 0;

            // Sum using canonical instances where possible (fallback to match objects)
            for (RobotTeam t : blue) {
                if (t == null) continue;
                RobotTeam canon = canonical.get(t.getTeamNumber());
                try { blueTotal += (canon != null ? canon.getTotalPointsInMatch(idx) : t.getTotalPointsInMatch(idx)); } catch (Exception e) {}
            }
            for (RobotTeam t : red) {
                if (t == null) continue;
                RobotTeam canon = canonical.get(t.getTeamNumber());
                try { redTotal += (canon != null ? canon.getTotalPointsInMatch(idx) : t.getTotalPointsInMatch(idx)); } catch (Exception e) {}
            }

            // write back match scores
            m.setBlueScore(blueTotal);
            m.setRedScore(redTotal);

            // update matchesPlayed for participating team numbers
            for (RobotTeam t : blue) {
                if (t == null) continue;
                int tn = t.getTeamNumber();
                int[] s = statsByNumber.get(tn);
                if (s == null) { s = new int[4]; statsByNumber.put(tn, s); }
                s[3]++; // matchesPlayed
            }
            for (RobotTeam t : red) {
                if (t == null) continue;
                int tn = t.getTeamNumber();
                int[] s = statsByNumber.get(tn);
                if (s == null) { s = new int[4]; statsByNumber.put(tn, s); }
                s[3]++; // matchesPlayed
            }

            if (blueTotal > redTotal) {
                for (RobotTeam t : blue) if (t != null) statsByNumber.get(t.getTeamNumber())[0]++;
                for (RobotTeam t : red) if (t != null) statsByNumber.get(t.getTeamNumber())[1]++;
            } else if (redTotal > blueTotal) {
                for (RobotTeam t : red) if (t != null) statsByNumber.get(t.getTeamNumber())[0]++;
                for (RobotTeam t : blue) if (t != null) statsByNumber.get(t.getTeamNumber())[1]++;
            } else {
                for (RobotTeam t : blue) if (t != null) statsByNumber.get(t.getTeamNumber())[2]++;
                for (RobotTeam t : red) if (t != null) statsByNumber.get(t.getTeamNumber())[2]++;
            }

            // mark populated
            m.setIsPopulated(true);
        }

        // Apply stats back to canonical RobotTeam objects
        for (Map.Entry<Integer,int[]> entry : statsByNumber.entrySet()) {
            int tn = entry.getKey();
            int[] s = entry.getValue();
            RobotTeam rt = canonical.get(tn);
            if (rt != null) {
                rt.setWins(s[0]);
                rt.setLosses(s[1]);
                rt.setDraws(s[2]);
                rt.setTotalMatchesPlayed(s[3]);
            }
        }
    }

    

    // Add a Match to the list
    public void addMatch(Matches match) {
        MatchesList.add(match);
    }

    // Get the list of Matches
    public List<Matches> getMatchesList() {
        return MatchesList;
    }

    // Serialize all Matches to a single file
    public void serialize() {
        // Rebuild the MatchesList from the canonical Matches array to avoid duplicates
        this.MatchesList = new ArrayList<>();
        for (Matches match : Matches.getAllMatches()) {
            if (match != null) {
                this.MatchesList.add(match);
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Serialized all Matches to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error serializing Matches to file: " + fileName);
            e.printStackTrace();
        }
    }

    // Deserialize all Matches from a single file
    public static ListOfMatches deserialize() throws IOException, ClassNotFoundException{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            ListOfMatches list = (ListOfMatches) ois.readObject();
            System.out.println("Deserialized all Matches from file: " + fileName);
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing Matches from file: " + fileName);
            e.printStackTrace();
            return null;
        }
    }
}
