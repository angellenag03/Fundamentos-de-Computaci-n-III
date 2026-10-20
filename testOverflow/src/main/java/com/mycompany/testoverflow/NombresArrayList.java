
package com.mycompany.testoverflow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author angelsn
 */
class nameCount {
    String name;
    int count;
    public nameCount(String name) {
        this.name = name;
        this.count = 1;
    }
    public void incrementCount() {
        this.count++;
    }
    public String getName() {
        return this.name+" (" + this.count + ")";
    }
}
public class NombresArrayList {
    public static void main(String[] args) {
//        ArrayList<String> countedFirstLastNames = countNames(namesList, FIRST_LASTNAME);
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
    
    public static ArrayList<String> countNames(ArrayList<String> names) {
        ArrayList<nameCount> countedNames = new ArrayList<>();
        int pos = 0; // track the position of the name 
        for (String name : names) {
            String[] splitName = name.split(" ");
            boolean found = false;
            for (nameCount nc : countedNames) {
                if (nc.name.equals(splitName[pos])) {
                    nc.incrementCount();
                    found = true;
                    break;  
                }
            }
            if (!found) {
                countedNames.add(new nameCount(splitName[pos]));
            }
        }
        ArrayList<String> result = new ArrayList<>();
        for (nameCount nc : countedNames) {
            result.add(nc.getName());
        }
        return result;
    }
}
