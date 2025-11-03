package actividad2;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        GestorTareas gestor = new GestorTareas();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        System.out.println("🎮 SIMULADOR DE TASK MANAGER - ACTIVIDAD 2");
        System.out.println("===========================================");
        
        // Generar procesos al inicio
        gestor.generarProcesos();
        
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Ver todos los procesos");
            System.out.println("2. Detener un proceso");
            System.out.println("3. Pausar un proceso");
            System.out.println("4. Reanudar un proceso");
            System.out.println("5. Ver próximo proceso a ejecutar");
            System.out.println("6. Generar nuevos procesos");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            
            // Validar entrada
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
            } else {
                System.out.println("Por favor, ingresa un número válido");
                scanner.nextLine(); // Limpiar entrada inválida
                opcion = 0;
                continue;
            }
            
            switch (opcion) {
                case 1:
                    gestor.mostrarTodo();
                    break;
                    
                case 2:
                    gestor.detenerProceso();
                    break;
                    
                case 3:
                    gestor.pausarProceso();
                    break;
                    
                case 4:
                    gestor.reanudarProceso();
                    break;
                    
                case 5:
                    Proceso proximo = gestor.verProximo();
                    if (proximo != null) {
                        System.out.println("➡️  Próximo proceso: " + proximo);
                    } else {
                        System.out.println("No hay procesos en ejecución");
                    }
                    break;
                    
                case 6:
                    gestor.generarProcesos();
                    break;
                    
                case 7:
                    System.out.println("¡Hasta luego! 👋");
                    break;
                    
                default:
                    System.out.println("Opción no válida. Por favor elige 1-7");
            }
            
        } while (opcion != 7);
        
        scanner.close();
    }
}