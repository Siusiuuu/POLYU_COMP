package controller;

import models.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatManager {
    
    //assign seat
    public boolean assignSeat(int seatNumber, int banquetBin, String attendeeEmail) {
        String query = "INSERT INTO seats (seat_number, banquet_bin, attendee_email) VALUES (?, ?, ?)";
        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, seatNumber);
            statement.setInt(2, banquetBin);
            statement.setString(3, attendeeEmail);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //seat for banquet info
    public void listSeatsForBanquet(int banquetBin) {
        String query = "SELECT seat_number, attendee_email FROM seats WHERE banquet_bin = ?";
        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, banquetBin);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("=== Seats for Banquet ID " + banquetBin + " ===");
            while (resultSet.next()) {
                int seatNumber = resultSet.getInt("seat_number");
                String attendeeEmail = resultSet.getString("attendee_email");
                System.out.println("Seat Number: " + seatNumber + ", Attendee Email: " + attendeeEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}