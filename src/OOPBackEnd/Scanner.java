package src.OOPBackEnd;

import src.OOPBackEnd.ConstantsForScanner;

public class Scanner {
    // Scanner class to read input from the QR Code
    // This class will handle the QR code scanning and data extraction
    // It will also handle the conversion of the scanned data into a format that can
    // be used by the RobotTeam class
    private static String[] scannedData = new String[35];

    private static int TeamNumber;

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

    // Extras
    private static String comments;

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
        if (Scanner.checkIfMatchDataExists(Scanner.getScannedData()[ConstantsForScanner.getMatchNumber()])) {
            Scanner.sendAllDataToTeam(Scanner.determineRobotTeam(Scanner.getTeamNumber()));
            Scanner.clear();
        }
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
                Integer.parseInt(getScannedData()[ConstantsForScanner.getAutonCoralL1()]) +
                // Auton Coral L2
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getAutonCoralL2()]) +
                        // Auton Coral L3
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getAutonCoralL3()]) +
                        // Auton Coral L4
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getAutonCoralL4()]) +
                        // Teleop Coral L1
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getTeleopCoralL1()]) +
                        // Teleop Coral L2
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getTeleopCoralL2()]) +
                        // Teleop Coral L3
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getTeleopCoralL3()]) +
                        // Teleop Coral L4
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getTeleopCoralL4()]);
        System.out.println("Got Coral Points: " + TotalCoralPoints);
        // Algae Points
        TotalAlgaePoints =
                // Auton Algae Barge
                Integer.parseInt(getScannedData()[ConstantsForScanner.getAutonAlgaeBarge()]) +
                // Auton Algae Processor
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getAutonAlgaeProcessor()]) +
                        // Teleop Algae Barge
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getTeleopAlgaeBarge()]) +
                        // Teleop Algae Processor
                        Integer.parseInt(getScannedData()[ConstantsForScanner.getTeleopAlgaeProcessor()]);
        System.out.println("Got Algae Points: " + TotalAlgaePoints);
        // Total Points
        TotalPoints = TotalCoralPoints + TotalAlgaePoints;
        System.out.println("Got Total Points: " + TotalPoints);
        // Can Remove Algae
        if (getScannedData()[ConstantsForScanner.getIntentionallyRemovedAlgaeAuto()].equals("true") ||
                getScannedData()[ConstantsForScanner.getIntentionallyRemovedAlgaeTeleop()].equals("true")) {
            CanRemoveAlgae = true;
        }
        // Auton
        if (getScannedData()[ConstantsForScanner.getMoved()] == "true") {
            HasAuton = true;
        }
        // Comments
        if (getScannedData().length < 35) {
            System.out.println("Error: Scanned data is incomplete or no comments provided.");
            return;
        } else {
            if (getScannedData()[ConstantsForScanner.getComments()] != null) {
                comments = getScannedData()[ConstantsForScanner.getComments()];
                System.out.println("Got Comments: " + comments);
            }
        }

    }

    public static void sendAllDataToTeam(RobotTeam team) {
        // Send all the data to the team
        team.addTotalPointsInMatch(Scanner.matchNumber, Scanner.TotalPoints);
        System.out.println("Got Total Points: " + TotalPoints);

        team.addTotalCoralPointsInMatch(Scanner.matchNumber, Scanner.TotalCoralPoints);
        System.out.println("Got Coral Points: " + TotalCoralPoints);

        team.addTotalAlgaePointsInMatch(Scanner.matchNumber, Scanner.TotalAlgaePoints);
        System.out.println("Got Algae Points: " + TotalAlgaePoints);

        team.setCanRemoveAlgae(CanRemoveAlgae);
        System.out.println("Got Can Remove Algae: " + CanRemoveAlgae);

        team.setHasAuton(HasAuton);
        System.out.println("Got Has Auton: " + HasAuton);

        team.setNotes(comments, matchNumber);
        System.out.println("Got Comments: " + comments);

    }

    public static void sendDataToMatches(Matches match) {
        // Send all the data to the match
        if (Scanner.getScannedData()[ConstantsForScanner.getRobot()].equals("R1")) {
            match.setRed1(Scanner.determineRobotTeam(TeamNumber));
        }

    }

    public static boolean checkIfMatchDataExists(int MatchNumber) {
        // Check if the match data exists in any of the matches
        for (Matches match : Matches.getAllMatches()) {
            if (match.getMatchNumber() == MatchNumber) {
                System.out.println("Match data exists for match number: " + MatchNumber);
                return true;
            }
        }
        System.out.println("No match data exists for match number: " + MatchNumber);
        return false;
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

    public static void clear() {
        // Clear the scanned data
        for (int i = 0; i < scannedData.length; i++) {
            scannedData[i] = null;
        }
        TeamNumber = 0;
        Wins = 0;
        Losses = 0;
        Draws = 0;
        TotalMatchesPlayed = 0;
        matchNumber = 0;

        Breakdown = 0;
        MissedMatches = null;
        StuckGamePieces = 0;
        TotalCoralPoints = 0;
        TotalAlgaePoints = 0;
        TotalPoints = 0;
    }

}
