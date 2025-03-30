package hk.edu.polyu.comp.comp2021.assignment2.employee;

/**
 * Levels of salary.
 */
public enum SalaryLevel {
    ENTRY(1), JUNIOR(1.25), SENIOR(1.5), EXECUTIVE(2);

    // Task 1.5: Add missing code here.
    //enum is just a list of constant variables
    //you can not extend the enum class
    private final double scale;

    SalaryLevel(double scale){
        this.scale=scale;
    }

    public double getScale(){
        return scale;
    }






}