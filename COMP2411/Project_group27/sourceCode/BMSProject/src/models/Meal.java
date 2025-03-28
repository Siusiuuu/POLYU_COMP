package models;

public class Meal {
    private String type;
    private String name;
    private double price;
    private String special;


    public Meal(String type, String name, double price, String special) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.special = special;
    }


    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSpecial() {
        return special;
    }
}