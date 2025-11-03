package actividad2;

public class Proceso {
    public int pid;
    public String nombre;
    public int prioridad;
    public String estado;
    
    public Proceso(int pid, String nombre, int prioridad, String estado) {
        this.pid = pid;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.estado = estado;
    }
    
    public String toString() {
        return "PID: " + pid + " - " + nombre + " - Prioridad: " + prioridad + " - Estado: " + estado;
    }
}