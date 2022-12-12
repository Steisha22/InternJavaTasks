package org.example.Task2;

import java.time.Instant;

public class Model {
    private String stringProperty;

    @Property(name="numberProperty")
    private int myNumber;

    @Property(format="dd.MM.yyyy HH:mm")
    private Instant timeProperty;

    @Override
    public String toString() {
        return "Model{" +
                "stringProperty = '" + stringProperty + '\'' +
                ", myNumber = " + myNumber +
                ", timeProperty = " + timeProperty +
                '}';
    }
}
