package org.example;

import org.example.Task1.Task1;
import org.example.Task3.*;

import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        //----------------------------- Task 1 -----------------------------
        System.out.println("----------------------------- Task 1 -----------------------------");

        //Creating a new array
        Task1 task1 = new Task1(15);

        //Outputting the initial array to the screen
        System.out.println("Initial array");
        for (int a : task1.arr) {
            System.out.print(a + " ");
        }
        System.out.println();
        System.out.println();

        //Getting from a method TaSortingArray() an array filled with only
        //positive elements and sorted in descending order
        int onlyPositiveselementsArray[] = SortingArray(task1.arr);
        System.out.println();
        System.out.println("Only positives elements in descending order");
        //Outputting the final array to the screen
        for (int a : onlyPositiveselementsArray) {
            System.out.print(a + " ");
        }
        System.out.println();
        System.out.println();

        //----------------------------- Task 2 -----------------------------
        System.out.println("----------------------------- Task 2 -----------------------------");
        List<String> stringList = List.of("#beatiful flowers", "the #best game ever", "my lovely #dog",
                "give the #dog in good hands", "#cool house", "#best friends", "#best master",
                "#beatiful girl", "#dog and #cat", "the #best scientific book", "my #best morning",
                "#cool programmer", "#cool candle", "#cool teacher #cool subject", "#bachelor degree");
        LinkedHashMap<String, Integer> res = sortTags(stringList);
        System.out.println();
        System.out.println("Top 5 hashtags");
        for(Map.Entry<String,Integer> item : res.entrySet()){
            System.out.printf("Number of occurrences: %s  Tag: %s \n", item.getValue(), item.getKey());
        }

        System.out.println();
        System.out.println();

        //----------------------------- Task 3 -----------------------------
        System.out.println("----------------------------- Task 3 -----------------------------");
        ArrayList<Figure> figures = new ArrayList<>();
        figures.add(new Ball(3));
        figures.add(new Ball(1.5));
        figures.add(new Cube(5));
        figures.add(new Cube(4.2));
        figures.add(new Cylinder(2, 4));
        figures.add(new Cylinder(3.7, 0.8));
        System.out.println("Initial list of figures");
        for (Figure figure : figures) {
            System.out.println(figure);
        }
        ArrayList<Figure> sortedFigures = Sort(figures);
        System.out.println();
        System.out.println("Final list of sorted by volume figures");
        for (Figure figure : sortedFigures) {
            System.out.println(figure);
        }
    }

    //Method that takes an array of integers as input and returns a new array consisting of
    //only positive elements of the input array and sorted in descending order
    public static int[] SortingArray(int arr[]){
        //Sorting the input array by the Bubble method
        for(int i = 0; i < arr.length; i++) {
            for (int k = 0; k < arr.length - 1; k++) {
                if (arr[i] > arr[k]) {
                    int tmp = arr[k];
                    arr[k] = arr[i];
                    arr[i] = tmp;
                }
            }
        }
        int k = 0; //Counter of positive elements in input array
        System.out.println("Array after sorting");
        for (int a: arr) {
            System.out.print(a + " ");
            if(a >= 0){
                k++;
            }
        }
        System.out.println();
        //Creating an array of length k that will consist of only positive elements of the input array
        int onlyPositiveselementsArray[] = new int[k];
        k = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] >= 0){
                onlyPositiveselementsArray[k] = arr[i];
                k++;
            }
        }
        return onlyPositiveselementsArray;
    }

    public static LinkedHashMap<String, Integer> sortTags(List<String> stringList){
        if(stringList.isEmpty()){
            throw new IllegalArgumentException();
        }
        LinkedHashMap<String, Integer> tags = new LinkedHashMap<>();
        //
        for(String str : stringList) {
            //Split current row into words
            String words[] = str.split(" ");
            //A list to store the hashtags of each row
            List<String> list = new ArrayList<>();
            //Traversing an array of strings
            for (int i = 0; i < words.length; i++){
                //Checking that the current word is a tag and this tag is not in the list (not repeating a tag)
                if (words[i].startsWith("#") && !list.contains(words[i])){
                    list.add(words[i]);
                    //Checking if this tag is already in the map, if it is, then its value is increased by 1
                    if(tags.containsKey(words[i])){
                        tags.put(words[i], tags.get(words[i]) + 1);
                        continue;
                    }
                    tags.put(words[i], 1);
                }
            }
        }
        System.out.println("Initial map");
        for(Map.Entry<String,Integer> item : tags.entrySet()){
            System.out.printf("Number of occurrences: %s  Tag: %s \n", item.getValue(), item.getKey());
        }
        //Sorting the map by value and getting the first top 5 hashtags
        return tags.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal, LinkedHashMap::new));
    }

    //Method that sorts figures using Comparator
    public static ArrayList<Figure> Sort(ArrayList<Figure> figures){
        ArrayList<Figure> sortedFigures = new ArrayList<>();
        sortedFigures.addAll(figures);
        sortedFigures.sort(new ComratorByVolume());
        return sortedFigures;
    }
}