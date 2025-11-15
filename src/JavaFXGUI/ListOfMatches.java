package src.JavaFXGUI;

import src.OOPBackEnd.Matches;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        this.MatchesList = new ArrayList<>();
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
