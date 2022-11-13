package org.example.Task3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.example.Main.Sort;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FigureTest {
    //Checking that it`s impossible to create a figure with a negative measurement.
    //It`s checked only on one of the figures, because the check is carried out in the super class.
    @Test
    void negativeMeasurementShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Ball(-2);
        });
    }

    //Checking that it`s impossible to create a figure with a zero measurement.
    //It`s checked only on one of the figures, because the check is carried out in the super class.
    @Test
    void zeroMeasurementShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Cube(0);
        });
    }

    //Checking that it`s impossible to create a cylinder with a negative height.
    //Negative measurement will be checked in the super class.
    @Test
    void negativeHeightShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, ()->{
            new Cylinder(3.1, -5.4);
        });
    }

    //Checking the accuracy of calculating the volume of the ball.
    //Previously, the volume was calculated in another program so that the values matched exactly.
    @Test
    void checkingVolumeOfBall(){
        Ball ball = new Ball(2.4);
        assertEquals(57.87647999999999, ball.CalcVolume());
    }

    //Checking the accuracy of calculating the volume of the cube.
    //Previously, the volume was calculated in another program so that the values matched exactly.
    @Test
    void checkingVolumeOfCube(){
        Cube cube = new Cube(3.8);
        assertEquals(54.87199999999999, cube.CalcVolume());
    }

    //Checking the accuracy of calculating the volume of the cylinder.
    //Previously, the volume was calculated in another program so that the values matched exactly.
    @Test
    void checkingVolumeOfCylinder(){
        Cylinder cylinder = new Cylinder(2.1, 4.7);
        assertEquals(65.08278, cylinder.CalcVolume());
    }

    //Checking the order of shapes in a sorted list.
    @Test
    void figuresShouldBeSortedInAscendingOrderByVolume(){
        ArrayList<Figure> figures = new ArrayList<>();
        figures.add(new Cube(3.8)); //volume = 54.872
        figures.add(new Ball(2.4)); //volume = 57.90584
        figures.add(new Cylinder(2.1, 4.7)); //volume = 65.11579
        figures.add(new Cube(5.2)); //volume = 140.608
        assertArrayEquals(figures.toArray(), Sort(figures).toArray());
    }
}