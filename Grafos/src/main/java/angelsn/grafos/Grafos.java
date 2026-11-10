
package angelsn.grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author angelsn
 */
class GrafoArrays {
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;
    private int sizeArcosDFS, sizeArcosBFS;

    public GrafoArrays(String origen) {
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
        populateMatrizAdyacencia(origen);
    }

    private void creaTablaVertices(String origen) {
        String[] lineas = origen.split("\\R");
        vertices = new String[lineas.length * 2];
        numVertices = 0;
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
        }
    }
    private void populateMatrizAdyacencia(String origen) {
        String[] lineas = origen.split("\\R");
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            int peso = 1; // Peso default
            if (etiqueta >= 0) {
                String pesoStr = arista.substring(etiqueta + 1, arista.indexOf(']', etiqueta));
                try {
                    peso = Integer.parseInt(pesoStr.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Peso inválido en la línea: " + linea);
                }
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarArista(extremos[0].trim(), extremos[1].trim(), peso);
            }
        }
    }
    private void agregarVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return;
            }
        }
        vertices[numVertices] = vertice;
        numVertices++;
    }

    public void display() {
        System.out.println("Vértices del grafo:");
        displayVertices();
        displayMatrizAdyacencia();
    }

    private void displayVertices() {
        for (int i = 0; i < numVertices; i++) {
            System.out.println(vertices[i]);
        }
    }
    private void displayMatrizAdyacencia() {
        System.out.println("Matriz de Adyacencia:");
        // Imprimir encabezado de columnas
        System.out.print("    ");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("   " +vertices[i].substring(0,3) );
        }
        System.out.println();
        for (int i = 0; i < numVertices; i++) {
            System.out.print(vertices[i].substring(0, 4) + "    ");
            for (int j = 0; j < numVertices; j++) {
                System.out.print(matrizAdyacencia[i][j] + "     ");
            }
            System.out.println();
        }
    }
    private int obtenerIndiceVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return i;
            }
        }
        return -1;
    }
    private void agregarArista(String vertice1, String vertice2, int peso) {
        int indice1 = obtenerIndiceVertice(vertice1);
        int indice2 = obtenerIndiceVertice(vertice2);
        if (indice1 >= 0 && indice2 >= 0) {
            matrizAdyacencia[indice1][indice2] = peso;
            matrizAdyacencia[indice2][indice1] = peso; // Para grafos no dirigidos
        }
    }
    public void DFS(String verticeInicial, String VerticeFinal){
        ArrayList<String> DFSarcos = new ArrayList<>();
        boolean[] visitado = new boolean[numVertices];
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(VerticeFinal);
        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        Stack<Integer> stack = new Stack<>();
        stack.push(indiceInicial);
        int indicePasado = -1;
        while (!stack.isEmpty()) {
            int indiceActual = stack.pop();
            if (!visitado[indiceActual]) {
                visitado[indiceActual] = true;
                System.out.print(vertices[indiceActual] + " ");
                arcosDFS(indiceActual, vertices, DFSarcos, indicePasado);
                indicePasado = indiceActual;
                if (indiceActual == indiceFinal) {
                    System.out.println("\nSe ha alcanzado el vértice final.");
                    arcosDFSprint(DFSarcos);
                    return; 
                }
            }
            // Agregar vecinos no visitados al stack
            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i]) {
                    stack.push(i);
                }
            }
        }
    }
    
    public void arcosDFS(int indiceActual, String vertices[], ArrayList<String> DFSarcos, int indicePasado){
        if (indicePasado== -1) {
            return;
        }
        DFSarcos.add(vertices[indicePasado]+ " - "+vertices[indiceActual]+" / ");
    }
    public void arcosDFSprint(ArrayList<String> DFSarcos) {
        for (String DFSarco : DFSarcos) {
            System.out.print(DFSarco);
        }
        System.out.println(" ");
        System.out.println("Cantidad de arcos = " + DFSarcos.size());
        sizeArcosDFS = DFSarcos.size();
    }
    
    
    
    public void BFS(String verticeInicial, String VerticeFinal){
        ArrayList<String> BFSarcos = new ArrayList<>();
        boolean[] visitado = new boolean[numVertices];
        int indicePasado = -1;
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(VerticeFinal);
        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        Queue<Integer> queue = new LinkedList<>();
        queue.add(indiceInicial); // Agregar el vértice inicial a la cola
        while (!queue.isEmpty()) {
            int indiceActual = queue.poll();
            if (!visitado[indiceActual]) {
                visitado[indiceActual] = true;
                System.out.print(vertices[indiceActual] + " ");
                arcosBFS(indiceActual, vertices, BFSarcos, indicePasado);
                indicePasado = indiceActual;
                if (indiceActual == indiceFinal) {
                    System.out.println("\nSe ha alcanzado el vértice final.");
                    arcosBFSprint(BFSarcos);
                    return; 
                }
            }
            // Agregar vecinos no visitados a la cola
            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i]) {
                    queue.add(i);
                }
            }
        }
    }
    
    public void arcosBFS(int indiceActual, String vertices[], ArrayList<String> BFSarcos, int indicePasado){
        if (indicePasado== -1) {
            return;
        }
        BFSarcos.add(vertices[indicePasado]+ " - "+vertices[indiceActual]+" / ");
    }
    public void arcosBFSprint(ArrayList<String> BFSarcos) {
        for (String BFSarco : BFSarcos) {
            System.out.print(BFSarco);
        }
        System.out.println(" ");
        System.out.println("Cantidad de arcos = " + BFSarcos.size());
        sizeArcosBFS = BFSarcos.size();
    }
    
    public void sizeComparasion () {
        if(sizeArcosBFS > sizeArcosDFS) {
            System.out.println("El metodo DFS ha sido mas rapido con un total de arcos de: " + sizeArcosDFS);
        } else if (sizeArcosBFS < sizeArcosDFS) {
            System.out.println("El metodo BFS ha sido mas rapido con un total de arcos de: " + sizeArcosBFS);
        } else if (sizeArcosBFS == sizeArcosDFS) {
            System.out.println("Ambos metodos han demostrado la misma cantidad de arcos: "+ sizeArcosBFS);
        } else {
            System.out.println("Un error ha ocurrido en la comparacion de arcos");
        }
    }
}
public class Grafos {
    public static void main(String[] args) {
        String texto = cargaArchivo("grafo_salon.txt");
        System.out.println("Contenido del archivo:\n" + texto);
        GrafoArrays grafo = new GrafoArrays(texto);
        grafo.display();
        grafo.DFS("Carol", "Jorge");
        grafo.BFS("Carol", "Jorge");
        grafo.sizeComparasion();
        
    }
    public static String cargaArchivo(String ruta) {
        String contenido = "";
        try {
            File archivo = new File(ruta);
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                contenido += scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + ruta);
        }
        return contenido;
    }

}