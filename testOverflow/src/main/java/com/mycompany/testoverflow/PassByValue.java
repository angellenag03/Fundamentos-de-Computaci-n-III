
package com.mycompany.testoverflow;

public class PassByValue {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        compute(b, b);
        System.out.printf("%d",a);
        
    }
    
    static void compute(int x, int y){
        x = x*y;
    }
}
