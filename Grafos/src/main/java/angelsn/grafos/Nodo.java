package angelsn.grafos;

import java.util.ArrayList;        

public class Nodo {
    String nombre;
    ArrayList<Nodo> adyacentes;
    int peso;
    boolean visitado;
    
    public Nodo (String nombre){
        this.nombre = nombre;
        this.peso=0;
        this.visitado = false;
        this.adyacentes= new ArrayList<>();
    }
    
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
    
    public boolean isVisitado() {
        return visitado;
    }
    
    public void agregarAdyacente(Nodo nodo, int peso) {
        adyacentes.add(nodo);
        nodo.peso = peso;
    }
    
    public void display(){
        System.out.println(nombre);
        System.out.println("| Adyacentes: ");
        for (Nodo nodo : adyacentes) {
            System.out.println(nodo.nombre + ": "+ nodo.peso + " ");
        }
        System.out.println();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public ArrayList<Nodo> getAdyacentes() {
        return adyacentes;
    }
    
    public int getPeso() {
        return peso;
    }
    
}
