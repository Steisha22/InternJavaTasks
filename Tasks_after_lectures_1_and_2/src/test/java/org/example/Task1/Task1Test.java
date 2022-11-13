package org.example.Task1;

import org.junit.jupiter.api.Test;

import static org.example.Main.SortingArray;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task1Test {
    //Checking array creation by constructor with 1 parameter
    @Test
    void lenghtOfArrayShoudBe7(){
        Task1 task1 = new Task1(7);
        assertEquals(7, task1.arr.length);
    }

    //Checking for array creation by default constructor
    @Test
    void defaultArrayLengthShouldBe10(){
        Task1 task1 = new Task1();
        assertEquals(10, task1.arr.length);
    }

    //Checking array creation by constructor with 5 parameters
    @Test
    void arrayMustConsistOf5PassedElements(){
        Task1 task1 = new Task1(5, -2, 0, 1, 3);
        int expectedArr[] = {5, -2, 0, 1, 3};
        assertArrayEquals(expectedArr, task1.arr);
    }

    //Checking the final array
    @Test
    void finalArrayShouldContainOnlyPositivesElementsInDescendingOrder(){
        Task1 task1 = new Task1(5, -2, 0, 1, 3);
        int finalarr[] = {5, 3, 1, 0};
        assertArrayEquals(finalarr, SortingArray(task1.arr));
    }
}