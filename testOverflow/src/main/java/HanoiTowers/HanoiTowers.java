
package HanoiTowers;

import java.util.Scanner;

/**
 *
 * @author angelsn
 */
public class HanoiTowers {
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        menuPrincipal();
    }
    
    static int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.println("Ingresa un número válido: ");
            sc.next();
        }
        return sc.nextInt();
    }
    
    static void menuPrincipal(){
        int opcion;
        do {            
            System.out.println("\n========= TORRES DE HANOI ==========");
            System.out.println(""); // número de discos
            System.out.println("1. Elegir número de discos (3-8)");
            System.out.println("2. Jugar manualmente");
            System.out.println("3. Mostrar solución automática");
            System.out.println("4. Salir");
            System.out.println("Elige una opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    elegirNumeroDiscos();
                    break;
                case 2:
                    jugarManual();
                    break;
                case 3:
                    mostrarSolucion();
                    break;
                case 4:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
    }
    
    static void elegirNumeroDiscos(){
        int n;
        do {            
            System.out.println("Ingresa el número de discos (3-8)");
            n = leerEntero();
            if (n > 3 || n > 8) {
                
            }
        } while (n < 3 || n > 8);
    }
    static void jugarManual(){}
    static void mostrarSolucion(){}
}
