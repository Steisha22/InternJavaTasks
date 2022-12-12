package org.example;

import org.example.Task1.JsonParser;
import org.example.Task2.Model;
import org.example.Task2.PropertyAnnotationAnalyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        //------------------------------ Task 1 ------------------------------
        long start = System.currentTimeMillis();
        try {
            JsonParser.asyncReading();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Program running time: " + (System.currentTimeMillis() - start));

        //------------------------------ Task 2 ------------------------------
        Model model = new Model();
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream("C:\\Study\\Tasks_after_lectures_5_and_6\\src\\main\\resources\\properties.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Class cls = model.getClass();
        Path path = Paths.get("C:\\Study\\Tasks_after_lectures_5_and_6\\src\\main\\resources\\properties.properties");
        try {
            System.out.println();
            System.out.println("Filled Model");
            System.out.println(PropertyAnnotationAnalyzer.loadFromProperties(cls, path));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}