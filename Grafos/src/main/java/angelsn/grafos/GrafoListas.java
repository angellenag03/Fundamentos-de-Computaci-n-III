package angelsn.grafos;

import angelsn.grafos.Nodo;
import java.util.ArrayList;

public class GrafoListas {
    ArrayList<Nodo> nodos;
    
    public GrafoListas(String texto) {
        creaListaVertices(texto);
        agregaAristas(texto);
    }
    
    private void creaListaVertices(String texto) {
        String[] lineas = texto.split("\\R");
        nodos = new ArrayList<>();
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
    private void agregarVertice(String nombre) {
        for (Nodo nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return; // El vértice ya existe
            }
        }
        nodos.add(new Nodo(nombre));
    }
    
    public void display() {
        for (Nodo nodo : nodos) {
            nodo.display();
        }
    }
    
    private void agregaAristas(String texto) {
        String[] lineas = texto.split("\\R");
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            int peso = 0;
            int primeracomilla = arista.indexOf("\"");
            int segundacomilla = arista.indexOf("\"", primeracomilla+1);
            if (primeracomilla>=0 && segundacomilla>primeracomilla) {
                String valorPeso = arista.substring(primeracomilla+1, segundacomilla);
                peso = Integer.parseInt(valorPeso);
            }
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                Nodo nodo1 = new Nodo(extremos[0].trim());
                Nodo nodo2 = new Nodo(extremos[1].trim());
                if (nodo1 != null && nodo2 != null) {
                    agregarAdyacente(nodo1, nodo2, peso);
                    agregarAdyacente(nodo2, nodo1, peso); // Para grafos no dirigidos
                }
            }
        }
    }
    
    private void agregarAdyacente(Nodo nodoOrigen, Nodo nodoDestino, int peso) {
        for (Nodo n : nodos) {
            if (n.getNombre().equals(nodoOrigen.getNombre())) {
                n.agregarAdyacente(nodoDestino, peso);
                return;
            }
        }
    }
    
    private Nodo obtenerNodoPorNombre(String nombre) {
        for (Nodo nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null; // Retorna null si no se encuentra el nodo
    } 
    
    public void DFS(String verticeInicial, String VerticeFinal){
        int numVertices = nodos.size();
        boolean[] visitado = new boolean[numVertices];
        Nodo nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodo nodoFinal = obtenerNodoPorNombre(VerticeFinal);

        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        java.util.Stack<Nodo> stack = new java.util.Stack<>();
        stack.push(nodoInicial);
        while (!stack.isEmpty()) {
            Nodo nodoActual = stack.pop();
            if (!nodoActual.isVisitado()) {
                nodoActual.setVisitado(true);
                System.out.print(nodoActual.getNombre() + "->");
                if (nodoActual.getNombre().equals(nodoFinal.getNombre())) {
                    System.out.println("\nSe ha alcanzado el vértice final.");
                    return; 
                }
            }
            // Agregar vecinos no visitados al stack
            for (Nodo vecino : nodoActual.getAdyacentes()) {
                if (!vecino.isVisitado()) {
                    stack.push(vecino);
                }
            }
        }
    }
    
    public void BFS(String verticeInicial, String VerticeFinal) {
        Nodo nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodo nodoFinal = obtenerNodoPorNombre(VerticeFinal);
        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        java.util.Queue<Nodo> queue = new java.util.LinkedList<>();
        queue.add(nodoInicial);
        while (!queue.isEmpty()) {
            Nodo nodoActual = obtenerNodoPorNombre(queue.poll().getNombre());
            if (!nodoActual.isVisitado()) {
                nodoActual.setVisitado(true);
                System.out.print(nodoActual.getNombre() + "->");
                if (nodoActual.getNombre().equals(nodoFinal.getNombre())) {
                    System.out.println("\nSe ha alcanzado el vértice final.");
                    return;
                }
            }
            for (Nodo vecino : nodoActual.getAdyacentes()) {
                Nodo vecinoReal = obtenerNodoPorNombre(vecino.getNombre());
                if (!vecinoReal.isVisitado()) {
                    queue.add(vecinoReal);
                }
            }
        }
    }
    
    public String caminoCorto(String verticeInicial, String verticeFinal) {
        int numVertices = nodos.size();
        Nodo nodoInicio = obtenerNodoPorNombre(verticeInicial);
        Nodo nodoDestino = obtenerNodoPorNombre(verticeFinal);
        if (nodoInicio == null || nodoDestino == null) {
            return "Uno o ambos vértices no existen en el grafo";
        }
        int inicio = nodos.indexOf(nodoInicio);
        int destino = nodos.indexOf(nodoDestino);
        int[] distancia = new int[numVertices];
        int[] anterior = new int[numVertices];
        boolean[] visitado = new boolean[numVertices];
        java.util.Arrays.fill(distancia, Integer.MAX_VALUE);
        java.util.Arrays.fill(anterior, -1);
        distancia[inicio] = 0;
        for (int paso = 0; paso < numVertices; paso++) {
            int actual = -1;
            for (int i = 0; i < numVertices; i++) {
                if (!visitado[i] && distancia[i] != Integer.MAX_VALUE
                        && (actual == -1 || distancia[i] < distancia[actual])) {
                    actual = i;
                }
            }
            if (actual == -1) {
                break; 
            }
            if (actual == destino) {
                break; 
            }
            visitado[actual] = true;
            Nodo nodoActual = nodos.get(actual);
            for (Nodo vecino : nodoActual.getAdyacentes()) {
                Nodo vecinoReal = obtenerNodoPorNombre(vecino.getNombre());
                int indiceVecino = nodos.indexOf(vecinoReal);
                int peso = vecino.getPeso();

                if (indiceVecino != -1 && peso > 0 && !visitado[indiceVecino]
                        && distancia[actual] <= Integer.MAX_VALUE - peso) {
                    int nuevaDistancia = distancia[actual] + peso;
                    if (nuevaDistancia < distancia[indiceVecino]) {
                        distancia[indiceVecino] = nuevaDistancia;
                        anterior[indiceVecino] = actual;
                    }
                }
            }
        }
        if (distancia[destino] == Integer.MAX_VALUE) {
            return "No existe un camino entre " + verticeInicial + " y " + verticeFinal;
        }
        java.util.ArrayList<String> camino = new ArrayList<>();
        int actual = destino;
        while (actual != -1) {
            camino.add(0, nodos.get(actual).getNombre());
            actual = anterior[actual];
        }
        return "El camino más corto de " + verticeInicial + " a " + verticeFinal + " es: "
                + String.join(" -> ", camino) + " (Costo total: " + distancia[destino] + ")";
    }
}
