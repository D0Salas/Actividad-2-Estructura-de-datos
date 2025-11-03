package actividad2;

import java.util.Random;

public class GestorTareas {
    // Lista para procesos en ejecución (ordenados por prioridad)
    private Proceso[] procesosEjecucion = new Proceso[50];
    private int cantidadEjecucion = 0;
    
    // Lista para procesos en espera
    private Proceso[] procesosEspera = new Proceso[50];
    private int cantidadEspera = 0;
    
    // Lista para historial de procesos parados
    private Proceso[] procesosParados = new Proceso[50];
    private int cantidadParados = 0;
    
    private Random random = new Random();
    private String[] nombresProcesos = {
        "Sistema", "Explorador", "Chrome", "Word", "Excel", 
        "PowerPoint", "Antivirus", "Sonido", "Impresora", "Calculadora",
        "BlocNotas", "Paint", "Reproductor", "Navegador", "Actualizaciones"
    };
    
    // Agregar proceso a ejecución (ordenado por prioridad)
    public void agregarEjecucion(Proceso proceso) {
        if (cantidadEjecucion >= procesosEjecucion.length) {
            System.out.println("No hay espacio para más procesos en ejecución");
            return;
        }
        
        if (cantidadEjecucion == 0) {
            procesosEjecucion[0] = proceso;
            cantidadEjecucion++;
            return;
        }
        
        // Encontrar posición correcta según prioridad
        int i = cantidadEjecucion - 1;
        while (i >= 0 && procesosEjecucion[i].prioridad > proceso.prioridad) {
            procesosEjecucion[i + 1] = procesosEjecucion[i];
            i--;
        }
        procesosEjecucion[i + 1] = proceso;
        cantidadEjecucion++;
    }
    
    // Quitar el proceso de mayor prioridad
    public Proceso quitarEjecucion() {
        if (cantidadEjecucion == 0) return null;
        
        Proceso proceso = procesosEjecucion[0];
        // Mover todos los procesos una posición hacia adelante
        for (int i = 0; i < cantidadEjecucion - 1; i++) {
            procesosEjecucion[i] = procesosEjecucion[i + 1];
        }
        procesosEjecucion[cantidadEjecucion - 1] = null;
        cantidadEjecucion--;
        return proceso;
    }
    
    // Ver el próximo proceso a ejecutar
    public Proceso verProximo() {
        if (cantidadEjecucion == 0) return null;
        return procesosEjecucion[0];
    }
    
    // Agregar a cola de espera
    public void agregarEspera(Proceso proceso) {
        if (cantidadEspera >= procesosEspera.length) {
            System.out.println("No hay espacio para más procesos en espera");
            return;
        }
        procesosEspera[cantidadEspera] = proceso;
        cantidadEspera++;
    }
    
    // Quitar de cola de espera (primero en entrar)
    public Proceso quitarEspera() {
        if (cantidadEspera == 0) return null;
        
        Proceso proceso = procesosEspera[0];
        // Mover todos los procesos una posición hacia adelante
        for (int i = 0; i < cantidadEspera - 1; i++) {
            procesosEspera[i] = procesosEspera[i + 1];
        }
        procesosEspera[cantidadEspera - 1] = null;
        cantidadEspera--;
        return proceso;
    }
    
    // Agregar a historial de parados (como pila - último arriba)
    public void agregarParado(Proceso proceso) {
        if (cantidadParados >= procesosParados.length) {
            System.out.println("No hay espacio para más procesos parados");
            return;
        }
        
        // Mover todos los procesos una posición hacia atrás
        for (int i = cantidadParados; i > 0; i--) {
            procesosParados[i] = procesosParados[i - 1];
        }
        procesosParados[0] = proceso;
        cantidadParados++;
    }
    
    // Generar procesos aleatorios
    public void generarProcesos() {
        for (int i = 0; i < 20; i++) {
            int pid = 1000 + random.nextInt(9000);
            String nombre = nombresProcesos[random.nextInt(nombresProcesos.length)];
            int prioridad = 1 + random.nextInt(5); // 1=alta, 5=baja
            Proceso p = new Proceso(pid, nombre, prioridad, "EJECUTANDO");
            agregarEjecucion(p);
        }
        System.out.println("✓ Se generaron 20 procesos aleatorios");
    }
    
    // Mostrar todos los procesos
    public void mostrarTodo() {
        System.out.println("\n=== PROCESOS EN EJECUCIÓN (" + cantidadEjecucion + ") ===");
        if (cantidadEjecucion == 0) {
            System.out.println("No hay procesos en ejecución");
        } else {
            for (int i = 0; i < cantidadEjecucion; i++) {
                System.out.println((i+1) + ". " + procesosEjecucion[i]);
            }
        }
        
        System.out.println("\n=== PROCESOS EN ESPERA (" + cantidadEspera + ") ===");
        if (cantidadEspera == 0) {
            System.out.println("No hay procesos en espera");
        } else {
            for (int i = 0; i < cantidadEspera; i++) {
                System.out.println((i+1) + ". " + procesosEspera[i]);
            }
        }
        
        System.out.println("\n=== HISTORIAL DE PROCESOS PARADOS (" + cantidadParados + ") ===");
        if (cantidadParados == 0) {
            System.out.println("No hay procesos parados");
        } else {
            for (int i = 0; i < cantidadParados; i++) {
                System.out.println((i+1) + ". " + procesosParados[i]);
            }
        }
    }
    
    // Detener un proceso
    public void detenerProceso() {
        Proceso p = quitarEjecucion();
        if (p != null) {
            p.estado = "PARADO";
            agregarParado(p);
            System.out.println("✓ Proceso detenido: " + p.nombre);
        } else {
            System.out.println("✗ No hay procesos para detener");
        }
    }
    
    // Pausar un proceso
    public void pausarProceso() {
        Proceso p = quitarEjecucion();
        if (p != null) {
            p.estado = "PAUSADO";
            agregarEspera(p);
            System.out.println("⏸️  Proceso pausado: " + p.nombre);
        } else {
            System.out.println("✗ No hay procesos para pausar");
        }
    }
    
    // Reanudar un proceso
    public void reanudarProceso() {
        Proceso p = quitarEspera();
        if (p != null) {
            p.estado = "EJECUTANDO";
            agregarEjecucion(p);
            System.out.println("▶️  Proceso reanudado: " + p.nombre);
        } else {
            System.out.println("✗ No hay procesos para reanudar");
        }
    }
    
    // Métodos para obtener contadores (útiles para la interfaz)
    public int getCantidadEjecucion() {
        return cantidadEjecucion;
    }
    
    public int getCantidadEspera() {
        return cantidadEspera;
    }
    
    public int getCantidadParados() {
        return cantidadParados;
    }
}