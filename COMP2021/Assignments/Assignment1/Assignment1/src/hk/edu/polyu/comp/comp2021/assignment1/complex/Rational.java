package hk.edu.polyu.comp.comp2021.assignment1.complex;

public class Rational {

    // Todo: add the missing fields


    public Rational(int numerator, int denominator){
        // Todo: complete the constructor

    }

    public Rational add(Rational other){
        // Todo: complete the method

    }

    public Rational subtract(Rational other){
        // Todo: complete the method

    }

    public Rational multiply(Rational other){
        // Todo: complete the method

    }

    public Rational divide(Rational other){
        // Todo: complete the method

    }

    public String toString(){
        // Todo: complete the method

    }

    public void simplify(){
        // Todo: complete the method

    }

    // ==========================================

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
