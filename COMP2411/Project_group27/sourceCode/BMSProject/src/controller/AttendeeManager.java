package controller;
import models.Database;
import models.Attendee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendeeManager {
    
    //sign up
    public boolean registerAttendee(String firstName, String lastName, String address,
                                  String attendeeType, String email, String password,
                                  String mobileNumber, String affiliatedOrg) {
        
        //Create Attendee Object and check
        Attendee attendee = new Attendee(firstName, lastName, address, attendeeType,
                                       email, password, mobileNumber, affiliatedOrg);
        
        if (!attendee.isValid()) {
            return false;
        }

        //SQL
        String sql = "INSERT INTO attendees (first_name, last_name, address, attendee_type, " +
                    "email, password, mobile_number, affiliated_organization) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, address);
            pstmt.setString(4, attendeeType);
            pstmt.setString(5, email);
            pstmt.setString(6, password);
            pstmt.setString(7, mobileNumber);
            pstmt.setString(8, affiliatedOrg);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //check the attendee through email
    public Attendee getAttendeeByEmail(String email) {
        String sql = "SELECT * FROM attendees WHERE email = ?";
        
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add login method
    public Attendee login(String email, String password) {
        String sql = "SELECT * FROM attendees WHERE email = ? AND password = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //update user info
    public boolean updateAttendee(Attendee attendee) {
        if (!attendee.isValid()) {
            return false;
        }

        String sql = "UPDATE attendees SET first_name=?, last_name=?, address=?, " +
                    "email=?, password=?, mobile_number=?, affiliated_organization=? " +
                    "WHERE email=?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, attendee.getFirstName());
            pstmt.setString(2, attendee.getLastName());
            pstmt.setString(3, attendee.getAddress());
            pstmt.setString(4, attendee.getEmail());
            pstmt.setString(5, attendee.getPassword());
            pstmt.setString(6, attendee.getMobileNumber());
            pstmt.setString(7, attendee.getAffiliatedOrg());
            pstmt.setString(8, attendee.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}