package org.example.Task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class PropertyAnnotationAnalyzerTest {

    @Test
    void stringFieldShouldHaveValue1() {
        Model model = new Model();
        Class cls = model.getClass();
        Path path = Paths.get("C:\\Study\\Tasks_after_lectures_5_and_6\\src\\main\\resources\\properties.properties");
        try {
            Object obj = PropertyAnnotationAnalyzer.loadFromProperties(cls, path);
            Field stringPropertyField = obj.getClass().getDeclaredField("stringProperty");
            stringPropertyField.setAccessible(true);
            Assertions.assertEquals("value1", stringPropertyField.get(obj).toString());
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void intFieldShouldHaveValue10() {
        Model model = new Model();
        Class cls = model.getClass();
        Path path = Paths.get("C:\\Study\\Tasks_after_lectures_5_and_6\\src\\main\\resources\\properties.properties");
        try {
            Object obj = PropertyAnnotationAnalyzer.loadFromProperties(cls, path);
            Field stringPropertyField = obj.getClass().getDeclaredField("myNumber");
            stringPropertyField.setAccessible(true);
            Assertions.assertEquals(10, stringPropertyField.get(obj));
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void instantFieldShouldHaveValue29NovemberOf2022Hours18Minutes30() {
        Model model = new Model();
        Class cls = model.getClass();
        Path path = Paths.get("C:\\Study\\Tasks_after_lectures_5_and_6\\src\\main\\resources\\properties.properties");
        try {
            Object obj = PropertyAnnotationAnalyzer.loadFromProperties(cls, path);
            Field stringPropertyField = obj.getClass().getDeclaredField("timeProperty");
            stringPropertyField.setAccessible(true);
            Assertions.assertEquals(LocalDateTime.of(2022, 11, 29, 18, 30).toInstant(ZoneOffset.UTC), stringPropertyField.get(obj));
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //IllegalArgumentException should be thrown if in properties file is incorrect value of String or int type,
    //or date format is invalid, or field type is illegal
//    @Test
//    void incorrectValueInPropertiesFileShouldThrowIllegalArgumentException(){
//        Model model = new Model();
//        Class cls = model.getClass();
//        Path path = Paths.get("C:\\Study\\Tasks_after_lectures_5_and_6\\src\\main\\resources\\properties.properties");
//        assertThrows(IllegalArgumentException.class, ()->{
//            PropertyAnnotationAnalyzer.loadFromProperties(cls, path);
//        });
//    }

}