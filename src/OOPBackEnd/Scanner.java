package src.OOPBackEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.*;

import src.OOPBackEnd.ConstantsForScanner;

public class Scanner implements ActionListener{
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

    // Safely parse integers from scanned strings. Handles values like "3.0"
    private static int parseIntSafe(String s) {
        if (s == null) return 0;
        s = s.trim();
        if (s.length() == 0) return 0;
        // If it's a floating representation of an integer like "3.0", strip the decimal
        if (s.endsWith(".0")) {
            s = s.substring(0, s.length() - 2);
            if (s.length() == 0) return 0;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            try {
                double d = Double.parseDouble(s);
                return (int) Math.round(d);
            } catch (NumberFormatException ex) {
                // As a last resort, return 0
                return 0;
            }
        }
    }

    public static int getTeamNumber() {
        return TeamNumber;
    }

    // public static boolean doesMatchDataExist(int MatchNumber) {
    //     // Check if the match data exists in any of the matches
    //     for (Matches match : Matches.getAllMatches()) {
    //         if (match == null) {
    //             continue; // Skip null matches
    //         }
    //         if (match.getMatchNumber() == MatchNumber) {
    //             System.out.println("Match data exists for match number: " + MatchNumber);
    //             return true;
    //         }
    //     }
    //     System.out.println("No match data exists for match number: " + MatchNumber);
    //     return false;
    // }

    public static void QRdataToRobotTeam(String inputedddata, java.util.Scanner scanner) {
        Scanner.processScannedData(inputedddata,scanner);
        Scanner.sendAllDataToTeam(Scanner.determineRobotTeam(Scanner.getTeamNumber()));
        Scanner.sendDataToMatches(Matches.getAllMatches()[matchNumber]);
        Scanner.clear();
    }

    public static void FileDataToRobotTeamTSV(String inputedData, JFrame frame){
        Scanner.processScannedData(inputedData,frame, "tab");
        Scanner.sendAllDataToTeam(Scanner.determineRobotTeam(Scanner.getTeamNumber()));
        Scanner.sendDataToMatches(Matches.getAllMatches()[matchNumber]);
        Scanner.clear();
    }
    public static void FileDataToRobotTeamCSV(String inputedData, JFrame frame){
        Scanner.processScannedData(inputedData,frame, "comma");
        Scanner.sendAllDataToTeam(Scanner.determineRobotTeam(Scanner.getTeamNumber()));
        Scanner.sendDataToMatches(Matches.getAllMatches()[matchNumber]);
        Scanner.clear();
    }

