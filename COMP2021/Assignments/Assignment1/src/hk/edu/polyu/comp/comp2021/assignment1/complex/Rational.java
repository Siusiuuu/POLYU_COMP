package hk.edu.polyu.comp.comp2021.assignment1.complex;

public class Rational {

    // Task 3: add the missing fields
    private int numerator;
    private int denominator;



    // Task 4: 	Complete the constructor and
    // the methods add, subtract, multiply, divide, simplify, and toString.

    public Rational(int numerator, int denominator){
        // Todo: complete the constructor
        if(denominator == 0){
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        setNumerator(numerator);
        setDenominator(denominator);

    }

    public Rational add(Rational other){
        // Todo: complete the method
        int newNumer = (this.numerator * other.denominator) + (other.numerator * this.denominator);
        int newDenom = this.denominator* other.denominator;
        return new Rational(newNumer,newDenom);

    }

    public Rational subtract(Rational other){
        // Todo: complete the method
        int newNumer = (this.numerator * other.denominator) - (other.numerator * this.denominator);
        int newDenom = this.denominator* other.denominator;
        return new Rational(newNumer,newDenom);


    }

    public Rational multiply(Rational other){
        // Todo: complete the method
        int newNumer = this.numerator * other.numerator;
        int newDenom= this.denominator * other.denominator;
        return new Rational(newNumer, newDenom);

    }

    public Rational divide(Rational other){
        // Todo: complete the method
        int newNumer= this.numerator * other.denominator;
        int newDenom= this.denominator * other.numerator;

        return new Rational(newNumer, newDenom);

    }

    public String toString(){
        // Todo: complete the method
        return numerator+"/"+denominator;


    }

    public void simplify(){
        // Todo: complete the method
        int gcd= gcd(numerator,denominator);
        numerator= numerator/gcd;
        denominator = denominator/gcd;

        if(denominator<0){
            denominator=-(denominator);
            numerator=-(numerator);
        }



    }

    // ========================================== Do not change the methods below.

    private int getNumerator() {
        return numerator;
    }

    private void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    private int getDenominator() {
        return denominator;
    }

    private void setDenominator(int denominator) {
        this.denominator = denominator;
    }



    private int gcd(int a, int b){
        if(b == 0)
            return a;
        else
            return gcd(b, a % b);
    }

}
