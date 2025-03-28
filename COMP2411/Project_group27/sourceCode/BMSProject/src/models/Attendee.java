package models;

public class Attendee {
    private String firstName;
    private String lastName;
    private String address;
    private String attendeeType;
    private String email;
    private String password;
    private String mobileNumber;
    private String affiliatedOrg;
    private String banquetIds;

    //constructor with all fields except banquetIds
    public Attendee(String firstName, String lastName, String address, 
                   String attendeeType, String email, String password, 
                   String mobileNumber, String affiliatedOrg) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.attendeeType = attendeeType;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.affiliatedOrg = affiliatedOrg;
    }

    //default constructor
    public Attendee() {
    }

    //getters and setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getAttendeeType() {
        return attendeeType;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAffiliatedOrg() {
        return affiliatedOrg;
    }

    public String getBanquetIds() {
        return banquetIds;
    }

    public void setBanquetIds(String banquetIds) {
        this.banquetIds = banquetIds;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAttendeeType(String attendeeType) {
        this.attendeeType = attendeeType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAffiliatedOrg(String affiliatedOrg) {
        this.affiliatedOrg = affiliatedOrg;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Validation methods
    public boolean isValid() {
        return isEmailValid() && 
               isMobileNumberValid() && 
               isNameValid() &&
               isPasswordValid();
    }

    private boolean isEmailValid() {
        return email != null && 
               email.contains("@") && 
               email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isMobileNumberValid() {
        return mobileNumber != null && 
               mobileNumber.matches("\\d{8}");
    }

    private boolean isNameValid() {
        return firstName != null && 
               lastName != null && 
               firstName.matches("[a-zA-Z\\s]+") && 
               lastName.matches("[a-zA-Z\\s]+") && 
               !firstName.trim().isEmpty() && 
               !lastName.trim().isEmpty();
    }

    private boolean isPasswordValid() {
        return password != null && 
               password.length() >= 6;
    }

    // Field validation with error messages
    public String validateField(String fieldName, String value) {
        switch (fieldName.toLowerCase()) {
            case "email":
                if (value == null || !value.contains("@") || !value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    return "Invalid email format";
                }
                break;
            case "password":
                if (value == null || value.length() < 6) {
                    return "Password must be at least 6 characters";
                }
                break;
            case "mobilenumber":
                if (value == null || !value.matches("\\d{8}")) {
                    return "Mobile number must be exactly 8 digits";
                }
                break;
            case "firstname":
            case "lastname":
                if (value == null || !value.matches("[a-zA-Z\\s]+") || value.trim().isEmpty()) {
                    return "Name must contain only English letters";
                }
                break;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Attendee{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", attendeeType='" + attendeeType + '\'' +
               ", affiliatedOrg='" + affiliatedOrg + '\'' +
               ", banquetIds='" + banquetIds + '\'' +
               '}';
    }
}
