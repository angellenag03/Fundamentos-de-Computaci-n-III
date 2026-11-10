
package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author angelsn
 */
public class ContadorPalabras {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Uso: java AnalizadorDocumento <archivo>");
            return;
        }

        Path archivo = Path.of(args[0]);

        if (!Files.exists(archivo)) {
            System.err.println("El archivo no existe: " + archivo);
            return;
        }

        // TreeMap ordena las claves alfabeticamente de forma automatica
        Map<String, Integer> frecuencias = new TreeMap<>();
        int totalLineas = 0;
        int totalPalabras = 0;

        try (
            BufferedReader lector = Files.newBufferedReader(archivo)
        ) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                totalLineas++;

                linea = linea.toLowerCase();
                // Elimina cualquier caracter que no sea letra, numero o espacio
                linea = linea.replaceAll("[^\\p{L}\\p{N}\\s]", "");

                if (linea.isBlank()) {
                    continue;
                }

                String[] palabras = linea.trim().split("\\s+");

                for (String palabra : palabras) {
                    frecuencias.put(
                        palabra,
                        frecuencias.getOrDefault(palabra, 0) + 1
                    );
                    totalPalabras++;
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        // Buscar la palabra con mayor frecuencia
        String palabraMasFrecuente = "";
        int frecuenciaMaxima = 0;

        for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
            if (entrada.getValue() > frecuenciaMaxima) {
                palabraMasFrecuente = entrada.getKey();
                frecuenciaMaxima = entrada.getValue();
            }
        }

        Path directorioSalida = Path.of("salida");
        Path archivoSalida = directorioSalida.resolve("reporte-documento.txt");

        try {
            Files.createDirectories(directorioSalida);
        } catch (IOException e) {
            System.err.println("No se pudo crear el directorio de salida: " + e.getMessage());
            return;
        }

        try (
            PrintWriter escritor = new PrintWriter(
                Files.newBufferedWriter(archivoSalida)
            )
        ) {
            escritor.println("ANALISIS DEL DOCUMENTO");
            escritor.println("======================");
            escritor.println();
            escritor.println("Archivo: " + archivo.getFileName());
            escritor.println();
            escritor.println("Total de lineas: " + totalLineas);
            escritor.println("Total de palabras: " + totalPalabras);
            escritor.println("Palabras diferentes: " + frecuencias.size());
            escritor.println();
            escritor.println("Palabra mas frecuente:");
            escritor.println(palabraMasFrecuente + " (" + frecuenciaMaxima + ")");
            escritor.println();
            escritor.println("FRECUENCIA DE PALABRAS");
            escritor.println("----------------------");
            escritor.println();

            for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {
                escritor.printf("%-20s %d%n", entrada.getKey(), entrada.getValue());
            }

        } catch (IOException e) {
            System.err.println("Error al escribir el reporte: " + e.getMessage());
            return;
        }

        System.out.println("Reporte generado en: " + archivoSalida.toAbsolutePath());
    }
}
