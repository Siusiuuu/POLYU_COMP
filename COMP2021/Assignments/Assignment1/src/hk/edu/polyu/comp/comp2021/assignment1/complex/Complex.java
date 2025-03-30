package hk.edu.polyu.comp.comp2021.assignment1.complex;

public class Complex {

    // Task 5 : add the missing fields
    private Rational real;
    private Rational imag;



    // Task 6: Complete the constructor as well as the methods add, subtract, multiply, divide, and toString.
    public Complex(Rational real, Rational imag) {
        // Todo: complete the constructor
        setReal(real);
        setImag(imag);

    }


    public Complex add(Complex other) {
        // Todo: complete the method
        Rational newReal=this.real.add(other.real);
        Rational newImag=this.imag.add(other.imag);
        return new Complex(newReal, newImag);



    }

    public Complex subtract(Complex other) {
        // Todo: complete the method
        Rational newReal=this.real.subtract(other.real);
        Rational newImag=this.imag.subtract(other.imag);
        return new Complex(newReal, newImag);



    }

    public Complex multiply(Complex other) {
        // Todo: complete the method
        Rational newReal=(this.real.multiply(other.real)).subtract(this.imag.multiply(other.imag));
        Rational newImag=(this.real.multiply(other.imag)).add(this.imag.multiply(other.real));
        return new Complex(newReal, newImag);
    }

    public Complex divide(Complex other) {
        // Todo: complete the method
        // you may assume 'other' is never equal to '0+/-0i'.
        Rational newReal=((this.real.multiply(other.real)).add(this.imag.multiply(other.imag))).divide((other.real.multiply(other.real)).add(other.imag.multiply(other.imag)));
        Rational newImag=((this.imag.multiply(other.real)).subtract(this.real.multiply(other.imag))).divide((other.real.multiply(other.real)).add(other.imag.multiply(other.imag)));
        return new Complex(newReal, newImag);


    }

    public void simplify() {
        // Todo: complete the method
        this.real.simplify();
        this.imag.simplify();



    }

    public String toString() {
        // Todo: complete the method
        return "("+real.toString()+","+imag.toString()+")";


    }

    // =========================== Do not change the methods below


    private Rational getReal() {
        return real;
    }

    private void setReal(Rational real) {
        this.real = real;
    }

    private Rational getImag() {
        return imag;
    }

    private void setImag(Rational imag) {
        this.imag = imag;
    }
}
