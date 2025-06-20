package src;
import src.JavaFXGUI.mainapp;
import src.OOPBackEnd.Matches;
import src.OOPBackEnd.RobotTeam;
import src.OOPBackEnd.Scanner;

public class Main {
    public static void main(String[] args) {
        
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        // Create instances of RobotTeam

        RobotTeam Vector = new RobotTeam(8177, "Vector");
        RobotTeam Robonauts = new RobotTeam(118, "Robonauts");
        RobotTeam TexasTorque = new RobotTeam(1477, "TexasTorque");
        RobotTeam Valor = new RobotTeam(6800, "Valor");
        RobotTeam AwtyBots = new RobotTeam(5829, "AwtyBots");
        RobotTeam BlargleFish = new RobotTeam(6369, "BlargleFish");
        RobotTeam Orbit = new RobotTeam(1690, "Orbit");

        // AwtyBots.displayTeamInfo();
        // Create instances of Matches
        Matches match1 = new Matches(1, AwtyBots, Vector, Robonauts, 100, TexasTorque, Valor, BlargleFish, 80);
        mainapp.showMainAppGUI();
        AwtyBots.displayTeamInfo();

        Scanner.determineRobotTeam(5829);
        Scanner.determineRobotTeam(2389);

        while (true) {
            System.out.println("Input Data From QR Scout (type 'clear' to exit): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("clear")) {
            break;
            }
            Scanner.QRdataToRobotTeam(input);
            AwtyBots.displayTeamInfo();
            AwtyBots.checkNotes();
        }

        scanner.close();
        }
    }