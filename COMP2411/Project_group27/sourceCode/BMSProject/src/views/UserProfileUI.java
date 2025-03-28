package views;

import models.*;
import controller.*;
import java.util.Scanner;
import java.util.List;

public class UserProfileUI {
    public static void start(Scanner scanner, AttendeeManager attendeeManager, Attendee attendee) {
        BanquetManager banquetManager = new BanquetManager();
        
        while (true) {
            System.out.println("\n=== User Profile ===");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. View Available Banquets");
            System.out.println("4. Register for Banquet");
            System.out.println("5. Search My Registrations");
            System.out.println("6. Logout");
            System.out.print("Enter your choice (1-6): ");

            int choice = getValidChoice(scanner, 1, 6);

            switch (choice) {
                case 1:
                    displayProfile(attendee);
                    break;
                case 2:
                    updateProfile(scanner, attendeeManager, attendee);
                    break;
                case 3:
                    displayAvailableBanquets(banquetManager);
                    break;
                case 4:
                    handleBanquetRegistration(scanner, banquetManager, attendee);
                    break;
                case 5:
                    handleRegistrationSearch(scanner, banquetManager, attendee);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
            }
        }
    }

    private static void displayProfile(Attendee attendee) {
        System.out.println("\n=== Your Profile ===");
        System.out.println("First Name: " + attendee.getFirstName());
        System.out.println("Last Name: " + attendee.getLastName());
        System.out.println("Address: " + attendee.getAddress());
        System.out.println("Email: " + attendee.getEmail());
        System.out.println("Mobile Number: " + attendee.getMobileNumber());
        System.out.println("Affiliated Organization: " + attendee.getAffiliatedOrg());
    }

