package org.example.Task3;

public class Ball extends Figure{
    private static final double PI = 3.14;

    public Ball(double measurement) {
        super(measurement);
    }

    @Override
    public double CalcVolume() {
        return ((4.0/3.0) * PI * Math.pow(measurement, 3.0));
    }

    @Override
    public String toString() {
        return "The radius of the ball = " + measurement + "; volume of the ball = " + this.CalcVolume() + ';';
    }
}
