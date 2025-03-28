package views;
import models.*;
import controller.*;
import java.sql.Connection;
import java.util.Scanner;

public class UserUI {
    public static void start() {
        Connection connection = Database.connect();
        if (connection == null) {
            System.out.println("The database connection failed!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        AttendeeManager attendeeManager = new AttendeeManager();

        while (true) {
            System.out.println("\nWelcome to the User Page!");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1/2/3): ");

            int choice = getValidChoice(scanner, 1, 3);

            switch (choice) {
                case 1:
                    handleRegistration(scanner, attendeeManager);
                    break;
                case 2:
                    handleLogin(scanner, attendeeManager);
                    break;
                case 3:
                    System.out.println("Thank you for using the Banquet Management System!");
                    return;
            }
        }
    }

    private static void handleRegistration(Scanner scanner, AttendeeManager attendeeManager) {
        System.out.println("\n=== Sign Up ===");

        System.out.print("First Name (English characters): ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name (English characters): ");
        String lastName = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.println("Attendee Type (please select):");
        System.out.println("1. Staff");
        System.out.println("2. Student");
        System.out.println("3. Alumni");
        System.out.println("4. Guest");
        int typeChoice = getValidChoice(scanner, 1, 4);
        String attendeeType = convertAttendeeType(typeChoice);

        System.out.print("Email (must contain @): ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Mobile Number (8 numbers): ");
        String mobileNumber = scanner.nextLine();

        System.out.println("Affiliated Organization (please select):");
        System.out.println("1. PolyU");
        System.out.println("2. SPEED");
        System.out.println("3. HKCC");
        System.out.println("4. Others");
        int orgChoice = getValidChoice(scanner, 1, 4);
        String affiliatedOrg = convertAffiliatedOrg(orgChoice);

        boolean success = attendeeManager.registerAttendee(
            firstName, lastName, address, attendeeType,
            email, password, mobileNumber, affiliatedOrg
        );

        if (success) {
            System.out.println("Registered successfully!");
        } else {
            System.out.println("Registration failed! Please check the input format:");
            System.out.println("- The name must contain only English characters");
            System.out.println("- The mailbox must contain the @ symbol");
            System.out.println("- The phone number must be 8 digits");
        }
    }

    private static void handleLogin(Scanner scanner, AttendeeManager attendeeManager) {
        System.out.println("\n=== Login ===");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Attendee attendee = attendeeManager.login(email, password);

        if (attendee != null) {
            System.out.println("Login successful!");
            UserProfileUI.start(scanner, attendeeManager, attendee);
        } else {
            System.out.println("Login failed! Invalid email or password.");
        }
    }

    private static int getValidChoice(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.printf("Please enter valid options (%d-%d): ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("Please enter a valid number (%d-%d): ", min, max);
            }
        }
    }

    private static String convertAttendeeType(int choice) {
        switch (choice) {
            case 1: return "Staff";
            case 2: return "Student";
            case 3: return "Alumni";
            case 4: return "Guest";
            default: return "Guest";
        }
    }

    private static String convertAffiliatedOrg(int choice) {
        switch (choice) {
            case 1: return "PolyU";
            case 2: return "SPEED";
            case 3: return "HKCC";
            case 4: return "Others";
            default: return "Others";
        }
    }
}