    public static void processScannedData(String inputFromQR,java.util.Scanner scanner) {
        // Process the scanned data
        System.out.println("Processing scanned data: ");
        // Split the scanned data into an array
        Scanner.setScannedData(inputFromQR.split("\t"));
        System.out.println("Split data");
        // Get match number
    matchNumber = parseIntSafe(getScannedData()[ConstantsForScanner.getMatchNumber()]);
        System.out.println(" Got Match Number");
        // Team Number
    TeamNumber = parseIntSafe(getScannedData()[ConstantsForScanner.getTeamNumber()]);
        System.out.println("Got Team Number");
        // Robot
        String robot = getScannedData()[ConstantsForScanner.getRobot()];
        System.out.println("Got Robot: " + robot);
        // Create Robot Team if it does not exist (checking is done in the method)
        Scanner.createNewRobotTeam(TeamNumber,scanner);
        // Coral Points
        TotalCoralPoints =
                // Auton Coral L1
                parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL1()]) +
                // Auton Coral L2
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL2()]) +
                        // Auton Coral L3
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL3()]) +
                        // Auton Coral L4
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL4()]) +
                        // Teleop Coral L1
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL1()]) +
                        // Teleop Coral L2
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL2()]) +
                        // Teleop Coral L3
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL3()]) +
                        // Teleop Coral L4
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL4()]);
        System.out.println("Got Coral Points: " + TotalCoralPoints);
        // Algae Points
        TotalAlgaePoints =
                // Auton Algae Barge
                parseIntSafe(getScannedData()[ConstantsForScanner.getAutonAlgaeBarge()]) +
                // Auton Algae Processor
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonAlgaeProcessor()]) +
                        // Teleop Algae Barge
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopAlgaeBarge()]) +
                        // Teleop Algae Processor
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopAlgaeProcessor()]);
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

    public static void processScannedData(String inputFromQR, JFrame frame, String delimiter) {
        // Process the scanned data and update the robot's state
        // For example, you might want to parse the scanned data and update the robot's
        // attributes
        System.out.println("Processing scanned data: ");
        // Split the scanned data into an array
        if (delimiter.equals("tab")) {
            delimiter = "\t";
        } else if (delimiter.equals("comma")) {
            delimiter = ",";
        }
        Scanner.setScannedData(inputFromQR.split(delimiter));
        System.out.println("Split data");
        // Get match number
    matchNumber = parseIntSafe(getScannedData()[ConstantsForScanner.getMatchNumber()]);
        System.out.println(" Got Match Number");

        // Team Number
    TeamNumber = parseIntSafe(getScannedData()[ConstantsForScanner.getTeamNumber()]);
        System.out.println("Got Team Number");

        // Robot
        String robot = getScannedData()[ConstantsForScanner.getRobot()];
        System.out.println("Got Robot: " + robot);


        Scanner.createNewRobotTeamGUI(TeamNumber,frame);
        // If No Show Adds to Missed Matches
        // if (getScannedData()[5].equals("true")) {
        // MissedMatches[matchNumber] = true;
        // }
        // Coral Points
        TotalCoralPoints =
                // Auton Coral L1
                parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL1()]) +
                // Auton Coral L2
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL2()]) +
                        // Auton Coral L3
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL3()]) +
                        // Auton Coral L4
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL4()]) +
                        // Teleop Coral L1
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL1()]) +
                        // Teleop Coral L2
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL2()]) +
                        // Teleop Coral L3
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL3()]) +
                        // Teleop Coral L4
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL4()]);
        System.out.println("Got Coral Points: " + TotalCoralPoints);
        // Algae Points
        TotalAlgaePoints =
                // Auton Algae Barge
                parseIntSafe(getScannedData()[ConstantsForScanner.getAutonAlgaeBarge()]) +
                // Auton Algae Processor
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonAlgaeProcessor()]) +
                        // Teleop Algae Barge
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopAlgaeBarge()]) +
                        // Teleop Algae Processor
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopAlgaeProcessor()]);
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

    public static void processScannedData(String[] inputFromFile,java.util.Scanner scanner) {
        // Process the scanned data and update the robot's state
        // For example, you might want to parse the scanned data and update the robot's
        // attributes
        System.out.println("Processing scanned data: ");
        // Split the scanned data into an array
        Scanner.setScannedData(inputFromFile);
        // Get match number
    matchNumber = parseIntSafe(getScannedData()[ConstantsForScanner.getMatchNumber()]);
        System.out.println(" Got Match Number");

        // Team Number
    TeamNumber = parseIntSafe(getScannedData()[ConstantsForScanner.getTeamNumber()]);
        System.out.println("Got Team Number");

        // Robot
        String robot = getScannedData()[ConstantsForScanner.getRobot()];
        System.out.println("Got Robot: " + robot);


        Scanner.createNewRobotTeam(TeamNumber,scanner);
        // If No Show Adds to Missed Matches
        // if (getScannedData()[5].equals("true")) {
        // MissedMatches[matchNumber] = true;
        // }
        // Coral Points
        TotalCoralPoints =
                // Auton Coral L1
                parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL1()]) +
                // Auton Coral L2
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL2()]) +
                        // Auton Coral L3
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL3()]) +
                        // Auton Coral L4
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonCoralL4()]) +
                        // Teleop Coral L1
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL1()]) +
                        // Teleop Coral L2
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL2()]) +
                        // Teleop Coral L3
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL3()]) +
                        // Teleop Coral L4
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopCoralL4()]);
        System.out.println("Got Coral Points: " + TotalCoralPoints);
        // Algae Points
        TotalAlgaePoints =
                // Auton Algae Barge
                parseIntSafe(getScannedData()[ConstantsForScanner.getAutonAlgaeBarge()]) +
                // Auton Algae Processor
                        parseIntSafe(getScannedData()[ConstantsForScanner.getAutonAlgaeProcessor()]) +
                        // Teleop Algae Barge
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopAlgaeBarge()]) +
                        // Teleop Algae Processor
                        parseIntSafe(getScannedData()[ConstantsForScanner.getTeleopAlgaeProcessor()]);
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
    System.out.println("Storing data for team=" + (team!=null?team.getTeamNumber():"null") + " matchNumber=" + matchNumber +
        " total=" + TotalPoints + " coral=" + TotalCoralPoints + " algae=" + TotalAlgaePoints);
    team.addTotalPointsInMatch(matchNumber, TotalPoints);

    team.addTotalCoralPointsInMatch(matchNumber, TotalCoralPoints);

    team.addTotalAlgaePointsInMatch(matchNumber, TotalAlgaePoints);
    System.out.println("Stored points for team " + (team!=null?team.getTeamNumber():"null") + " at match " + matchNumber + ": total=" + team.getTotalPointsInMatch(matchNumber) +
        " coral=" + team.getTotalCoralPointsInMatch(matchNumber) + " algae=" + team.getTotalAlgaePointsInMatch(matchNumber));

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
        } else if (Scanner.getScannedData()[ConstantsForScanner.getRobot()].equals("R2")) {
            match.setRed2(Scanner.determineRobotTeam(TeamNumber));
        } else if (Scanner.getScannedData()[ConstantsForScanner.getRobot()].equals("R3")) {
            match.setRed3(Scanner.determineRobotTeam(TeamNumber));
        } else if (Scanner.getScannedData()[ConstantsForScanner.getRobot()].equals("B1")) {
            match.setBlue1(Scanner.determineRobotTeam(TeamNumber));
        } else if (Scanner.getScannedData()[ConstantsForScanner.getRobot()].equals("B2")) {
            match.setBlue2(Scanner.determineRobotTeam(TeamNumber));
        } else if (Scanner.getScannedData()[ConstantsForScanner.getRobot()].equals("B3")) {
            match.setBlue3(Scanner.determineRobotTeam(TeamNumber));
        }
        match.setIsPopulated(true);

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

    public static RobotTeam determineRobotTeam(String teamName) {
        // Determine the robot team based on the team number
        for (int i = 0; i < RobotTeam.AllTeams.length; i++) {
            if (RobotTeam.AllTeams[i] != null) {
                if (teamName != null && teamName.equals(RobotTeam.AllTeams[i].getTeamName())) {
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
    public static boolean checkIfRobotTeamExists(int teamNumber) {
        // Check if the robot team exists in the directory
        for (RobotTeam team : RobotTeam.AllTeams) {
            if (team != null && team.getTeamNumber() == teamNumber) {
                System.out.println("Robot team with number " + teamNumber + " exists in the directory.");
                return true;
            }
        }
        System.out.println("Robot team with number " + teamNumber + " does not exist in the directory.");
        return false;
    }
    public static boolean checkIfRobotTeamExists(String teamName) {
        // Check if the robot team exists in the directory
        for (RobotTeam team : RobotTeam.AllTeams) {
            if (team != null && team.getTeamName().equals(teamName)) {
                System.out.println("Robot team with name " + teamName + " exists in the directory.");
                return true;
            }
        }
        System.out.println("Robot team with name " + teamName + " does not exist in the directory.");
        return false;
    }

    public static void createNewRobotTeam(int teamNumber, java.util.Scanner scanner) {
        // Create a new RobotTeam if it does not exist
        if (!checkIfRobotTeamExists(teamNumber)) {
            RobotTeam newTeam = new RobotTeam(teamNumber, null);
            System.out.println("Created new RobotTeam with number: " + teamNumber);
            System.out.println("Please state RobotTeam name: ");
            String input = scanner.nextLine(); // Use the passed Scanner object
            newTeam.setTeamName(input);
            System.out.println("New RobotTeam created: " + newTeam.getTeamName() + " with number: " + newTeam.getTeamNumber());
        }
    }

    public static void createNewRobotTeam(int teamNumber, String teamName,java.util.Scanner scanner) {
        // Create a new RobotTeam if it does not exist
        if (!checkIfRobotTeamExists(teamNumber)) {
            RobotTeam newTeam = new RobotTeam(teamNumber, teamName);
            System.out.println("Created new RobotTeam with number: " + teamNumber);
            System.out.println("Please state RobotTeam name: ");
            String input = scanner.nextLine(); // Use the passed Scanner object
            newTeam.setTeamName(input);
            System.out.println("New RobotTeam created: " + newTeam.getTeamName() + " with number: " + newTeam.getTeamNumber());
        }
    }
    public static void createNewRobotTeamGUI(int teamNumber, JFrame frame) {
        // Create a new RobotTeam if it does not exist
        if (!checkIfRobotTeamExists(teamNumber)) {
            RobotTeam newTeam = new RobotTeam(teamNumber, null);
            JTextField teamNameField = new JTextField(20);
            JButton skip = new JButton("Use Team Number?");
                skip.addActionListener(event -> teamNameField.setText(String.valueOf(teamNumber)));
            JPanel panel = new JPanel();
            panel.add(new JLabel("Team Number: "+ teamNumber + ". Please enter team name:"));
            panel.add(teamNameField);
            panel.add(skip);
            frame.add(panel);

            int result = JOptionPane.showConfirmDialog(frame, panel, "New RobotTeam", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                newTeam.setTeamName(teamNameField.getText());
                System.out.println("New RobotTeam created: " + newTeam.getTeamName() + " with number: " + newTeam.getTeamNumber());
            } else {
                System.out.println("Error in Team Creation.");
            }
            frame.remove(panel);
        }
    }


    public static RobotTeam[] calculateUpToTop5RobotTeams(){
        // Calculate and return the top 5 RobotTeams based on total points
        
        RobotTeam[] top5Teams = new RobotTeam[5];
        int count = 0; // To keep track of the number of teams added to top5Teams

        for (RobotTeam team : RobotTeam.AllTeams) {
            if (team != null) {
            for (int i = 0; i < top5Teams.length; i++) {
                if (top5Teams[i] == null || team.getTotalPoints() > top5Teams[i].getTotalPoints()) {
                // Shift lower ranked teams down
                for (int j = top5Teams.length - 1; j > i; j--) {
                    top5Teams[j] = top5Teams[j - 1];
                }
                top5Teams[i] = team;
                count++;
                break;
                }
            }
            }
        }

        // If there are fewer than 5 teams, resize the array to fit the actual number of teams
        if (count < 5) {
            RobotTeam[] resizedTopTeams = new RobotTeam[count];
            System.arraycopy(top5Teams, 0, resizedTopTeams, 0, count);
            top5Teams = resizedTopTeams;
        }

        System.out.println("Calculated top " + count + " RobotTeams based on total points.");
        return top5Teams;
    }
    // Handling TSV Data
    public static String tsvFileToString(String filePath) throws IOException {
        try {
            Path file = java.nio.file.Paths.get(filePath);
            System.out.println("Attempting to read file: " + file.toAbsolutePath());
            String fileContent = Files.readString(file);
            System.out.println("Read TSV file content successfully.");
            return fileContent;
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            throw e; // Re-throw the exception for higher-level handling
        }
    }
    public static String[] tsvStringToArray(String tsvData) {
        // Splits the TSV data into an array of strings using tab as the delimiter
        String[] dataArray = tsvData.split("\n");
        System.out.println("Split TSV data into array.");
        return dataArray;
    }

    public static String[] tsvRowToArray(String tsvRow) {
        // Splits a single TSV row into an array of strings using tab as the delimiter
        String[] rowArray = tsvRow.split("\t");
        System.out.println("Split TSV row into array.");
        return rowArray;
    }

    public static void processTSVData(File filename, java.util.Scanner scanner) throws IOException {
        String tsvData = tsvFileToString(filename.getAbsolutePath());
        String[] rows = tsvStringToArray(tsvData);
        for (String row : rows) {
            System.out.println("Processing row: " + row);
            // Example: You can call processScannedData or other methods here
            Scanner.QRdataToRobotTeam(row, scanner);
            
        }
    }
    // Handling CSV Data
    public static String csvFileToString(String filePath) throws IOException {
        // Creates a Path object for the file
        Path file = Path.of(filePath);

        // Reads the entire content of the file into a single String.
        // It uses the system's default charset (usually UTF-8).
        String fileContent = Files.readString(file);

        return fileContent;
    }

    public static String[] csvStringToArray(String csvData) {
        // Splits the CSV data into an array of strings using comma as the delimiter
        String[] dataArray = csvData.split(",");
        return dataArray;
    }

    public static String[] csvRowToArray(String csvRow) {
        // Splits a single CSV row into an array of strings using comma as the delimiter
        String[] rowArray = csvRow.split(",");
        return rowArray;
    }
    public static List<Matches> matchesTeamIsIn(RobotTeam team) {
        // Returns an array of Matches that the given RobotTeam is part of
       java.util.List<Matches> teamMatches = new java.util.ArrayList<>();
        if (team == null) return teamMatches;
        int teamNum = team.getTeamNumber();
        for (Matches match : Matches.getAllMatches()) {
            if (match == null) continue;
            // Compare by team number rather than object identity so deserialized references still match
            RobotTeam r1 = match.getRed1();
            RobotTeam r2 = match.getRed2();
            RobotTeam r3 = match.getRed3();
            RobotTeam b1 = match.getBlue1();
            RobotTeam b2 = match.getBlue2();
            RobotTeam b3 = match.getBlue3();
            if ((r1 != null && r1.getTeamNumber() == teamNum) ||
                (r2 != null && r2.getTeamNumber() == teamNum) ||
                (r3 != null && r3.getTeamNumber() == teamNum) ||
                (b1 != null && b1.getTeamNumber() == teamNum) ||
                (b2 != null && b2.getTeamNumber() == teamNum) ||
                (b3 != null && b3.getTeamNumber() == teamNum)) {
                teamMatches.add(match);
            }
        }
        System.out.println("matchesTeamIsIn: Found " + teamMatches.size() + " matches for team " + (team!=null?team.getTeamNumber():"null"));
        for (Matches m : teamMatches) {
            System.out.println(" - match " + m.getMatchNumber());
        }
        return teamMatches;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
