
package com.mycompany.testoverflow;

class Dog{
    String name;
    Dog(String name){
        this.name = name;
    }
}

public class PassByReference {
    public static void main(String[] args) {
        Dog myDog = new Dog("Max");
        System.out.println(myDog.name);
        modifyName(myDog, "Maximus");
        System.out.println(myDog.name);
    }
    static void modifyName(Dog d, String name) {
        d.name = name;
    }
}