    private static void updateProfile(Scanner scanner, AttendeeManager attendeeManager, Attendee attendee) {
        System.out.println("\n=== Update Profile ===");

        System.out.print("New First Name (Enter to keep current): ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) attendee.setFirstName(firstName);

        System.out.print("New Last Name (Enter to keep current): ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) attendee.setLastName(lastName);

        System.out.print("New Address (Enter to keep current): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) attendee.setAddress(address);

        System.out.print("New Email (Enter to keep current): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) attendee.setEmail(email);

        System.out.print("New Password (Enter to keep current): ");
        String password = scanner.nextLine();
        if (!password.isEmpty()) attendee.setPassword(password);

        System.out.print("New Mobile Number (Enter to keep current): ");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) attendee.setMobileNumber(mobileNumber);

        if (attendeeManager.updateAttendee(attendee)) {
            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("Update failed! Please check your input format.");
        }
    }

    private static void displayAvailableBanquets(BanquetManager banquetManager) {
        System.out.println("\n=== Available Banquets ===");
        List<Banquet> banquets = banquetManager.getAvailableBanquets();
        if (banquets.isEmpty()) {
            System.out.println("No available banquets found.");
            return;
        }

        for (Banquet banquet : banquets) {
            System.out.println("BIN: " + banquet.getBin());
            System.out.println("Name: " + banquet.getName());
            System.out.println("Date: " + banquet.getBanquetDate());
            System.out.println("Location: " + banquet.getLocation());
            System.out.println("Available Seats: " + banquet.getQuota());
            System.out.println("------------------------");
        }
    }

    private static void handleBanquetRegistration(Scanner scanner, BanquetManager banquetManager, Attendee attendee) {
        System.out.println("\n=== Banquet Registration ===");

        int bin;
        while (true) {
            try {
                System.out.print("Enter Banquet BIN: ");
                bin = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for BIN.");
            }
        }

        Banquet banquet = banquetManager.getBanquetByBin(bin);
        if (banquet == null) {
            System.out.println("Banquet not found!");
            return;
        }

        if (!banquetManager.hasAvailableSeats(bin)) {
            System.out.println("Sorry, this banquet is fully booked!");
            return;
        }

        System.out.println("\nDrink Choice:");
        System.out.println("1. Tea");
        System.out.println("2. Coffee");
        System.out.println("3. Lemon Tea");
        int drinkChoice = getValidChoice(scanner, 1, 3);
        String drink = convertDrinkChoice(drinkChoice);

        System.out.println("\nMeal Choice:");
        System.out.println("1. Regular");
        System.out.println("2. Vegetarian");
        System.out.println("3. Halal");
        int mealChoice = getValidChoice(scanner, 1, 3);
        String meal = convertMealChoice(mealChoice);

        System.out.print("\nRemarks (seating preference, etc.): ");
        String remarks = scanner.nextLine();

        boolean success = banquetManager.registerForBanquet(
            bin, attendee.getEmail(), drink, meal, remarks
        );

        if (success) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Please try again later.");
        }
    }

    private static void handleRegistrationSearch(Scanner scanner, BanquetManager banquetManager, Attendee attendee) {
        System.out.println("\n=== Search Registrations ===");

        System.out.print("Enter banquet name (or press Enter to skip): ");
        String banquetName = scanner.nextLine().trim();
        if (banquetName.isEmpty()) banquetName = null;

        System.out.print("Enter banquet date (YYYY-MM-DD) (or press Enter to skip): ");
        String banquetDate = scanner.nextLine().trim();
        if (banquetDate.isEmpty()) banquetDate = null;

        List<Registration> registrations = banquetManager.searchRegistrations(
            attendee.getEmail(), banquetName, banquetDate, attendee.getAttendeeType()
        );

        if (registrations.isEmpty()) {
            System.out.println("No registrations found matching your criteria.");
            return;
        }

        System.out.println("\nFound Registrations:");
        for (int i = 0; i < registrations.size(); i++) {
            Registration reg = registrations.get(i);
            System.out.println("\n" + (i + 1) + ".");
            System.out.println("Registration ID: " + reg.getRegistrationId());
            System.out.println("Banquet ID: " + reg.getBanquetBin());
            System.out.println("Drink Choice: " + reg.getDrinkChoice());
            System.out.println("Meal Choice: " + reg.getMealChoice());
            System.out.println("Remarks: " + reg.getRemarks());
            System.out.println("Seat Number: " + reg.getSeatNumber());
        }

        System.out.print("\nWould you like to update any registration? (Y/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.print("Enter the number of the registration to update: ");
            int index = getValidChoice(scanner, 1, registrations.size()) - 1;
            updateRegistrationDetails(scanner, registrations.get(index));
        }
    }

    private static void updateRegistrationDetails(Scanner scanner, Registration registration) {
        System.out.println("\n=== Update Registration Details ===");

        System.out.println("\nDrink Choice:");
        System.out.println("1. Tea");
        System.out.println("2. Coffee");
        System.out.println("3. Lemon Tea");
        int drinkChoice = getValidChoice(scanner, 1, 3);
        String drink = convertDrinkChoice(drinkChoice);

        System.out.println("\nMeal Choice:");
        System.out.println("1. Regular");
        System.out.println("2. Vegetarian");
        System.out.println("3. Halal");
        int mealChoice = getValidChoice(scanner, 1, 3);
        String meal = convertMealChoice(mealChoice);

        System.out.print("\nRemarks (seating preference, etc.): ");
        String remarks = scanner.nextLine();

        boolean success = new BanquetManager().updateRegistration(
            registration.getRegistrationId(), drink, meal, remarks
        );

        if (success) {
            System.out.println("Registration updated successfully!");
        } else {
            System.out.println("Failed to update registration. Please try again.");
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

    private static String convertDrinkChoice(int choice) {
        switch (choice) {
            case 1: return "Tea";
            case 2: return "Coffee";
            case 3: return "Lemon Tea";
            default: return "Tea";
        }
    }

    private static String convertMealChoice(int choice) {
        switch (choice) {
            case 1: return "Regular";
            case 2: return "Vegetarian";
            case 3: return "Halal";
            default: return "Regular";
        }
    }
}