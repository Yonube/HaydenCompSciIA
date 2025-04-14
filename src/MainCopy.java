// package src;

// import src.OOPBackEnd.Matches;
// import src.OOPBackEnd.RobotTeam;
// import src.OOPBackEnd.Scanner;

// // Removed to avoid conflict with src.OOPBackEnd.Scanner

// public class MainCopy {
//     public static void main(String[] args) {
//         java.util.Scanner inputScanner = new java.util.Scanner(System.in);

//         // Create instances of RobotTeam
//         RobotTeam Vector = new RobotTeam(8177, "Vector");
//         RobotTeam Robonauts = new RobotTeam(118, "Robonauts");
//         RobotTeam TexasTorque = new RobotTeam(1477, "TexasTorque");
//         RobotTeam Valor = new RobotTeam(3008, "Valor");
//         RobotTeam AwtyBots = new RobotTeam(5829, "AwtyBots");
//         RobotTeam BlargleFish = new RobotTeam(6969, "BlargleFish");

//         // Create instances of Matches
//         Matches match1 = new Matches(1, AwtyBots, Vector, Robonauts, 100, TexasTorque, Valor, BlargleFish, 80);

//         // Main interaction loop
//         while (true) {
//             System.out.println("\nChoose an option:");
//             System.out.println("1. Display team info");
//             System.out.println("2. Set notes for a team");
//             System.out.println("3. Check notes for a team");
//             System.out.println("4. Set if a team can defend");
//             System.out.println("5. Check if a team can defend");
//             System.out.println("6. Exit");

//             int choice = inputScanner.nextInt();
//             inputScanner.nextLine(); // Consume newline

//             switch (choice) {
//                 case 1:
//                     // Display team info
//                     AwtyBots.displayTeamInfo();
//                     Vector.displayTeamInfo();
//                     Robonauts.displayTeamInfo();
//                     TexasTorque.displayTeamInfo();
//                     Valor.displayTeamInfo();
//                     BlargleFish.displayTeamInfo();
//                     break;

//                 case 2:
//                     // Set notes for a team
//                     System.out.println("Enter team name:");
//                     String teamName = inputScanner.nextLine();
//                     System.out.println("Enter match number:");
//                     int matchNumber = inputScanner.nextInt();
//                     inputScanner.nextLine(); // Consume newline
//                     System.out.println("Enter notes:");
//                     String notes = inputScanner.nextLine();

//                     RobotTeam team = getTeamByName(teamName, AwtyBots, Vector, Robonauts, TexasTorque, Valor,
//                             BlargleFish);
//                     if (team != null) {
//                         team.setNotes(notes, matchNumber);
//                         System.out.println("Notes added for " + teamName);
//                     } else {
//                         System.out.println("Team not found.");
//                     }
//                     break;

//                 case 3:
//                     // Check notes for a team
//                     System.out.println("Enter team name:");
//                     teamName = inputScanner.nextLine();

//                     team = getTeamByName(teamName, AwtyBots, Vector, Robonauts, TexasTorque, Valor, BlargleFish);
//                     if (team != null) {
//                         team.checkNotes();
//                     } else {
//                         System.out.println("Team not found.");
//                     }
//                     break;

//                 case 4:
//                     // Set if a team can defend
//                     System.out.println("Enter team name:");
//                     teamName = inputScanner.nextLine();
//                     System.out.println("Can this team defend? (true/false):");
//                     boolean canDefend = inputScanner.nextBoolean();

//                     team = getTeamByName(teamName, AwtyBots, Vector, Robonauts, TexasTorque, Valor, BlargleFish);
//                     if (team != null) {
//                         team.setCanDefend(canDefend);
//                         System.out.println("Defense capability updated for " + teamName);
//                     } else {
//                         System.out.println("Team not found.");
//                     }
//                     break;

//                 case 5:
//                     // Check if a team can defend
//                     System.out.println("Enter team name:");
//                     teamName = inputScanner.nextLine();

//                     team = getTeamByName(teamName, AwtyBots, Vector, Robonauts, TexasTorque, Valor, BlargleFish);
//                     if (team != null) {
//                         System.out.println(teamName + " can defend: " + team.canDefend());
//                     } else {
//                         System.out.println("Team not found.");
//                     }
//                     break;

//                 case 6:
//                     // Exit
//                     System.out.println("Exiting program.");
//                     inputScanner.close();
//                     return;

//                 default:
//                     System.out.println("Invalid choice. Please try again.");
//             }
//         }
//     }

//     // Helper method to get a team by name
//     private static RobotTeam getTeamByName(String name, RobotTeam... teams) {
//         for (RobotTeam team : teams) {
//             if (team.getTeamName().equalsIgnoreCase(name)) {
//                 return team;
//             }
//         }
//         return null;
//     }
// }