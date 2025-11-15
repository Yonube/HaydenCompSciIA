package src.JavaFXGUI;

import src.OOPBackEnd.RobotTeam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListOfRobotTeams implements Serializable {
    private static final long serialVersionUID = 1L; // Ensure compatibility during serialization
    public static final String fileName = "robotTeam.data";
    private List<RobotTeam> robotTeams;

    public ListOfRobotTeams() {
        try {
            ListOfRobotTeams deserializedData = deserialize();
            if (deserializedData != null) {
                this.robotTeams = deserializedData.robotTeams;
                for(RobotTeam rt : this.robotTeams){
                    for (int i = 0; i < RobotTeam.AllTeams.length; i++) {
                        if (RobotTeam.AllTeams[i] == null) { // Assuming 0 indicates an empty index
                            RobotTeam.AllTeams[i] = rt; // Assign the team number to the first empty index
                            break; // Exit the loop after filling the first empty index
                        }
                    }
                    System.out.println("Pulled Team:" + rt.getTeamName());
                }
            } else {
                this.robotTeams = new ArrayList<>();
                System.out.println("Empty List");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No existing data found or error during deserialization. Starting with an empty list.");
            this.robotTeams = new ArrayList<>();
        }
        this.robotTeams = new ArrayList<>();
    }

    // Add a RobotTeam to the list
    public void addRobotTeam(RobotTeam team) {
        robotTeams.add(team);
    }

    // Get the list of RobotTeams
    public List<RobotTeam> getRobotTeams() {
        return robotTeams;
    }

    // Serialize all RobotTeams to a single file
    public void serialize() {
        for (RobotTeam team : RobotTeam.AllTeams) {
            if (team != null) {
                this.robotTeams.add(team);
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Serialized all RobotTeams to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error serializing RobotTeams to file: " + fileName);
            e.printStackTrace();
        }
    }

    // Deserialize all RobotTeams from a single file
    public static ListOfRobotTeams deserialize() throws IOException, ClassNotFoundException{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            ListOfRobotTeams list = (ListOfRobotTeams) ois.readObject();
            System.out.println("Deserialized all RobotTeams from file: " + fileName);
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing RobotTeams from file: " + fileName);
            e.printStackTrace();
            return null;
        }
    }
}
