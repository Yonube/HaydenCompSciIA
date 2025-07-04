package src.OOPBackEnd;

public class RobotTeam {
    // Statics
    public static RobotTeam[] AllTeams = new RobotTeam[90];

    private int TeamNumber;
    private String TeamName;

    private int Wins;
    private int Losses;
    private int Draws;
    private int TotalMatchesPlayed;
    // Numerical Performance In Matches
    private int[] TotalPointsInEachMatch;
    private int[] TotalCoralPointsInEachMatch;
    private int[] TotalAlgaePointsInEachMatch;

    private int Breakdowns;
    private boolean[] MissedMatches;
    private int StuckGamePieces;
    private int TotalCoralPoints;
    private int TotalAlgaePoints;
    private int TotalPoints;

    // Booleans
    private boolean HasAuton;
    private boolean HasTeleop;
    private boolean CanDeepClimb;
    private boolean CanShallowClimb;
    private boolean CanRemoveAlgae;
    private boolean CanDefend;

    // Other Things that may be useful
    private String[] Notes;
    // private Image TeamImage;

    public RobotTeam(int TeamNumber, String TeamName) {
        this.TeamNumber = TeamNumber;
        this.TeamName = TeamName;
        this.Notes = new String[12]; // Assuming 12 matches
        // Initialize arrays
        this.TotalPointsInEachMatch = new int[90];
        this.TotalCoralPointsInEachMatch = new int[90];
        this.TotalAlgaePointsInEachMatch = new int[90];
        this.MissedMatches = new boolean[90];

        for (int i = 0; i < AllTeams.length; i++) {
            if (AllTeams[i] == null) { // Assuming 0 indicates an empty index
                AllTeams[i] = this; // Assign the team number to the first empty index
                break; // Exit the loop after filling the first empty index
            }
        }
        // Constructor for RobotTeam
    }

