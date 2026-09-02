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
    Student tail;
    int index=0;
    public LinkdList(){
        this.head = null;
        this.tail= null;
    }

    public void add(String name, int score) {
        Student newStudent = new Student(name, score);
        if (head == null) {
            head = newStudent;
            tail=newStudent;
            this.index++;
            return;            
        }
        tail.next=newStudent;
        tail= newStudent;
        this.index++;
        
    }
    
    public void print(){
        Student current= this.head;
        while (current !=null) {
            System.out.println(current.name + " - "+ current.score);
            current = current.next;
        } 
    }
    
    public Student retrieve (String searchedName){
        Student current=this.head;
        while (current != null) {
            if (current.name.equals(searchedName)) {
                return current; 
            }
            current = current.next;
        }
        return null; //not found
    }
    
    public void remove (int n){
        if (head == null || n<0) {return;} 
        if (n>=this.index){return;}
        Student current=head;
        
        if (n==0) { 
            head = head.next;
            this.index--;
            return;
        }
        
        if (n==-1) {
            while (current.next !=tail) {                
                current= current.next;
            }
            current.next=null;
            tail=current;
            index--;
            return;
        }
        
        while (current != null && index < (n-1)) {            
            current = current.next;
            
        }
        if (current==null || current.next ==null) {
            return;
        }
        
        current.next= current.next.next;
        index--;
        
    }  
}

public class PruebaLinkedList {

    public static void main(String[] args) {
        LinkdList vaciaLista=new LinkdList();
        LinkdList lista = new LinkdList();
        lista.add("Linus", 90);
        lista.add("Ada", 95);
        lista.add("Paul", 78);
        lista.add("Grace", 75);
        
        lista.print();
        
        Student found=lista.retrieve("Ada");
        System.out.println("Found: " + found.name + " score: " + found.score);
        
        Student notFound = lista.retrieve("Manuel");
        if (notFound != null) {
            System.out.println("Found: " + notFound.name + " score: " + notFound.score);
        }
        
        lista.remove(2);
        lista.print();
        
        
        lista.remove(4);
        vaciaLista.remove(0);
        
        lista.remove(-100);
        
    }
}
