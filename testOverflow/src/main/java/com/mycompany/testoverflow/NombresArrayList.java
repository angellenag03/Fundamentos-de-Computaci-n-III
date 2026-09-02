/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testoverflow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author angelsn
 */
public class NombresArrayList {
    public static void main(String[] args) {
        ArrayList<String> nombres = new ArrayList<>();
        Path filePath = Paths.get ("Listado.txt");
        ArrayList<String> namesList = readNamesFromFile(filePath.toString());
        System.out.println(filePath.toAbsolutePath().toString());
        printNames(namesList);
        ArrayList<String> duplicados = mostrarDuplicados(namesList);
        printNames(duplicados);
    }
    
    public static ArrayList<String> readNamesFromFile(String filePath) {
        ArrayList<String> nombres = new ArrayList<>();
        Path path = Paths.get(filePath);

        try {
            List<String> lineas = Files.readAllLines(path);
            
            for (String linea : lineas) {
                String nombreLimpio = linea.trim();
                if (!nombreLimpio.isEmpty()) {
                    nombres.add(nombreLimpio);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return nombres;
    }
    
    public static void printNames(ArrayList<String> namesList) {
        for(String n : namesList){
            System.out.println(n);
        }
    }

    public static ArrayList<String> mostrarDuplicados(ArrayList<String> namesList) {
        ArrayList<String> primerosNombres = listarPrimerosNombres(namesList);
//        printNames(primerosNombres); // DEBUG
        
        ArrayList<String> resultado = new ArrayList<>();

        int contador = 1;
        for (int i = 0; i < primerosNombres.size(); i++) {
            if (i < primerosNombres.size() - 1 && primerosNombres.get(i).equals(primerosNombres.get(i + 1))) {
                contador++;
            } else {
                resultado.add(primerosNombres.get(i) + ": " + contador + " veces");
                contador = 1; 
            }
        }

        return resultado;
    }

    private static ArrayList<String> listarPrimerosNombres(ArrayList<String> list) {
        ArrayList<String> primerosNombres = new ArrayList<>();
        for(String n : list) {
            primerosNombres.add(n.split(" ")[0]);
        }
        Collections.sort(primerosNombres);
        return primerosNombres;
    }
}
