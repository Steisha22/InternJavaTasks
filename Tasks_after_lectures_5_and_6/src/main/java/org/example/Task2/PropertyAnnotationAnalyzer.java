package org.example.Task2;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class PropertyAnnotationAnalyzer {
    public static <T>T loadFromProperties(Class<T> cls, Path propertiesPath) throws InstantiationException, IllegalAccessException {
        T model = cls.newInstance();
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(propertiesPath.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Fill fields
        Field [] fields = cls.getDeclaredFields();
        for (Field field: fields) {
            //Якщо тип поля не String, не int та не Instant, то генерується виключення
            if(!field.getType().equals(String.class) && !field.getType().equals(int.class) && !field.getType().equals(Instant.class)){
                throw new IllegalArgumentException("Illegal filed type!");
            }
            Annotation annotation = field.getAnnotation(Property.class);
            //Перевірка чи має поточне поле анотацію
            if (annotation == null) {
                //Якщо поле не має анотації, то необхідно просто присвоїти полю значення із property файлу
                if(appProps.getProperty(field.getName()) != null){
                    //Якщо тип поля String, то просто присвоюємо значення
                    if(field.getType().equals(String.class)){
                        //Перевірка на валідність даних у файлі properties
                        if (appProps.getProperty(field.getName()).equals("") || appProps.getProperty(field.getName()) instanceof String == false) {
                            throw new IllegalArgumentException("stringProperty is not String type");
                        }
                        field.setAccessible(true);
                        field.set(model, appProps.getProperty(field.getName()));
                        field.setAccessible(false);
                    }
                    //Якщо тип поля int, то необхідно привести значення до цілого числа
                    else{
                        //Перевірка на валідність даних у файлі properties
                        if (Double.parseDouble(appProps.getProperty(field.getName())) % 1 != 0) {
                            throw new IllegalArgumentException("numberProperty is not Integer type");
                        }
                        field.setAccessible(true);
                        field.set(model, Integer.parseInt(appProps.getProperty(field.getName())));
                        field.setAccessible(false);
                    }
                }
                continue;
            }
            //Якщо в анотації використовується префікс, то необхідно отримати саме назву, відділивши префікс
            String fieldName = "";
            //Перевірка чи використовується префікс в анотації
            if(field.getAnnotation(Property.class).name().matches("\\w+\\.\\w+")){
                //Якщо префікс є, то розбиваємо назву по крапці
                String [] words = field.getAnnotation(Property.class).name().split("\\.");
                //Оскільки назва йде після крапки, то беремо 2 елемент з отриманого масиву слів
                fieldName = words[1];
            }
            //Якщо префікса не має, то просто отримуємо значення
            else fieldName = field.getAnnotation(Property.class).name();
            //Якщо поле типу String
            if (field.getType().equals(String.class)) {
                //Перевірка на валідність даних у файлі properties
                if (appProps.getProperty(field.getName()).equals("") || appProps.getProperty(fieldName) instanceof String == false) {
                    throw new IllegalArgumentException("stringProperty is not String type");
                }
                //Встановлення значення поля
                field.setAccessible(true);
                field.set(model, appProps.getProperty(fieldName));
                field.setAccessible(false);
            }
            //Якщо поле типу int
            if (field.getType().equals(int.class)) {
                //Перевірка на валідність даних у файлі properties
                if (Double.parseDouble(appProps.getProperty(fieldName)) % 1 != 0) {
                    throw new IllegalArgumentException("numberProperty is not Integer type");
                }
                //Встановлення значення поля
                field.setAccessible(true);
                field.set(model, Integer.parseInt(appProps.getProperty(fieldName)));
                field.setAccessible(false);
            }
            //Якщо поле типу Instant
            if (field.getType().equals(Instant.class)) {
                //Перевірка на валідність даних у файлі properties
                if (!appProps.getProperty(field.getName()).matches("\\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}")) {
                    throw new IllegalArgumentException("timeProperty is not valid");
                }
                field.setAccessible(true);
                //Перетворення даних з файлу properties за заданим шаблоном
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(field.getAnnotation(Property.class).format());
                LocalDateTime localDateTime = LocalDateTime.parse(appProps.getProperty(field.getName()), formatter);
                try {
                    //Встановлення значення поля
                    field.set(model, localDateTime.toInstant(ZoneOffset.UTC));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                field.setAccessible(false);
            }
        }

        return model;
    }

}
