package controller;

import models.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportGenerator {

    public void generateRegistrationStatusReport() {
        String query = "SELECT COUNT(*) AS total_registrations, "
                     + "SUM(CASE WHEN banquet_bin IS NOT NULL THEN 1 ELSE 0 END) AS attended "
                     + "FROM registrations";
        
        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
             
            if (resultSet.next()) {
                int totalRegistrations = resultSet.getInt("total_registrations");
                int attended = resultSet.getInt("attended");
                
                System.out.println("=== Registration Status Report ===");
                System.out.println("Total Registrations: " + totalRegistrations);
                System.out.println("Total Attended: " + attended);
                System.out.println("Attendance Rate: " + (totalRegistrations > 0 ? (double) attended / totalRegistrations * 100 : 0) + "%");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generatePopularMealsReport() {
        String query = "SELECT meal_choice, COUNT(*) AS count "
                     + "FROM registrations "
                     + "GROUP BY meal_choice "
                     + "ORDER BY count DESC";
        
        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
             
            System.out.println("\n=== Popular Meals Report ===");
            while (resultSet.next()) {
                String mealChoice = resultSet.getString("meal_choice");
                int count = resultSet.getInt("count");
                System.out.println(mealChoice + ": " + count + " registrations");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Generate seating report
    public void generateSeatingPlanReport(int banquetBin) {
        String query = "SELECT seat_number, attendee_email "
                     + "FROM seats "
                     + "WHERE banquet_bin = ?";

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, banquetBin);
            ResultSet resultSet = statement.executeQuery();
            
            System.out.println("\n=== Seating Plan Report for Banquet ID: " + banquetBin + " ===");
            while (resultSet.next()) {
                String seatNumber = resultSet.getString("seat_number");
                String attendeeEmail = resultSet.getString("attendee_email");
                System.out.println("Seat " + seatNumber + ": " + attendeeEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}