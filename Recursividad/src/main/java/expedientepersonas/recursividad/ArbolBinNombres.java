package expedientepersonas.recursividad;

class Nodo {
    String valor;
    Nodo izquierda;
    Nodo derecha;
    public Nodo(String valor) {
        this.valor = valor;
        this.izquierda = null;
        this.derecha = null;
    }
}

class ArbolBinario {
    Nodo raiz;
    public ArbolBinario() {
        this.raiz = null;
    }
    public void insertar(String valor) {
        raiz = insertarRecursivo(raiz, valor);
    }
    private Nodo insertarRecursivo(Nodo nodo, String valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }
        int resultado = valor.compareTo(nodo.valor);
        if (resultado < 0) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, valor);
        } else if (resultado > 0) {
            nodo.derecha = insertarRecursivo(nodo.derecha, valor);
        }
        return nodo;
    }
    public void preOrden() {
        preOrdenRecursivo(raiz);
    }
    
    private void preOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            preOrdenRecursivo(nodo.izquierda);
            preOrdenRecursivo(nodo.derecha);
        }
    }
    
    public void inOrden() {
        inOrdenRecursivo(raiz);
    }

    public void inOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            preOrdenRecursivo(nodo.derecha);            
            preOrdenRecursivo(nodo.izquierda);
        }
    }
}

public class ArbolBinNombres {
    public static void main(String[] args) {
        String[] personas = {"Alan Aldama Andre", "Isabel Domíngues Ochoa",
        "Ernesto Ozuna Ramirez", "Ada Pino López", "Bruno Díaz Henández", "Luis Caro Durazo"};
        ArbolBinario arbolB = new ArbolBinario();
        for(String persona: personas) {
           arbolB.insertar(persona);
        }
        
        System.out.println();
        System.out.println("Orden alfatico A-Z");
        System.out.println();
        
        System.out.println();
        System.out.println("Orden Alfabetico");
        
        arbolB.preOrden();
        System.out.println();
        arbolB.inOrden();
    }
}
