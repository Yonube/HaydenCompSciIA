package src;

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
        RobotTeam BlargleFish = new RobotTeam(6969, "BlargleFish");

        // AwtyBots.displayTeamInfo();
        // Create instances of Matches
        Matches match1 = new Matches(1, AwtyBots, Vector, Robonauts, 100, TexasTorque, Valor, BlargleFish, 80);

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

        /**
         * Performs a binary search on a sorted array of integers.
         *
         * @param arr The sorted array to search.
         * @param target The value to search for.
         * @return The index of the target if found, otherwise -1.
         */
        // public static int binarySearch(int[] arr, int target) {
        //     int left = 0;
        //     int right = arr.length - 1;

        //     while (left <= right) {
        //         int mid = left + (right - left) / 2;

        //         // Check if the target is at mid
        //         if (arr[mid] == target) {
        //             return mid;
        //         }

        //         // If target is greater, ignore the left half
        //         if (arr[mid] < target) {
        //             left = mid + 1;
        //         } else {
        //             // If target is smaller, ignore the right half
        //             right = mid - 1;
        //         }
        //     }

            // Target not found
          //  return -1;
        }
    }