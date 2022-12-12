package org.example.Task1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class JsonParser {

    private static Lock lock = new ReentrantLock();
    private static ObjectMapper mapper = new ObjectMapper();
    private static final File folder = new File("src/main/resources/JsonFiles");
    private static Map<String, Double> statisticMap = new HashMap<>();

    //Метод, який отримує назви всіх файлів із вказаної директорії
    private static ArrayList<String> listFilesForFolder(final File folder) {
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

    //Метод, який читає дані з json файлу, парсить об'єкти та заповнює statisticMap
    private static void readingJson(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fr);
        //Рядок, в який буде збиратись json об'єкт, якщо той не записаний в один рядок
        StringBuffer stringBuilder = new StringBuffer();
        String line = reader.readLine();
        line = reader.readLine();
        while (!line.equals("]")) {
            if(line.contains("{") || !stringBuilder.isEmpty()){
                if(line.contains("}")){
                    if(!stringBuilder.isEmpty()){
                        stringBuilder.append(line);
                        line = stringBuilder.toString();
                    }
                    JsonNode jsonNode = mapper.readTree(line);
                    String type = jsonNode.get("type").asText();
                    Double fine_amount = Double.parseDouble(jsonNode.get("fine_amount").asText());
                    lock.lock();
                    if(statisticMap.containsKey(type)){
                        statisticMap.put(type, statisticMap.get(type) + fine_amount);
                    }
                    else{
                        statisticMap.put(type, fine_amount);
                    }
                    lock.unlock();
                    line = reader.readLine();
                    stringBuilder.delete(0, stringBuilder.length());
                    continue;
                }
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        try {
            //Запис отриманої статистики в xml файл
            SerializeToXmlFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Метод, який записує отриману статистику у вигляді statisticMap в xml файл,
    //попередньо відсортувавши statisticMap за спаданням загальної суми по кожному штрафу
    private static void SerializeToXmlFile() throws IOException {
        LinkedHashMap<String, Double> resultMap =
                statisticMap.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldVal, newVal) -> oldVal, LinkedHashMap::new));

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.writeValue(new File("output.xml"), resultMap);
    }

    //Реалізація багатопоточності
    public static void asyncReading() throws IOException {
        ArrayList<String> fileNames = listFilesForFolder(folder);

        //Створення пулу потоків
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        //Читання файлів й збирання статистки у багатопоточному режимі
        for (String fileName: fileNames) {
            CompletableFuture.supplyAsync(() -> fileNames, executorService)
                    .thenAccept(e -> {
                        try {
                            readingJson(fileName);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
        }
        executorService.shutdown();
        //Чекаємо, поки всі потоки завершать свою роботу
        while(!executorService.isTerminated()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
