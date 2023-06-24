
// a complex number representation in Java
public class Complex {
    public double real;
    public double imaginary;

    private final double EPSILON = 0.00001; // this is used to compare double values more accurately

    public static final Complex ZERO = new Complex(0,0);

    public Complex(double real, double imaginary) {
        this.real=real;
        this.imaginary=imaginary;
    }

    public Complex add(double num1) {
        return new Complex(this.real+num1,this.imaginary);
    }

    public Complex add(Complex z) {
        return new Complex(this.real+z.real,this.imaginary+z.imaginary);
    }

    public Complex subtract(double num1) {
        return new Complex(this.real-num1,this.imaginary);
    }

    public Complex subtract(Complex z) {
        return new Complex(this.real-z.real,this.imaginary-z.imaginary);
    }

    public Complex multiply(Complex z) {
        return new Complex(this.real*z.real - this.imaginary*z.imaginary, this.real*z.imaginary + this.imaginary*z.real);
    }

    public Complex square() {
        return new Complex(Math.pow(this.real, 2) - Math.pow(this.imaginary,2),2 * (this.real * this.imaginary));
    }

    @Override
    public String toString() {
        return this.real+"+"+this.imaginary+"i";
    }

    private boolean accurateCompare(double d1, double d2, double epsilon) {
        return Math.abs(d2 - d1) < epsilon;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Complex) {
            Complex z=(Complex)obj;
            if (accurateCompare(real, z.real, EPSILON) && accurateCompare(imaginary, z.imaginary, EPSILON)) return true;
        }
        return false;
    }

    public boolean isNaN() {
        if (Double.isNaN(real) || Double.isNaN(imaginary)) return true;
        if (Double.isInfinite(imaginary)) return true;
        return false;
    }

}
