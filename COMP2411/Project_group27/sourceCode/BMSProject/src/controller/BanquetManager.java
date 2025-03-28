package controller;
import models.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;


public class BanquetManager {

    // Create a new banquet method, returns the generated BIN
    public int createBanquet(String name, String banquetDate, String address, String location, String contactFirstName, String contactLastName, String available, int quota) {
        String sql = "INSERT INTO banquets (name, banquet_date, address, location, contact_first_name, contact_last_name, available, quota) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, banquetDate); // Make sure to use banquet_date instead of date
            pstmt.setString(3, address);
            pstmt.setString(4, location);
            pstmt.setString(5, contactFirstName);
            pstmt.setString(6, contactLastName);
            pstmt.setString(7, available);
            pstmt.setInt(8, quota);

            pstmt.executeUpdate();

            // Get the generated banquet ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating banquet failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 if an error occurs
        }
    }

    // Update banquet information method
    public void updateBanquet(int bin, String newName, String newBanquetDate, String newAddress, String newLocation) {
        // First get current banquet information
        Banquet currentBanquet = getBanquetByBin(bin);
        if (currentBanquet == null) {
            System.out.println("Banquet not found");
            return;
        }

        // If input is empty, use existing values
        String finalName = newName.isEmpty() ? currentBanquet.getName() : newName;
        String finalDate = newBanquetDate.isEmpty() ? currentBanquet.getBanquetDate() : newBanquetDate;
        String finalAddress = newAddress.isEmpty() ? currentBanquet.getAddress() : newAddress;
        String finalLocation = newLocation.isEmpty() ? currentBanquet.getLocation() : newLocation;

        String sql = "UPDATE banquets SET name = ?, banquet_date = ?, address = ?, location = ? WHERE bin = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, finalName);
            pstmt.setString(2, finalDate);
            pstmt.setString(3, finalAddress);
            pstmt.setString(4, finalLocation);
            pstmt.setInt(5, bin);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Banquet information updated successfully!");
            } else {
                System.out.println("Banquet not found, update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Query banquet information method
    public static Banquet getBanquetByBin(int bin) {
        String sql = "SELECT bin, name, banquet_date, address, location, contact_first_name, contact_last_name, available, quota FROM banquets WHERE bin = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bin);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Extract data, make sure to use the correct column names
                int id = rs.getInt("bin");
                String name = rs.getString("name");
                String banquetDate = rs.getString("banquet_date");
                String address = rs.getString("address");
                String location = rs.getString("location");
                String contactFirstName = rs.getString("contact_first_name");
                String contactLastName = rs.getString("contact_last_name");
                String available = rs.getString("available");
                int quota = rs.getInt("quota");

                return new Banquet(id, name, banquetDate, address, location, contactFirstName, contactLastName, available, quota);
            } else {
                System.out.println("No banquet found with ID: " + bin);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete banquet method
    public boolean deleteBanquet(int bin) {
        String sql = "DELETE FROM banquets WHERE bin = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bin);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;//return true if deletion is successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; //return false if deletion fails
    }

    // Add meal information method
    public void addMealsForBanquet(int banquetBin, List<Meal> meals) {
        for (Meal meal : meals) {
            addMealToDatabase(banquetBin, meal);
        }
        System.out.println("Meals added for models.Banquet ID: " + banquetBin);
    }

    private void addMealToDatabase(int banquetBin, Meal meal) {
        String sql = "INSERT INTO meals (banquet_bin, type, name, price, special) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, banquetBin);
            pstmt.setString(2, meal.getType());
            pstmt.setString(3, meal.getName());
            pstmt.setDouble(4, meal.getPrice());
            pstmt.setString(5, meal.getSpecial());

            pstmt.executeUpdate();
            System.out.println("models.Meal added to database: " + meal.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Attendee getAttendeeByEmail(String email) {
        String sql = "SELECT first_name, last_name, address, attendee_type, email, " +
                     "password, mobile_number, affiliated_organization FROM attendees WHERE email = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Attendee(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("attendee_type"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("mobile_number"),
                    rs.getString("affiliated_organization")
                );
            } else {
                System.out.println("No attendee information can be found for this email address" + email);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while querying attendee information:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateAttendee(Attendee attendee) {
        String sql = "UPDATE attendees SET " +
                     "first_name = ?, " +
                     "last_name = ?, " +
                     "address = ?, " +
                     "attendee_type = ?, " +
                     "mobile_number = ?, " +
                     "affiliated_organization = ? " +
                     "WHERE email = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, attendee.getFirstName());
            pstmt.setString(2, attendee.getLastName());
            pstmt.setString(3, attendee.getAddress());
            pstmt.setString(4, attendee.getAttendeeType());
            pstmt.setString(5, attendee.getMobileNumber());
            pstmt.setString(6, attendee.getAffiliatedOrg());
            pstmt.setString(7, attendee.getEmail());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Attendee information updated successfully!");
                return true;
            } else {
                System.out.println("No attendee records were found to update.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while updating attendee information:" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<Banquet> getAvailableBanquets() {
        List<Banquet> banquets = new ArrayList<>();
        String sql = "SELECT * FROM banquets WHERE available = 'Y' AND quota > 0";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Banquet banquet = new Banquet(
                    rs.getInt("bin"),
                    rs.getString("name"),
                    rs.getString("banquet_date"),
                    rs.getString("address"),
                    rs.getString("location"),
                    rs.getString("contact_first_name"),
                    rs.getString("contact_last_name"),
                    rs.getString("available"),
                    rs.getInt("quota")
                );
                banquets.add(banquet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return banquets;
    }

    public static boolean hasAvailableSeats(int bin) {
        String sql = "SELECT quota FROM banquets WHERE bin = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bin);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("quota") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerForBanquet(int bin, String attendeeEmail,
                                           String drinkChoice, String mealChoice,
                                           String remarks) {
        Connection conn = null;
        try {
            conn = Database.connect();
            conn.setAutoCommit(false);

            // 1. Check if seats are available
            if (!hasAvailableSeats(bin)) {
                return false;
            }

            // 2. Get the next available seat number
            String seatSql = "SELECT COALESCE(MAX(seat_number), 0) + 1 AS next_seat " +
                            "FROM registrations WHERE banquet_bin = ?";
            int seatNumber;
            try (PreparedStatement pstmt = conn.prepareStatement(seatSql)) {
                pstmt.setInt(1, bin);
                ResultSet rs = pstmt.executeQuery();
                rs.next();
                seatNumber = rs.getInt("next_seat");
            }

            // 3. Insert registration information
            String regSql = "INSERT INTO registrations (banquet_bin, attendee_email, " +
                           "drink_choice, meal_choice, remarks, registration_time, seat_number) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(regSql)) {
                pstmt.setInt(1, bin);
                pstmt.setString(2, attendeeEmail);
                pstmt.setString(3, drinkChoice);
                pstmt.setString(4, mealChoice);
                pstmt.setString(5, remarks);
                pstmt.setObject(6, LocalDateTime.now());
                pstmt.setInt(7, seatNumber);
                pstmt.executeUpdate();
            }

            // 4. Update banquet remaining seats
            String updateSql = "UPDATE banquets SET quota = quota - 1 WHERE bin = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, bin);
                pstmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Registration> searchRegistrations(String attendeeEmail, String banquetName,
                                               String banquetDate, String attendeeType) {
        List<Registration> registrations = new ArrayList<>();

        String sql = "SELECT r.*, b.name as banquet_name, b.banquet_date " +
                     "FROM registrations r " +
                     "JOIN banquets b ON r.banquet_bin = b.bin " +
                     "JOIN attendees a ON r.attendee_email = a.email " +
                     "WHERE r.attendee_email = ? " +
                     "AND (? IS NULL OR LOWER(b.name) LIKE LOWER(?)) " +
                     "AND (? IS NULL OR b.banquet_date LIKE ?) " +
                     "AND (? IS NULL OR a.attendee_type = ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, attendeeEmail);
            pstmt.setString(2, banquetName == null ? null : "%" + banquetName + "%");
            pstmt.setString(3, banquetName == null ? null : "%" + banquetName + "%");
            pstmt.setString(4, banquetDate);
            pstmt.setString(5, banquetDate == null ? null : "%" + banquetDate + "%");
            pstmt.setString(6, attendeeType);
            pstmt.setString(7, attendeeType);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Registration registration = new Registration(
                    rs.getInt("registration_id"),
                    rs.getInt("banquet_bin"),
                    rs.getString("attendee_email"),
                    rs.getString("drink_choice"),
                    rs.getString("meal_choice"),
                    rs.getString("remarks"),
                    rs.getTimestamp("registration_time").toLocalDateTime(),
                    rs.getInt("seat_number")
                );
                registrations.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    public boolean updateRegistration(int registrationId, String drinkChoice,
                                    String mealChoice, String remarks) {
        String sql = "UPDATE registrations SET drink_choice = ?, meal_choice = ?, " +
                     "remarks = ? WHERE registration_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, drinkChoice);
            pstmt.setString(2, mealChoice);
            pstmt.setString(3, remarks);
            pstmt.setInt(4, registrationId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}