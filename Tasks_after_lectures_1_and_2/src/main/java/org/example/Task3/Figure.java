package org.example.Task3;

public abstract class Figure implements Comparable<Figure>{
    protected double measurement;

    public Figure(double measurement) {
        //Checking that it`s impossible to create a figure with a negative or zero measurement.
        if(measurement <= 0){
            throw new IllegalArgumentException();
        }
        this.measurement = measurement;
    }

    //An abstract method for calculating the volume of a figure, which will be override in descendant classes.
    public abstract double CalcVolume();

    //A method that will allow to sort the figures according to the desired attribute. Used in the comparator.
    @Override
    public int compareTo(Figure o) {
        if (this.CalcVolume() == o.CalcVolume())
            return 0;
        else
            return (this.CalcVolume() > o.CalcVolume()) ? 1 : -1;

    }
}
