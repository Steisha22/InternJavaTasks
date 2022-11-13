package org.example;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.example.Main.sortTags;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {
    //Checking that passing an empty list will throw an IllegalArgumentException
    @Test
    void passingAnEmptyListShouldThrowIllegalArgumentException(){
        List<String> stringList = List.of();
        assertThrows(IllegalArgumentException.class, ()->{
            LinkedHashMap<String, Integer> res = sortTags(stringList);
        });
    }

    //Checking that if the same hashtag appeared several times in a line, then it will be counted only 1 time
    @Test
    void hashtagGreatShouldOccurJustOneTime(){
        List<String> stringList = List.of("#great job #great #great", "love is beautiful",
                "#flovers for my mother", "#flovers for my girlfriend");
        LinkedHashMap<String, Integer> res = sortTags(stringList);
        LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put("#flovers", 2);
        expectedMap.put("#great", 1);
        assertTrue(res.equals(expectedMap), "Maps are not equals");
    }

    //Checking that a LinkedHashMap of only 5 elements will be returned, sorted by value from highest to lowest
    @Test
    void methodShouldReturnTop5HashtagsInDescendingOrder(){
        List<String> stringList = List.of("#great job #great #great", "#love is #beautiful",
                "#flovers for my #mother", "#flovers for my girlfriend", "my #cat", "#love through the millennia",
                "I #love you", "ginger #cat", "white #cat", "#cat on a tree");
        LinkedHashMap<String, Integer> res = sortTags(stringList);
        LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put("#cat", 4);
        expectedMap.put("#love", 3);
        expectedMap.put("#flovers", 2);
        expectedMap.put("#great", 1);
        expectedMap.put("#beautiful", 1);
        assertTrue(res.equals(expectedMap), "Maps are not equals");
    }
}