    // Mutators and Accessors
    public int getTeamNumber() {
        return TeamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        TeamNumber = teamNumber;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public int getWins() {
        return Wins;
    }

    public void setWins(int wins) {
        Wins = wins;
    }

    public int getLosses() {
        return Losses;
    }

    public void setLosses(int losses) {
        Losses = losses;
    }

    public int getDraws() {
        return Draws;
    }

    public void setDraws(int draws) {
        Draws = draws;
    }

    public int getTotalMatchesPlayed() {
        return TotalMatchesPlayed;
    }

    public void setTotalMatchesPlayed(int totalMatchesPlayed) {
        TotalMatchesPlayed = totalMatchesPlayed;
    }

    public int getTotalCoralPoints() {
        int total = 0;
        for (int i = 0; i < TotalCoralPointsInEachMatch.length; i++) {
            total += TotalCoralPointsInEachMatch[i];
        }
        return total;
    }

    public void setTotalCoralPoints(int totalCoralPoints) {
        TotalCoralPoints = totalCoralPoints;
    }

    public int getTotalAlgaePoints() {
        int total = 0;
        for (int i = 0; i < TotalAlgaePointsInEachMatch.length; i++) {
            total += TotalAlgaePointsInEachMatch[i];
        }
        return total;
    }

    public void setTotalAlgaePoints(int totalAlgaePoints) {
        TotalAlgaePoints = totalAlgaePoints;
    }

    public int getTotalPoints() {
        int total = 0;
        for (int i = 0; i < TotalPointsInEachMatch.length; i++) {
            total += TotalPointsInEachMatch[i];
        }
        return total;
    }

    public void setTotalPoints(int totalPoints) {
        TotalPoints = totalPoints;
    }

    public int getBreakdowns() {
        return Breakdowns;
    }

    public void setBreakdowns(int breakdowns) {
        Breakdowns = breakdowns;
    }

    public int getStuckGamePieces() {
        return StuckGamePieces;
    }

    public void setStuckGamePieces(int stuckGamePieces) {
        StuckGamePieces = stuckGamePieces;
    }

    public int getTotalCoralPointsInMatch(int matchNumber) {
        return TotalCoralPointsInEachMatch[matchNumber];
    }

    public void setTotalCoralPointsInMatch(int matchNumber, int points) {
        TotalCoralPointsInEachMatch[matchNumber] = points;
    }

    public int getTotalAlgaePointsInMatch(int matchNumber) {
        return TotalAlgaePointsInEachMatch[matchNumber];
    }

    public void setTotalAlgaePointsInMatch(int matchNumber, int points) {
        TotalAlgaePointsInEachMatch[matchNumber] = points;
    }

    public int getTotalPointsInMatch(int matchNumber) {
        return TotalPointsInEachMatch[matchNumber];
    }

    public void setTotalPointsInMatch(int matchNumber, int points) {
        TotalPointsInEachMatch[matchNumber] = points;
    }

    public boolean[] getMissedMatches() {
        return MissedMatches;
    }

    public void setMissedMatches(boolean[] missedMatches) {
        MissedMatches = missedMatches;
    }

    public RobotTeam[] getAllTeams() {
        return AllTeams;
    }

    public boolean hasAuton() {
        return HasAuton;
    }

    public void setHasAuton(boolean hasAuton) {
        HasAuton = hasAuton;
    }

    public boolean hasTeleop() {
        return HasTeleop;
    }

    public void setHasTeleop(boolean hasTeleop) {
        HasTeleop = hasTeleop;
    }

    public boolean canDeepClimb() {
        return CanDeepClimb;
    }

    public void setCanDeepClimb(boolean canDeepClimb) {
        CanDeepClimb = canDeepClimb;
    }

    public boolean canShallowClimb() {
        return CanShallowClimb;
    }

    public void setCanShallowClimb(boolean canShallowClimb) {
        CanShallowClimb = canShallowClimb;
    }

    public boolean canRemoveAlgae() {
        return CanRemoveAlgae;
    }

    public void setCanRemoveAlgae(boolean canRemoveAlgae) {
        CanRemoveAlgae = canRemoveAlgae;
    }

    public boolean canDefend() {
        return CanDefend;
    }

    public void setCanDefend(boolean canDefend) {
        CanDefend = canDefend;
    }

    public String[] getNotes() {
        return Notes;
    }

    public void setNotes(String notes, int matchNumber) {
        for (int i = 0; i < Notes.length; i++) {
            if (Notes[i] == null && notes != null) {
                Notes[i] = "Match " + matchNumber + " Notes: " + notes;
                break;
            }
        }
    }

    public void checkNotes() {
        for (int i = 0; i < Notes.length; i++) {
            if (Notes[0] == null) {
                System.out.println("No notes available.");
                break;
            } else if (Notes[i] != null) {
                System.out.println(Notes[i]);
            }
        }

    }

    public static String[] getAllTeamNames() {
				String[] teamNames = new String[AllTeams.length];
                int index = 0;
                for (RobotTeam team : AllTeams) { // Assuming AllTeams is a collection of RobotTeam objects
                    if (team != null) { // Ensure the team is not null
                        teamNames[index++] = team.getTeamName(); // Assign the team name to the array
                    }
                }
				return teamNames;
			}

    // Action Commands
    public void addWin() {
        Wins++;
        TotalMatchesPlayed++;
    }

    public void addLoss() {
        Losses++;
        TotalMatchesPlayed++;
    }

    public void addDraw() {
        Draws++;
        TotalMatchesPlayed++;
    }

    public void addBreakdown() {
        Breakdowns++;
    }

    public void addStuckGamePiece() {
        StuckGamePieces++;
    }

    public void addAuton() {
        HasAuton = true;
    }

    public void addTeleop() {
        HasTeleop = true;
    }

    public void addDeepClimb() {
        CanDeepClimb = true;
    }

    public void addShallowClimb() {
        CanShallowClimb = true;
    }

    public void addRemoveAlgae() {
        CanRemoveAlgae = true;
    }

    public void addTotalCoralPointsInMatch(int matchNumber, int points) {
        TotalCoralPointsInEachMatch[matchNumber] = points;
    }

    public void addTotalAlgaePointsInMatch(int matchNumber, int points) {
        TotalAlgaePointsInEachMatch[matchNumber] = points;
    }

    public void addTotalPointsInMatch(int matchNumber, int points) {
        TotalPointsInEachMatch[matchNumber] = points;
    }

    public void displayTeamInfo() {
        System.out.println("Team Number: " + getTeamNumber());
        System.out.println("Team Name: " + getTeamName());
        System.out.println("Wins: " + getWins());
        System.out.println("Losses: " + getLosses());
        System.out.println("Draws: " + getDraws());
        System.out.println("Total Matches Played: " + getTotalMatchesPlayed());
        System.out.println("Total Points: " + getTotalPoints());
        System.out.println("Total Coral Points: " + getTotalCoralPoints());
        System.out.println("Total Algae Points: " + getTotalAlgaePoints());
        System.out.println("Breakdowns: " + getBreakdowns());
        System.out.println("Stuck Game Pieces: " + getStuckGamePieces());
    }
    //

    // this. = Boolean.parseBoolean(ToBeSortedData[4]);

}

// Common Starting Position