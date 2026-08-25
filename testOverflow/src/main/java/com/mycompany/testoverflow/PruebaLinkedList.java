/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testoverflow;

/**
 *
 * @author angelsn
 * @date Aug 17,2026
 */
class Student {
    String name;
    int score;
    Student next;
    public Student(String name, int score) {
        this.name = name;
        this.score = score;
        this.next = null;
    }
}

class LinkdList {
    Student head;
    public LinkdList(){
        this.head = null;
    }
    
    public void add(String name, int score) {
        Student newStudent = new Student(name, score);
        if (head == null) {
            head = newStudent;
            return;
        }
        Student current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newStudent;
    }
    
    public void print() {       
        Student current = this.head;
        while(current !=null) {
            System.out.println(current.name+", "+current.score);
            current = current.next;
        }
    }
    
    public Student retrieve(String searchedName) {
        Student current = this.head;
        while(current != null) {
            if(current.name.equals(searchedName)) { return current; }
            current = current.next;
        }
        return null;
    }
    
    public Student toString(Student student) {
        if (student != null) {
            System.out.println("Found: "+student.name+" Score: "+student.score);
        } else {
            System.out.println("No se pudo encontrar al estudiante");
        }
    }
}
            

public class PruebaLinkedList {
    public static void main(String[] args) {
        LinkdList lista = new LinkdList();
        lista.add("Linus", 90);
        lista.add("Ada", 95);
        lista.add("Paul", 78);
        
        lista.print();
        
        Student found = lista.retrieve("Ada");
        System.out.println("Found: "+found.name+" Score: "+found.score);
        Student found2 = lista.retrieve("Pepe");
        if (found2 != null) {
            System.out.println("Found: "+found2.name+" Score: "+found2.score);
        }
    }
}
