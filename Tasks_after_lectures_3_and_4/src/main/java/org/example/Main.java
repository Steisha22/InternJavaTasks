package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.Task2.Violation;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        //------------------------------ Task 1 ------------------------------
        // Створюємо буферізований символьний вхідний потік
        BufferedReader in = new BufferedReader(new FileReader("src/main/resources/XMLFilesForTask1/input.xml"));
        // Використовуємо клас PrintWriter для виведення
        try (PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/XMLFilesForTask1/out.xml"))) {
            String s;
            //Рядок, в який буде збиратись тег, якщо він не записаний в один рядок
            StringBuffer wholeString = new StringBuffer();
            //Флаг, який показує, чи вже була частина розбитого тегу записана у wholeString, чи ні
            boolean flag = false;
            //Читання файлу
            while ((s = in.readLine()) != null) {
                //Якщо це кореневий тег, то він просто запишеться у вихідний файл
                if (s.compareTo("<persons>") == 0 || s.compareTo("</persons>") == 0) {
                    out.println(s);
                    continue;
                }
                //Перевірка, чи містить поточний рядок відкриваючий тег <person
                if (s.contains("<person")) {
                    //Перевірка, чи містить поточний рядок закриваючий тег.
                    //Тобто перевірка, чи є зчитаний рядок повним тегом <person/>
                    if (s.contains("/>")) {
                        //Виклик для повного тегу методу pasrsingTag(), що виконає над ним усі необхідні перетворення
                        out.println(pasrsingTag(s));
                        continue;
                    }
                    //Якщо рядок wholeString порожній і flag = false (тобто розбитий тег ще не зустрічався)
                    //або вже був зібраний, то тоді починається збирання розбитого тегу в один рядок
                    //з урахуванням вхідного форматування. Цей крок необхідний, тому що регулярний вираз буде
                    //застосовано на рядок, а сенсу застосування цого на частину тегу є не доречним
                    if (wholeString.isEmpty()) {
                        if (!flag) {
                            wholeString.append(s);
                            flag = true;
                            continue;
                        }
                    }
                }
                //Збирання розірваного тегу в один рядок зі збереженням форматування
                if (!wholeString.isEmpty() && flag) {
                    //Оскілик це був розбитий на декілька рядків тег, то для збереженням форматування
                    //в кінець поточного рядка додається символ перенесення рядка, після чого
                    //додається нова частина тегу
                    wholeString.append("\n");
                    wholeString.append(s);
                    String tag = wholeString.toString();
                    //Якщо поточна частина мстить закриваючий тег, то це означає, що весь тег зібрано
                    //і для нього можна викликати метод pasrsingTag(), скинути флаг і видалити вміст wholeString
                    if (tag.contains("/>")) {
                        out.println(pasrsingTag(tag));
                        flag = false;
                        wholeString.delete(0, wholeString.length());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //------------------------------ Task 2 ------------------------------
        //Отримання списку назв xml файлів з вихідними даними
        final File folder = new File("src/main/resources/XMLFilesForTask2");
        ArrayList<String> folderNames = listFilesForFolder(folder);

        //Створення SAXParser для парсингу xml-файлів
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        //Створення обробника подій
        Violation handler = new Violation();
        //Витягання даних з кожного xml-файлу
        for (String folderName: folderNames) {
            parser.parse(new File(folderName), handler);
        }
        //Виведення отриманого списку порушень з xml-файлів
        for (Violation violation : handler.violations){
            System.out.println(String.format("Дата порушення:  %s, Ім'я порушника: %s, " +
                            "Прізвище порушника: %s, Тип порушення: %s, Сумма штрафу:  %s", violation.getDate_time(),
                    violation.getFirst_name(), violation.getLast_name(), violation.getType(), violation.getFinal_amount()));
        }

        //Створення словника, в якому за згрупованими порушенням порахована загальна сума штрафу для кожного з них
        Map<String, Double> statisticMap = handler.violations.stream()
                .collect(Collectors.groupingBy(Violation::getType,
                        Collectors.summingDouble(Violation::getFinal_amount)));
        System.out.println(statisticMap);
        //Сортування отриманого statisticMap від найбільшої загальної суми штрафу до меншої
        LinkedHashMap<String, Double> resultMap =
                statisticMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal, LinkedHashMap::new));
        System.out.println(resultMap);
        //Виведення в json файл отриманий відсортований словник у "красивому вигляді" з використанням бібліотеки Jackson
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File("src/main/resources/statistic.json"), resultMap);
    }

    //Метод, який розбиває отриманий тег, витягує з нього ім'я та прізвище,
    //та потім збирає це все в один рядок
    public static String pasrsingTag(String s) {
        //Задання шаблонів
        Pattern nameBeforeSurnamePattern = Pattern.compile("\\s*<person\\s*name\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*surname\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*\\S+\\s*=\\s*\"\\s*\\d+\\s*.\\s*\\d+\\s*.\\s*\\d+\\s*\"\\s*/>");
        Pattern nameAfterSurnamePattern = Pattern.compile("\\s*<person\\s*surname\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*name\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*\\S+\\s*=\\s*\"\\s*\\d+\\s*.\\s*\\d+\\s*.\\s*\\d+\\s*\"\\s*/>");
        Pattern nameAfterBirthDataPattern = Pattern.compile("\\s*<person\\s*\\S+\\s*=\\s*\"\\s*\\d+\\s*.\\s*\\d+\\s*.\\s*\\d+\\s*\"\\s*name\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*surname\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*/>");
        Pattern surnameAfterBirthDataPattern = Pattern.compile("\\s*<person\\s*\\S+\\s*=\\s*\"\\s*\\d+\\s*.\\s*\\d+\\s*.\\s*\\d+\\s*\"\\s*surname\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*name\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*/>");
        Pattern BirthDataAfterNamePattern = Pattern.compile("\\s*<person\\s*name\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*\\S+\\s*=\\s*\"\\s*\\d+\\s*.\\s*\\d+\\s*.\\s*\\d+\\s*\"\\s*surname\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*/>");
        Pattern BirthDataAfterSurnamePattern = Pattern.compile("\\s*<person\\s*surname\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*\\S+\\s*=\\s*\"\\s*\\d+\\s*.\\s*\\d+\\s*.\\s*\\d+\\s*\"\\s*name\\s*=\\s*\"\\s*(\\S+)\\s*\"\\s*/>");
        Matcher matcher1 = nameBeforeSurnamePattern.matcher(s);
        Matcher matcher2 = nameAfterSurnamePattern.matcher(s);
        Matcher matcher3 = nameAfterBirthDataPattern.matcher(s);
        Matcher matcher4 = surnameAfterBirthDataPattern.matcher(s);
        Matcher matcher5 = BirthDataAfterNamePattern.matcher(s);
        Matcher matcher6 = BirthDataAfterSurnamePattern.matcher(s);
        //Рудок, в якому будуть зберігатися ім'я і прізвище
        String namePlusSurname = "";
        //Перевірки на відповідність одному з паттернів
        if(matcher1.matches()) {
            //Виокремлення імені
            String name = matcher1.group(1);
            //Виокремлення прізвища
            String surname = matcher1.group(2);
            namePlusSurname = name + " " + surname;
            //Видалення атрибуту surname
            String resultString = deleteSurname(s);
            //Замінення імені на і'мя та прізвище і повернення результату
            return resultString.replace(name, namePlusSurname);
        }
        if(matcher2.matches()) {
            String surname = matcher2.group(1);
            String name = matcher2.group(2);
            namePlusSurname = name + " " + surname;
            String resultString = deleteSurname(s);
            return resultString.replace(name, namePlusSurname);
        }
        if(matcher3.matches()) {
            String name = matcher3.group(1);
            String surname = matcher3.group(2);
            namePlusSurname = name + " " + surname;
            String resultString = deleteSurname(s);
            return resultString.replace(name, namePlusSurname);
        }
        if(matcher4.matches()) {
            String surname = matcher4.group(1);
            String name = matcher4.group(2);
            namePlusSurname = name + " " + surname;
            String resultString = deleteSurname(s);
            return resultString.replace(name, namePlusSurname);
        }
        if(matcher5.matches()) {
            String surname = matcher5.group(1);
            String name = matcher5.group(2);
            namePlusSurname = name + " " + surname;
            String resultString = deleteSurname(s);
            return resultString.replace(name, namePlusSurname);
        }
        if(matcher6.matches()) {
            String surname = matcher6.group(1);
            String name = matcher6.group(2);
            namePlusSurname = name + " " + surname;
            String resultString = deleteSurname(s);
            return resultString.replace(name, namePlusSurname);
        }
        return "";
    }

    //Метод, який перевіряє, чи був атрибут surname на окремому рядку, чи ні
    public static boolean isEnter(String str){
        Pattern pattern = Pattern.compile("surname\\s*=\\s*\"\\s*(\\S+)\\s*\" ");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            return false;
        }
        return true;
    }

    //Метод, який видаляє атрибут surname з тегу в залежності від того, чи був він на окремому рядку,
    //чи ні. Це зроблено задля збереження вхідного форматування
    public static String deleteSurname(String str){
        String resultString;
        if(isEnter(str) == false){
            resultString = str.replaceFirst("surname\\s*=\\s*\"\\s*(\\S+)\\s*\" ", "");
        }
        else{
            resultString = str.replaceFirst("surname\\s*=\\s*\"\\s*(\\S+)\\s*\"", "");
        }
        return resultString;
    }

    //Метод, який отримує назви всіх файлів із вказаної директорії
    public static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> folderNames = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                folderNames.add(folder.getPath() + '/' + fileEntry.getName());
            }
        }
        return folderNames;
    }
}