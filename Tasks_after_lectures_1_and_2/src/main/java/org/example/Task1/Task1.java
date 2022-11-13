package org.example.Task1;

public class Task1 {
    public int arr[];

    public Task1(int len){
        arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            //Filling an array with random numbers
            arr[i] = (int) ((Math.random() * (20 - (-20) + 1) + (-20)));
        }
    }

    public Task1 (){
        arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            //Filling an array with random numbers
            arr[i] = (int) ((Math.random() * (20 - (-20) + 1) + (-20)));
        }
    }

    public Task1 (int el1, int el2, int el3, int el4, int el5){
        arr = new int[]{el1, el2, el3, el4, el5};
    }
}
