package src;
import src.JavaFXGUI.Mainapp;
import src.OOPBackEnd.ListOfMatches;
import src.OOPBackEnd.ListOfRobotTeams;
import src.OOPBackEnd.Matches;
import src.OOPBackEnd.Scanner;


public class Main {
    public static ListOfRobotTeams rtList;
    public static ListOfMatches mList;
    public static void main(String[] args) {
        // Initialize Scanner
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        // Deserialize All RobotTeams
        rtList = new ListOfRobotTeams();


        // Initialize All Matches
        for (int i = 1; i < Matches.getAllMatches().length; i++) {
            Matches.getAllMatches()[i] = new Matches(i, null, null, null, 0, null, null, null, 0); // Initialize with null values
        }
        mList = new ListOfMatches();
    // Recompute team records (wins/losses/draws) from stored matches so UI shows correct values
    ListOfMatches.recomputeAllTeamRecords();
        // Create instances of RobotTeam

        // RobotTeam Vector = new RobotTeam(8177, "Vector");
        // RobotTeam Robonauts = new RobotTeam(118, "Robonauts");
        // RobotTeam TexasTorque = new RobotTeam(1477, "TexasTorque");
        // RobotTeam Valor = new RobotTeam(6800, "Valor");
        // RobotTeam AwtyBots = new RobotTeam(5829, "AwtyBots");
        // RobotTeam BlargleFish = new RobotTeam(6369, "BlargleFish");
        // Removed unused variable Orbit

        // AwtyBots.displayTeamInfo();
        // Create instances of Matches
        // Removed unused variable match1
        @SuppressWarnings("unused")
        Mainapp MainGUI = new Mainapp();
        //AwtyBots.displayTeamInfo();

        Scanner.determineRobotTeam(5829);
        Scanner.determineRobotTeam(2389);

        while (true) {
            System.out.println("Input Data From QR Scout (type 'clear' to exit): ");
            String input = scanner.hasNextLine() ? scanner.nextLine() : "clear";
            if (input.equalsIgnoreCase("clear")) {
                System.out.println("Exiting the program.");
                System.out.println("Thank you for using the Robotics Scouter App!");
                java.awt.Window[] windows = java.awt.Window.getWindows();
                for (java.awt.Window window : windows) {
                    window.dispose();
                }
                break;
            }
            try {
            Scanner.QRdataToRobotTeam(input, scanner);
           // AwtyBots.displayTeamInfo();
           // AwtyBots.checkNotes();
            } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            continue;
            }
        }
        
        try {
            rtList.serialize();
            mList.serialize();
            System.out.println("All is good with serializing");
        } catch (Exception e) {
            System.err.println("An error occurred while saving ListOfRobotTeams: " + e.getMessage());
        }
    }
}