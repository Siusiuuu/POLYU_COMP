package controller;

import models.Database;
import models.Attendee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationTracker {

    // Get all registration information
    public List<Attendee> getAllRegistrations() {
        String sql = "SELECT DISTINCT a.*, GROUP_CONCAT(r.banquet_bin) as banquet_ids " +
                    "FROM attendees a " +
                    "LEFT JOIN registrations r ON a.email = r.attendee_email " +
                    "GROUP BY a.email";
        
        List<Attendee> attendees = new ArrayList<>();
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Attendee attendee = new Attendee(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("attendee_type"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("mobile_number"),
                    rs.getString("affiliated_organization")
                );
                String banquetIds = rs.getString("banquet_ids");
                attendee.setBanquetIds(banquetIds != null ? banquetIds : "Not registered for any banquet");
                attendees.add(attendee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendees;
    }

    // Get information for a specific registrant
    public Attendee getRegistrationByEmail(String email) {
        String sql = "SELECT a.*, GROUP_CONCAT(r.banquet_bin) as banquet_ids " +
                    "FROM attendees a " +
                    "LEFT JOIN registrations r ON a.email = r.attendee_email " +
                    "WHERE a.email = ? " +
                    "GROUP BY a.email";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Attendee attendee = new Attendee(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("attendee_type"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("mobile_number"),
                    rs.getString("affiliated_organization")
                );
                String banquetIds = rs.getString("banquet_ids");
                attendee.setBanquetIds(banquetIds != null ? banquetIds : "Not registered for any banquet");
                return attendee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}