package src.OOPBackEnd;

import javafx.scene.robot.Robot;

public class Scanner {
    // Scanner class to read input from the QR Code
    // This class will handle the QR code scanning and data extraction
    // It will also handle the conversion of the scanned data into a format that can
    // be used by the RobotTeam class
    private static String[] scannedData = new String[35];

    private static int TeamNumber;
    private static String TeamName;

    private static int Wins;
    private static int Losses;
    private static int Draws;
    private static int TotalMatchesPlayed;
    private static int matchNumber;

    private static int Breakdown;
    private static boolean[] MissedMatches;
    private static int StuckGamePieces;
    private static int TotalCoralPoints;
    private static int TotalAlgaePoints;
    private static int TotalPoints;

    // Booleans
    private static boolean HasAuton;
    private static boolean HasTeleop;
    private static boolean CanDeepClimb;
    private static boolean CanShallowClimb;
    private static boolean CanRemoveAlgae;
    private static boolean CanDefend;

    public Scanner() {
        // Constructor for Scanner
        // Initialize the scanned data array
        scannedData = new String[35];
    }

    public static String[] getScannedData() {
        return scannedData;
    }

    public static void setScannedData(String[] scannedData) {
        Scanner.scannedData = scannedData;
    }

    public static int getTeamNumber() {
        return TeamNumber;
    }

    public static void QRdataToRobotTeam(String inputedddata) {
        Scanner.processScannedData(inputedddata);
        Scanner.sendAllDataToTeam(Scanner.determineRobotTeam(Scanner.getTeamNumber()));
    }

    public static void processScannedData(String inputFromQR) {
        // Process the scanned data and update the robot's state
        // For example, you might want to parse the scanned data and update the robot's
        // attributes
        System.out.println("Processing scanned data: ");
        // Split the scanned data into an array
        Scanner.setScannedData(inputFromQR.split("\t"));
        System.out.println("Split data");
        // Get match number
        matchNumber = Integer.parseInt(getScannedData()[1]);
        System.out.println(" Got Match Number");

        // Team Number
        TeamNumber = Integer.parseInt(getScannedData()[3]);
        System.out.println("Got Team Number");

        // If No Show Adds to Missed Matches
        // if (getScannedData()[5].equals("true")) {
        // MissedMatches[matchNumber] = true;
        // }
        // Coral Points
        TotalCoralPoints =
                // Auton Coral L1
                Integer.parseInt(getScannedData()[9]) +
                // Auton Coral L2
                        Integer.parseInt(getScannedData()[10] +
                        // Auton Coral L3
                                Integer.parseInt(getScannedData()[11]) +
                                // Auton Coral L4
                                Integer.parseInt(getScannedData()[12]))
                        +
                        // Teleop Coral L1
                        Integer.parseInt(getScannedData()[19]) +
                        // Teleop Coral L2
                        Integer.parseInt(getScannedData()[20]) +
                        // Teleop Coral L3
                        Integer.parseInt(getScannedData()[21]) +
                        // Teleop Coral L4
                        Integer.parseInt(getScannedData()[22]);
        System.out.println("Got Coral Points");
        // Algae Points
        TotalAlgaePoints =
                // Auton Algae Barge
                Integer.parseInt(getScannedData()[13]) +
                // Auton Algae Processor
                        Integer.parseInt(getScannedData()[14] +
                        // Teleop Algae Barge
                                Integer.parseInt(getScannedData()[23]) +
                                // Teleop Algae Processor
                                Integer.parseInt(getScannedData()[24]));
        System.out.println("Got Algae Points");

        // Total Points
        TotalPoints = TotalCoralPoints + TotalAlgaePoints;
        System.out.println("Got Total Points");

    }

    public static void sendAllDataToTeam(RobotTeam team) {
        // Send all the data to the team
        team.addTotalPointsInMatch(Scanner.matchNumber, Scanner.TotalPoints);
        team.addTotalCoralPointsInMatch(Scanner.matchNumber, Scanner.TotalCoralPoints);
        team.addTotalAlgaePointsInMatch(Scanner.matchNumber, Scanner.TotalAlgaePoints);
    }

    /**
     * @param teamNumber
     * @return RobotTeam
     */

    public static RobotTeam determineRobotTeam(int teamNumber) {
        // Determine the robot team based on the team number
        for (int i = 0; i < RobotTeam.AllTeams.length; i++) {
            if (RobotTeam.AllTeams[i] != null) {
                if (RobotTeam.AllTeams[i].getTeamNumber() == teamNumber) {
                    System.out.println(
                            "Team " + RobotTeam.AllTeams[i].getTeamName() + " found in the directory at index " + i);
                    return RobotTeam.AllTeams[i];
                }
            }
        }
        System.out.println("Team not found in the directory.");
        return null;
    }
    // If no matching team is found, return null or create a new RobotTeam
    // For example, you might want to create a new RobotTeam object and add it to
    // the directory
    // RobotTeam newTeam = new RobotTeam(teamNumber, teamName);

    // Replace with appropriate fallback logic if needed

    // if the robot team is not in the directory, then create a new one

}
