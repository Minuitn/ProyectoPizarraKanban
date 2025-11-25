
package PizarraKanban;

/**
 *
 * @author minu
 */
public class Tarea {
    private int id;
    private String descripcion;
    private Estado estado;
    private String prioridad;
    private String responsable;

    public Tarea(int id, String descripcion, Estado estado, String prioridad, String responsable) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.responsable = responsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

  
    
    
}
