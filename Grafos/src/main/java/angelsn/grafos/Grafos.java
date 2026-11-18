package angelsn.grafos;

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
