package org.example.Task3;

public class Cube extends Figure{

    public Cube(double measurement) {
        super(measurement);
    }

    @Override
    public double CalcVolume() {
        return Math.pow(measurement, 3);
    }

    @Override
    public String toString() {
        return "The side of the cube = " + measurement + "; volume of the cube = " + this.CalcVolume() + ';';
    }
}
