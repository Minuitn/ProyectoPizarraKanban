
package PizarraKanban;

// Modelo simple de Tarea (estado como String para casar con BD)
public class Tarea {
    private int id;
    private String descripcion;
    private String estado;     // POR_HACER, EN_PROGRESO, COMPLETADO
    private String prioridad;  // ALTA, MEDIA, BAJA
    private String responsable;

    public Tarea(int id, String descripcion, String estado, String prioridad, String responsable) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.responsable = responsable;
    }

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
}

