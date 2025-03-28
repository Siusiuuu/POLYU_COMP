package models;

import java.time.LocalDateTime;

public class Registration {
    private int registrationId;
    private int banquetBin;
    private String attendeeEmail;
    private String drinkChoice;
    private String mealChoice;
    private String remarks;
    private LocalDateTime registrationTime;
    private int seatNumber;

    public Registration(int registrationId, int banquetBin, String attendeeEmail,
                       String drinkChoice, String mealChoice, String remarks,
                       LocalDateTime registrationTime, int seatNumber) {
        this.registrationId = registrationId;
        this.banquetBin = banquetBin;
        this.attendeeEmail = attendeeEmail;
        this.drinkChoice = drinkChoice;
        this.mealChoice = mealChoice;
        this.remarks = remarks;
        this.registrationTime = registrationTime;
        this.seatNumber = seatNumber;
    }


    public int getRegistrationId() { return registrationId; }
    public int getBanquetBin() { return banquetBin; }
    public String getAttendeeEmail() { return attendeeEmail; }
    public String getDrinkChoice() { return drinkChoice; }
    public String getMealChoice() { return mealChoice; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getRegistrationTime() { return registrationTime; }
    public int getSeatNumber() { return seatNumber; }
}
