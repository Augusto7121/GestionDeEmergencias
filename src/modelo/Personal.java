package modelo;

public class Personal {
    private int id;
    private String nombre;
    private Jerarquia jerarquia;

    public Personal() {}
    public Personal(int id, String nombre, Jerarquia jerarquia) {
        this.id = id; this.nombre = nombre; this.jerarquia = jerarquia;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Jerarquia getJerarquia() { return jerarquia; }
    public void setJerarquia(Jerarquia jerarquia) { this.jerarquia = jerarquia; }

    @Override public String toString() { return nombre + (jerarquia != null ? " (" + jerarquia.getNombre()+")" : ""); }
}
