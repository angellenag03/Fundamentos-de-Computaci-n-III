
package com.mycompany.testoverflow;

public class ArrayTest {
    public static void main(String[] args) {
        int[] array = new int[3];
        array[0] = 7;
        array[1] = 8;
        array[2] = array[0]+array[1];
        
        int[] na = new int[5];
        for(int i=0;i<5;i++) {
            na[i] = array[i];
        }
    }
}
