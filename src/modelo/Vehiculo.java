package modelo;

public class Vehiculo {
    private int id;
    private String patente;
    private String tipo;
    private String estado;

    public Vehiculo() {}
    public Vehiculo(int id, String patente, String tipo, String estado) {
        this.id = id; this.patente = patente; this.tipo = tipo; this.estado = estado;
    }
    // compat constructor 3-arg
    public Vehiculo(int id, String patente, String tipo) { this(id, patente, tipo, ""); }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override public String toString() { return patente + " - " + tipo + (estado!=null && !estado.isEmpty() ? " ("+estado+")" : ""); }
}
