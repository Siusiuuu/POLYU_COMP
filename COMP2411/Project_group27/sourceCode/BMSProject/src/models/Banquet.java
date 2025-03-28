package models;

public class Banquet {
    private int bin;
    private String name;
    private String banquetDate;
    private String address;
    private String location;
    private String contactFirstName;
    private String contactLastName;
    private String available;
    private int quota;


    public Banquet(int bin, String name, String banquetDate, String address,
                   String location, String contactFirstName, String contactLastName,
                   String available, int quota) {
        this.bin = bin;
        this.name = name;
        this.banquetDate = banquetDate;
        this.address = address;
        this.location = location;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.available = available;
        this.quota = quota;
    }


    public int getBin() {
        return bin;
    }

    public String getName() {
        return name;
    }

    public String getBanquetDate() { // Modified Getter method name
        return banquetDate;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public String getAvailable() {
        return available;
    }

    public int getQuota() {
        return quota;
    }
}