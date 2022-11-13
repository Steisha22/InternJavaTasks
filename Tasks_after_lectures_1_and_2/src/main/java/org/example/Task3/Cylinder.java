package org.example.Task3;

public class Cylinder extends Figure{
    private static final double PI = 3.14;
    private double height;

    public Cylinder(double measurement, double height) {
        super(measurement);
        //Checking that it`s impossible to create a cylinder with a negative height.
        if(height <= 0){
            throw new IllegalArgumentException();
        }
        this.height = height;
    }

    @Override
    public double CalcVolume() {
        return PI * Math.pow(measurement, 2) * height;
    }

    @Override
    public String toString() {
        return "The radius of the cylinder = " + measurement + "; height = " + height +
                "; volume of the cylinder = " + this.CalcVolume() + ';';
    }
}
