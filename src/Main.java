package src;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import src.OOPBackEnd.Matches;
import src.OOPBackEnd.RobotTeam;
import src.OOPBackEnd.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create instances of RobotTeam
        RobotTeam Vector = new RobotTeam(8177, "Vector");
        RobotTeam Robonauts = new RobotTeam(118, "Robonauts");
        RobotTeam TexasTorque = new RobotTeam(1477, "TexasTorque");
        RobotTeam Valor = new RobotTeam(3008, "Valor");
        RobotTeam AwtyBots = new RobotTeam(5829, "AwtyBots");
        RobotTeam BlargleFish = new RobotTeam(6969, "BlargleFish");

        // AwtyBots.displayTeamInfo();
        // Create instances of Matches
        Matches match1 = new Matches(1, AwtyBots, Vector, Robonauts, 100, TexasTorque, Valor, BlargleFish, 80);

        // AwtyBots.displayTeamInfo();

        Scanner.determineRobotTeam(5829);
        Scanner.determineRobotTeam(2389);

    }
}