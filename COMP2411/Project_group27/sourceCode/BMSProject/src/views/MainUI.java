package views;

import controller.BanquetManager;
import models.Database;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainUI {

    public static void start() {
        System.out.println("Welcome to the Banquet Management System!");
        System.out.println("We are group 27");


        Connection connection = Database.connect();
        if (connection != null) {
            System.out.println("Successfully connected to the database!");
        } else {
            System.out.println("The database connection failed!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        BanquetManager banquetManager = new BanquetManager();

        try {
            while (true) {
                System.out.println("\nPlease choose an operation: ");
                System.out.println("1. I am a manager");
                System.out.println("2. I am a user");
                System.out.println("3. Exit");
                System.out.print("Enter your choice (1/2/3): ");
                int choice = getInputChoice(scanner);

                switch (choice) {
                    case 1: // go to the manager page
                        ManagerUI.start();
                        break;
                    case 2: // go to the user page
                        UserUI.start();
                        break;
                    case 3: // quit
                        System.out.println("Thank you for using the Banquet Management System!");
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } finally {
            scanner.close();
        }
    }

    //making sure the input is valid
    private static int getInputChoice(Scanner scanner) {
        int choice = 0;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > 3) {
                    throw new InputMismatchException("Choice must be between 1 and 3");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number between 1 and 3.");
                scanner.nextLine();
            }
        }
        return choice;
    }


}
