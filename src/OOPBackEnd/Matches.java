package src.OOPBackEnd;

import java.io.Serializable;

public class Matches implements Serializable {
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
    private static Matches[] allMatches = new Matches[60]; // Placeholder for all matches
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

    public void calcBlueScore() {
        int score = 0;
        if (Blue1 != null)
            score += Blue1.getTotalPointsInMatch(matchNumber);
        if (Blue2 != null)
            score += Blue2.getTotalPointsInMatch(matchNumber);
        if (Blue3 != null)
            score += Blue3.getTotalPointsInMatch(matchNumber);
        this.BlueScore = score;
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

    public void calcRedScore() {
        int score = 0;
        if (Red1 != null)
            score += Red1.getTotalPointsInMatch(matchNumber);
        if (Red2 != null)
            score += Red2.getTotalPointsInMatch(matchNumber);
        if (Red3 != null)
            score += Red3.getTotalPointsInMatch(matchNumber);
        this.RedScore = score;
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

    public boolean isFull() {
        if (Blue1 == null || Blue2 == null || Blue3 == null || Red1 == null || Red2 == null || Red3 == null) {
            return false;
        } else {
            return true;
        }
    }

    // Action Commands
    public void addToWinningTeam() {
        if (!isPopulated) {
            return;
        }

        calcBlueScore();
        calcRedScore();

        java.util.function.Function<RobotTeam, RobotTeam> canonical = (t) -> {
            if (t == null)
                return null;
            int tn = t.getTeamNumber();
            for (RobotTeam rt : RobotTeam.AllTeams) {
                if (rt != null && rt.getTeamNumber() == tn)
                    return rt;
            }
            return null;
        };

        RobotTeam[] blueTeams = { canonical.apply(Blue1), canonical.apply(Blue2), canonical.apply(Blue3) };
        RobotTeam[] redTeams = { canonical.apply(Red1), canonical.apply(Red2), canonical.apply(Red3) };

        if (BlueScore > RedScore) {
            for (RobotTeam t : blueTeams)
                if (t != null)
                    t.addWin();
            for (RobotTeam t : redTeams)
                if (t != null)
                    t.addLoss();
        } else if (RedScore > BlueScore) {
            for (RobotTeam t : redTeams)
                if (t != null)
                    t.addWin();
            for (RobotTeam t : blueTeams)
                if (t != null)
                    t.addLoss();
        } else {
            for (RobotTeam t : blueTeams)
                if (t != null)
                    t.addDraw();
            for (RobotTeam t : redTeams)
                if (t != null)
                    t.addDraw();
        }

        numberOfMatchesPlayed++;
    }

}
