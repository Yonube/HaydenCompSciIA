public class Main {
    public static void main(String[] args) {
        // Create instances of RobotTeam
        RobotTeam AwtyBots = new RobotTeam(5829, "AwtyBots");
        RobotTeam Vector = new RobotTeam(8177, "Vector");
        RobotTeam Robonauts = new RobotTeam(118, "Robonauts");
        RobotTeam TexasTorque = new RobotTeam(1477, "TexasTorque");
        RobotTeam Valor = new RobotTeam(3008, "Valor");
        RobotTeam BlargleFish = new RobotTeam(6969, "BlargleFish");

        AwtyBots.displayTeamInfo();
        // Create instances of Matches
        Matches match1 = new Matches(1, AwtyBots, Vector, Robonauts, 100, 3, TexasTorque, Valor, BlargleFish, 80, 2);

        AwtyBots.displayTeamInfo();

    }
}