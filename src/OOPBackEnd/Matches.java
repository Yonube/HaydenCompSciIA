package src.OOPBackEnd;

import java.io.Serializable;

public class Matches implements Serializable{
    private static int numberOfMatchesPlayed = 0;
    private int matchNumber;
    private RobotTeam Blue1;
    private RobotTeam Blue2;
    private RobotTeam Blue3;
    private int BlueScore;
    private RobotTeam Red1;
    private RobotTeam Red2;
    private RobotTeam Red3;
    private int RedScore;
    private static Matches[] allMatches = new Matches[90]; // Placeholder for all matches
    private boolean isPopulated; // Boolean variable to track if the match is populated

    public Matches(int matchNumber, RobotTeam Blue1, RobotTeam Blue2, RobotTeam Blue3, int BlueScore,
            RobotTeam Red1, RobotTeam Red2, RobotTeam Red3, int RedScore) {
        this.matchNumber = matchNumber;
        this.Blue1 = Blue1;
        this.Blue2 = Blue2;
        this.Blue3 = Blue3;
        this.BlueScore = BlueScore;
        this.Red1 = Red1;
        this.Red2 = Red2;
        this.Red3 = Red3;
        this.RedScore = RedScore;
        this.isPopulated = false;
        numberOfMatchesPlayed++;
        allMatches[matchNumber] = this;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }
    public RobotTeam getBlue1() {
        return Blue1;
    }

    public void setBlue1(RobotTeam Blue1) {
        this.Blue1 = Blue1;
    }

    public RobotTeam getBlue2() {
        return Blue2;
    }

    public void setBlue2(RobotTeam Blue2) {
        this.Blue2 = Blue2;
    }

    public RobotTeam getBlue3() {
        return Blue3;
    }

    public void setBlue3(RobotTeam Blue3) {
        this.Blue3 = Blue3;
    }

    public int getBlueScore() {
        return BlueScore;
    }

    public void setBlueScore(int BlueScore) {
        this.BlueScore = BlueScore;
    }

    public RobotTeam getRed1() {
        return Red1;
    }

    public void setRed1(RobotTeam Red1) {
        this.Red1 = Red1;
    }

    public RobotTeam getRed2() {
        return Red2;
    }

    public void setRed2(RobotTeam Red2) {
        this.Red2 = Red2;
    }

    public RobotTeam getRed3() {
        return Red3;
    }

    public void setRed3(RobotTeam Red3) {
        this.Red3 = Red3;
    }

    public int getRedScore() {
        return RedScore;
    }

    public void setRedScore(int RedScore) {
        this.RedScore = RedScore;
    }

    public static int getNumberOfMatchesPlayed() {
        return numberOfMatchesPlayed;
    }

    public static void setNumberOfMatchesPlayed(int numberOfMatchesPlayed) {
        Matches.numberOfMatchesPlayed = numberOfMatchesPlayed;
    }
    public static Matches[] getAllMatches() {
        return allMatches;
    }

    public boolean getIsPopulated() {
        return isPopulated;
    }

    public void setIsPopulated(boolean isPopulated) {
        this.isPopulated = isPopulated;
    }

    // Action Commands
    public void addWinToWinningTeam() {
        if (BlueScore > RedScore) {
            // When the blue team wins
            Blue1.addWin();
            Blue2.addWin();
            Blue3.addWin();
            Red1.addLoss();
            Red2.addLoss();
            Red3.addLoss();
        } else if (RedScore > BlueScore) {
            // When the red team wins
            Red1.addWin();
            Red2.addWin();
            Red3.addWin();
            Blue1.addLoss();
            Blue2.addLoss();
            Blue3.addLoss();
        } else {
            // Handle draw case
            Blue1.addDraw();
            Blue2.addDraw();
            Blue3.addDraw();
            Red1.addDraw();
            Red2.addDraw();
            Red3.addDraw();
        }
        // Update the total matches played
        numberOfMatchesPlayed++;
    }
}
