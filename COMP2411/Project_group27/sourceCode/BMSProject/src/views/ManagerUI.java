package views;

import controller.BanquetManager;
import controller.RegistrationTracker;
import controller.SeatManager;
import controller.ReportGenerator;
import models.Banquet;
import models.Database;
import models.Meal;
import models.Attendee;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ManagerUI {
    public static void start() {
        // Test database connection
        Connection connection = Database.connect();
        if (connection != null) {
            System.out.println("Successfully connected to the database!");
        } else {
            System.out.println("The database connection failed!");
            return; // If the connection fails, end the program
        }

        Scanner scanner = new Scanner(System.in);
        BanquetManager banquetManager = new BanquetManager();

        while (true) {
            System.out.println("\nPlease choose an operation: ");
            System.out.println("1. Create a banquet");
            System.out.println("2. Update banquet information");
            System.out.println("3. Query banquet information");
            System.out.println("4. Delete a banquet");
            System.out.println("5. Manage attendee information");
            System.out.println("6. Generate Reports");
            System.out.println("7. Seat Planning");
            System.out.println("8. Registration Tracking");
            System.out.println("9. Exit"); // Exit function is placed at the end
            System.out.print("Enter your choice (1/2/3/4/5/6/7/8/9): ");
            int choice = getInputChoice(scanner, 1, 9);
            
            switch (choice) {
                case 1: // Create a banquet
                    System.out.print("Please enter the banquet name: ");
                    String name = scanner.nextLine();
                    System.out.print("Please enter the banquet date (YYYY-MM-DD HH:MM): ");
                    String banquetDate = scanner.nextLine();
                    System.out.print("Please enter the banquet address: ");
                    String address = scanner.nextLine();
                    System.out.print("Please enter the banquet location: ");
                    String location = scanner.nextLine();
                    System.out.print("Please enter the contact person's first name: ");
                    String contactFirstName = scanner.nextLine();
                    System.out.print("Please enter the contact person's last name: ");
                    String contactLastName = scanner.nextLine();
                    System.out.print("Is the banquet available (Y/N): ");
                    String available = scanner.nextLine().toUpperCase(); // Convert to uppercase
                    System.out.print("Please enter the banquet quota: ");
                    int quota = getInputQuota(scanner);

                    int bin = banquetManager.createBanquet(name, banquetDate, address, location, contactFirstName, contactLastName, available, quota);
                    if (bin != -1) {
                        System.out.println("Banquet created successfully, Banquet ID (BIN): " + bin);

                        // Input detailed information for four meals
                        List<Meal> meals = new ArrayList<>();
                        for (int i = 0; i < 4; i++) {
                            System.out.println("Enter details for Meal " + (i + 1) + ":");
                            System.out.print("Type (e.g., Fish, Chicken, Beef, Vegetarian): ");
                            String type = scanner.nextLine();
                            System.out.print("Name: ");
                            String nameMeal = scanner.nextLine();
                            System.out.print("Price: ");
                            double price = getInputPrice(scanner); // Use a new method to get price
                            System.out.print("Special (e.g., Spicy, Gluten-Free): ");
                            String special = scanner.nextLine();

                            Meal meal = new Meal(type, nameMeal, price, special);
                            meals.add(meal);
                        }

                        // Add meals to the banquet
                        banquetManager.addMealsForBanquet(bin, meals);
                    } else {
                        System.out.println("Banquet creation failed, please check your input.");
                    }
                    break;

                case 2: // Update banquet information
                    System.out.print("Please enter the Banquet ID (BIN) to update: ");
                    int updateBin = getInputBin(scanner);
                    
                    // Get current banquet information first
                    Banquet currentBanquet = banquetManager.getBanquetByBin(updateBin);
                    if (currentBanquet == null) {
                        System.out.println("Banquet not found");
                        break;
                    }

                    // Display current values and allow user to choose whether to update
                    System.out.println("\nCurrent Banquet Information:");
                    System.out.println("Name: " + currentBanquet.getName());
                    System.out.println("Date: " + currentBanquet.getBanquetDate());
                    System.out.println("Address: " + currentBanquet.getAddress());
                    System.out.println("Location: " + currentBanquet.getLocation());
                    System.out.println("\nPlease enter new information (press Enter to keep current value):");

                    System.out.print("New banquet name [" + currentBanquet.getName() + "]: ");
                    String newName = scanner.nextLine();

                    System.out.print("New banquet date (YYYY-MM-DD HH:MM) [" + currentBanquet.getBanquetDate() + "]: ");
                    String newBanquetDate = scanner.nextLine();

                    System.out.print("New banquet address [" + currentBanquet.getAddress() + "]: ");
                    String newAddress = scanner.nextLine();

                    System.out.print("New banquet location [" + currentBanquet.getLocation() + "]: ");
                    String newLocation = scanner.nextLine();

                    banquetManager.updateBanquet(updateBin, newName, newBanquetDate, newAddress, newLocation);
                    break;

                case 3: // Query banquet information
                    System.out.print("Please enter the Banquet ID (BIN) to query: ");
                    int queryBin = getInputBin(scanner);
                    Banquet banquet = banquetManager.getBanquetByBin(queryBin);
                    if (banquet != null) {
                        System.out.println("Banquet Details:");
                        System.out.println("ID: " + banquet.getBin());
                        System.out.println("Name: " + banquet.getName());
                        System.out.println("Date: " + banquet.getBanquetDate());
                        System.out.println("Address: " + banquet.getAddress());
                        System.out.println("Location: " + banquet.getLocation());
                        System.out.println("Contact First Name: " + banquet.getContactFirstName());
                        System.out.println("Contact Last Name: " + banquet.getContactLastName());
                        System.out.println("Available: " + banquet.getAvailable());
                        System.out.println("Quota: " + banquet.getQuota());
                    } else {
                        System.out.println("No banquet found with ID: " + queryBin);
                    }
                    break;

                case 4: // Delete a banquet
                    System.out.print("Please enter the Banquet ID (BIN) to delete: ");
                    int deleteBin = getInputBin(scanner);
                    boolean deleted = banquetManager.deleteBanquet(deleteBin);
                    if (deleted) {
                        System.out.println("Banquet deleted successfully.");
                    } else {
                        System.out.println("No banquet found with ID: " + deleteBin + " or deletion failed.");
                    }
                    break;

                case 5: // Manage attendee information
                    System.out.println("\nAttendee Management:");
                    System.out.println("1. View attendee information");
                    System.out.println("2. Update attendee information");
                    System.out.print("Enter your choice (1/2): ");

                    int attendeeChoice = getInputChoice(scanner, 1, 2);
                    System.out.print("Please enter attendee's email: ");
                    String attendeeEmail = scanner.nextLine();

                    Attendee attendee = banquetManager.getAttendeeByEmail(attendeeEmail);
                    if (attendee == null) {
                        System.out.println("No attendee found with this email.");
                        break;
                    }

                    if (attendeeChoice == 1) {
                        displayAttendeeInfo(attendee);
                    } else {
                        updateAttendeeInfo(scanner, attendee, banquetManager);
                    }
                    break;

                case 6: // Generate Reports
                    System.out.println("1. Registration Status Report");
                    System.out.println("2. Popular Meals Report");
                    System.out.println("3. Seating Plan Report");
                    int reportChoice = getInputChoice(scanner, 1, 3);
                    ReportGenerator reportGenerator = new ReportGenerator();
                    
                    if (reportChoice == 1) {
                        reportGenerator.generateRegistrationStatusReport();
                    } else if (reportChoice == 2) {
                        reportGenerator.generatePopularMealsReport();
                    } else if (reportChoice == 3) {
                        System.out.print("Enter Banquet ID (BIN) for Seating Plan Report: ");
                        int banquetBin = getInputBin(scanner);
                        reportGenerator.generateSeatingPlanReport(banquetBin);
                    }
                    break;

                case 7: // Seat Planning
                    System.out.println("1. Assign Seat");
                    System.out.println("2. List Seats for Banquet");
                    int seatChoice = getInputChoice(scanner, 1, 2);
                    if (seatChoice == 1) {
                        System.out.print("Enter Banquet ID (BIN): ");
                        int banquetBin = getInputBin(scanner);
                        System.out.print("Enter Seat Number: ");
                        int seatNumber = getInputBin(scanner); // Reuse the method to get a valid seat number
                        System.out.print("Enter Attendee Email: ");
                        String email = scanner.nextLine();
                        if (new SeatManager().assignSeat(seatNumber, banquetBin, email)) {
                            System.out.println("Seat assigned successfully.");
                        } else {
                            System.out.println("Failed to assign seat.");
                        }
                    } else {
                        System.out.print("Enter Banquet ID (BIN) to list seats: ");
                        int banquetListBin = getInputBin(scanner);
                        new SeatManager().listSeatsForBanquet(banquetListBin);
                    }
                    break;

                case 8: // Registration Tracking
                    System.out.println("1. View All Registrations");
                    System.out.println("2. Query Registration by Email");
                    int registrationChoice = getInputChoice(scanner, 1, 2);
                    RegistrationTracker registrationTracker = new RegistrationTracker();

                    if (registrationChoice == 1) {
                        List<Attendee> attendees = registrationTracker.getAllRegistrations();
                        System.out.println("=== All Registration Information ===");
                        for (Attendee regAttendee : attendees) {
                            System.out.println("Name: " + regAttendee.getFirstName() + " " + regAttendee.getLastName() +
                                    ", Email: " + regAttendee.getEmail() +
                                    ", Registered Banquet IDs: " + regAttendee.getBanquetIds());
                        }
                    } else if (registrationChoice == 2) {
                        System.out.print("Enter Attendee Email: ");
                        String email = scanner.nextLine();
                        Attendee regAttendee = registrationTracker.getRegistrationByEmail(email);
                        if (regAttendee != null) {
                            System.out.println("=== Registration Details ===");
                            System.out.println("Name: " + regAttendee.getFirstName() + " " + regAttendee.getLastName());
                            System.out.println("Email: " + regAttendee.getEmail());
                            System.out.println("Mobile Number: " + regAttendee.getMobileNumber());
                            System.out.println("Address: " + regAttendee.getAddress());
                            System.out.println("Attendee Type: " + regAttendee.getAttendeeType());
                            System.out.println("Affiliated Organization: " + regAttendee.getAffiliatedOrg());
                            System.out.println("Registered Banquet IDs: " + regAttendee.getBanquetIds());
                        } else {
                            System.out.println("No registration found for email: " + email);
                        }
                    }
                    break;

                case 9: // Exit
                    System.out.println("Thank you for using the banquet management system!");
                    return; // Exit the program

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Get user choice
    private static int getInputChoice(Scanner scanner) {
        int choice = 0;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > 9) {
                    throw new InputMismatchException("Choice must be between 1 and 9");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number between 1 and 9.");
                scanner.nextLine();
            }
        }
        return choice;
    }

    // Specific range choice
    private static int getInputChoice(Scanner scanner, int min, int max) {
        int choice = 0;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < min || choice > max) {
                    throw new InputMismatchException("Choice must be between " + min + " and " + max);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number between " + min + " and " + max + ".");
                scanner.nextLine();
            }
        }
        return choice;
    }

    // Get valid banquet quota input
    private static int getInputQuota(Scanner scanner) {
        int quota = 0;
        while (true) {
            try {
                quota = scanner.nextInt();
                scanner.nextLine();
                if (quota <= 0) {
                    throw new InputMismatchException("Quota must be greater than 0");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a positive integer as the banquet quota.");
                scanner.nextLine();
            }
        }
        return quota;
    }

    // Get valid meal price input
    private static double getInputPrice(Scanner scanner) {
        double price = 0.0;
        while (true) {
            try {
                price = scanner.nextDouble();
                scanner.nextLine();
                if (price < 0) {
                    throw new InputMismatchException("Price must be non-negative");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a valid price.");
                scanner.nextLine();
            }
        }
        return price;
    }

    // Get valid banquet ID
    private static int getInputBin(Scanner scanner) {
        int bin = 0;
        while (true) {
            try {
                bin = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character
                if (bin <= 0) {
                    throw new InputMismatchException("Banquet ID (BIN) must be greater than 0");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a positive integer as the banquet ID (BIN).");
                scanner.nextLine();
            }
        }
        return bin;
    }

    // Display attendee information
    private static void displayAttendeeInfo(Attendee attendee) {
        System.out.println("\nAttendee Information:");
        System.out.println("First Name: " + attendee.getFirstName());
        System.out.println("Last Name: " + attendee.getLastName());
        System.out.println("Email: " + attendee.getEmail());
        System.out.println("Mobile Number: " + attendee.getMobileNumber());
        System.out.println("Address: " + attendee.getAddress());
        System.out.println("Attendee Type: " + attendee.getAttendeeType());
        System.out.println("Affiliated Organization: " + attendee.getAffiliatedOrg());
    }

    // Update attendee information
    private static void updateAttendeeInfo(Scanner scanner, Attendee attendee, BanquetManager banquetManager) {
        System.out.println("\nUpdate Attendee Information");
        System.out.println("Press Enter to keep current value");
        
        System.out.print("First Name [" + attendee.getFirstName() + "]: ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) attendee.setFirstName(firstName);
        
        System.out.print("Last Name [" + attendee.getLastName() + "]: ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) attendee.setLastName(lastName);
        
        System.out.print("Mobile Number [" + attendee.getMobileNumber() + "]: ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) attendee.setMobileNumber(mobileNumber);
        
        System.out.print("Address [" + attendee.getAddress() + "]: ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) attendee.setAddress(address);
        
        System.out.print("Attendee Type [" + attendee.getAttendeeType() + "]: ");
        String attendeeType = scanner.nextLine();
        if (!attendeeType.isEmpty()) attendee.setAttendeeType(attendeeType);
        
        System.out.print("Affiliated Organization [" + attendee.getAffiliatedOrg() + "]: ");
        String affiliatedOrg = scanner.nextLine();
        if (!affiliatedOrg.isEmpty()) attendee.setAffiliatedOrg(affiliatedOrg);
        
        if (attendee.isValid()) {
            if (banquetManager.updateAttendee(attendee)) {
                System.out.println("Attendee information updated successfully!");
            } else {
                System.out.println("Failed to update attendee information.");
            }
        } else {
            System.out.println("Invalid attendee information. Update failed.");
        }
    }
}