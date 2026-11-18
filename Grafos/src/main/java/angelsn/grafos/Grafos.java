package grafos;
import angelsn.grafos.GrafoListas;
import java.util.ArrayList;

class GrafoArrays{
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;
    
    private ArrayList<String> arcosDFS;
       private ArrayList<String> arcosBFS;

    
    public GrafoArrays(String origen){
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
    }
    
    private void creaTablaVertices(String origen){
        String[] lineas = origen.split("\\R");
        vertices = new String[lineas.length * 2];
        numVertices=0;
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista=arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            if (etiqueta>=0) {
                arista= arista.substring(0,etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length ==2) {
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
        }
    }
    
    private void agregarVertice(String vertice){
        for(int i = 0; i<numVertices; i++){
            if (vertices[i].equals(vertice)) {
                return;
            }
        }
        vertices[numVertices]= vertice;
        numVertices++;
    }
    
    public void display(){
        System.out.println("Vertices del grafo:");
        displayVertices();
    }
        
    private void displayVertices(){
        for (int i = 0; i < numVertices; i++) {
            System.out.println(vertices[i]);
        }
    }
    
    private int obtenerIndiceVertice(String vertice){
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return i;
            }
        }
        return -1;
    }
    
    private void agregarArista (String vertice1, String vertice2, int peso){
        int indice1 = obtenerIndiceVertice(vertice1);
        int indice2= obtenerIndiceVertice(vertice2);
        if (indice1>=0 && indice2>=0) {
            matrizAdyacencia[indice1][indice2] = peso;
            matrizAdyacencia[indice2][indice1] = peso;
        }
    }
    
    //Crear metodo de crear repetir un poco del codigo de creaTablaVertices
    public void populateMatriz(String origen){
        String[] lineas = origen.split("\\R");
        int peso = 0;
        for (String linea : lineas) {
            peso = 0;
            String arista = linea.trim();
            if (arista.startsWith("graph") || arista.isEmpty()) { continue; }
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int primeracomilla = arista.indexOf("\"");
            int segundacomilla = arista.indexOf("\"", primeracomilla+1);
            if (primeracomilla>=0 && segundacomilla>primeracomilla) {
                String valorPeso = arista.substring(primeracomilla+1, segundacomilla);
                peso = Integer.parseInt(valorPeso);
            }
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarArista(extremos[0].trim(), extremos[1].trim(), peso);
            }
        }
    }
    //crear metodo para desplegar
    
    public void desplegarMatriz(){
        System.out.println("================MATRIZ DE ADYACENCIA=====================");
        for (int i = 0; i < numVertices; i++) {
            System.out.print(vertices[i].substring(0, 3)+" ");
            for (int j = 0; j < numVertices; j++) {
                System.out.printf("%d   ", matrizAdyacencia[i][j]);
            }
            System.out.println();
        }
        System.out.print("   ");
        for (int i = 0; i < numVertices; i++) {
            System.out.print(vertices[i].substring(0, 3)+" ");
            
        }
    }
    //populate matrizAdyacencia(origen)
    
    public void DFS(String verticeInicial, String verticeFinal){
        arcosDFS = new ArrayList<>();
        boolean[] visitado= new boolean[numVertices];
        int[] origenPadre= new int[numVertices];
        for (int i =0; i<numVertices; i++) origenPadre[i]=-1;
        int indiceInicial= obtenerIndiceVertice(verticeInicial);
        int indiceFinal= obtenerIndiceVertice(verticeFinal);
        if (indiceInicial==-1 || indiceFinal==-1) {
            System.out.println("Uno o ambos de los vertices no existen en este árbol");
            return;
        }
        System.out.println("Recorrido DFS de "+ verticeInicial + " hasta "+verticeFinal);
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        stack.push(indiceInicial);
        while(!stack.isEmpty()){
            int indiceActual = stack.pop();
            if (!visitado[indiceActual]) {
                visitado[indiceActual] = true;
                if (origenPadre[indiceActual] != -1) {
                    arcosDFS.add(vertices[origenPadre[indiceActual]]+ " -- " + vertices[indiceActual]);
                }
                if (indiceActual == indiceFinal) {
                    System.out.println("Ya llegamos\n");
                    return;
                }
                for (int i = 0; i < numVertices; i++) {
                    if (matrizAdyacencia[indiceActual][i] !=0 && !visitado[i]) {
                        origenPadre[i] = indiceActual;
                        stack.push(i);
                    }
                }
            }   
        }
    }
    
    
    public void BFS (String verticeInicial, String verticeFinal){
        arcosBFS= new ArrayList<>();
        boolean[] visitado= new boolean[numVertices];
        int[] origenPadre= new int [numVertices];
        for (int i = 0; i<numVertices; i++) origenPadre[i] = -1;
        int indiceInicial= obtenerIndiceVertice(verticeInicial);
        int indiceFinal= obtenerIndiceVertice(verticeFinal);
        if (indiceInicial==-1 || indiceFinal==-1) {
           System.out.println("Uno o ambos de los vertices no existen en este árbol");
            return;
        }
        System.out.println("Recorrido BFS de "+ verticeInicial + " hasta "+verticeFinal);
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        queue.add(indiceInicial);
        visitado[indiceInicial]= true;
        while(!queue.isEmpty()){
            int indiceActual = queue.poll();
            if (origenPadre[indiceActual] !=-1){
                arcosBFS.add(vertices[origenPadre[indiceActual]] + " -- " + vertices[indiceActual]);
            }
            
            if (indiceActual == indiceFinal) {
                System.out.println("Ya llegamos\n");
                return;
            }
            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[indiceActual][i] !=0 && !visitado[i]) {
                    visitado[i]=true;
                    origenPadre[i] = indiceActual;
                    queue.add(i);                
                }
            }
        }
    }
    
    public void despliegaArcos(){
        System.out.println("Despliegue de Arcos: ");
        System.out.println("Arcos recorridos en DFS: " + (arcosDFS != null ? arcosDFS : "No ejecutado"));
        System.out.println("Arcos recorridos en BFS: " + (arcosBFS != null ? arcosBFS : "No ejecutado"));
        int totalArcosGrado=0;
        for (int i=0; i<numVertices;i++){
            for(int j=i; j<numVertices;j++){
                if (matrizAdyacencia[i][j] != 0) {
                    totalArcosGrado++;
                }
            }
        }
        System.out.println("Resumen de estadisticas:");
        System.out.println("Número de vértices en total: "+ numVertices);
        System.out.println("Número total de arcos en el grafo: " + totalArcosGrado);
        if (arcosDFS != null && arcosBFS != null) {
            System.out.println("\n--- Comparación ---");
            if (arcosDFS.size() < arcosBFS.size()) {
                System.out.println("DFS ocupó menos arcos (" + arcosDFS.size() + " vs " + arcosBFS.size() + ")");
            } else if (arcosBFS.size() < arcosDFS.size()) {
                System.out.println("BFS ocupó menos arcos (" + arcosBFS.size() + " vs " + arcosDFS.size() + ")");
            } else {
                System.out.println("Ambos métodos ocuparon el mismo número de arcos (" + arcosDFS.size() + ")");
            }
        }
    }
    
    public String caminoCorto (String verticeInicial, String verticeFinal){
        int inicio= obtenerIndiceVertice(verticeInicial);
        int destino = obtenerIndiceVertice(verticeFinal);
        if (inicio==-1 || destino == -1) {
            return "Uno o ambos vertices no existen en el grafo";
        }
        int[] distancia= new int[numVertices];
        int[] anterior= new int[numVertices];
        boolean[] visitado = new boolean[numVertices];
        java.util.Arrays.fill(distancia, Integer.MAX_VALUE);
        java.util.Arrays.fill(anterior, -1);
        distancia[inicio] = 0;
        for (int paso= 0; paso<numVertices; paso++){
            int actual = -1;
            //Seleccionamos le vertice no visitado con la distancia más corta
            for (int i = 0; i < numVertices; i++) {
                if (!visitado[i] && distancia[i] != Integer.MAX_VALUE
                        && (actual ==-1 || distancia[i] < distancia[actual])) {
                    actual=i;
                }
            }
            if (actual==-1) {
                break; //los vertices son inalcnzables
            }
            if (actual== destino) {
                break; //la distancia al destino ya es definitiva
            }
            visitado[actual] = true;
            for (int vecino = 0; vecino < numVertices; vecino++) {
                int peso = matrizAdyacencia[actual][vecino];
                if (peso>0 && !visitado[vecino] && distancia[actual]<= Integer.MAX_VALUE - peso) {
                    int nuevaDistancia = distancia[actual]+peso;
                    if (nuevaDistancia<distancia[vecino]) {
                        distancia[vecino] = nuevaDistancia;
                        anterior[vecino] = actual;
                    }
                }
            }
        }
        if (distancia[destino] == Integer.MAX_VALUE) {
            return "No existe un camino entre " + verticeInicial +" y " + verticeFinal;
        }
        java.util.ArrayList<String> camino = new ArrayList<>();
        int actual = destino;
        while (actual!=-1) {            
            camino.add(0, vertices[actual]);
            actual= anterior[actual];
        }
        return "El camino más corto de " + verticeInicial + " a " + verticeFinal + " es: " + String.join(" -> ", camino);    }
}

public class Grafos {

    public static void main(String[] args) {
        String texto = cargaArchivo("grafo_salon.txt");
        GrafoListas grafoListas = new GrafoListas(texto);
        grafoListas.display();
        grafoListas.DFS("Yael", "Andres");
        System.out.println();
        grafoListas.BFS("Yael", "Andres");
        System.out.println();
        String caminoCorto = grafoListas.caminoCorto("Yael", "Andres");
        System.out.println(caminoCorto);
    }
    
    public static String cargaArchivo(String ruta){
        String contenido = "";
        try {
            java.io.File archivo = new java.io.File(ruta);
            java.util.Scanner scanner = new java.util.Scanner(archivo);
            while (scanner.hasNextLine()) {
                contenido += scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + ruta);
        }
        return contenido;
    }
    
}
