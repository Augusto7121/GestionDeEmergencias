package modelo;

public class EmergenciaPersonal {
    private int idEmergencia;
    private int idPersonal;
    private String rol;

    public EmergenciaPersonal() {}

    public EmergenciaPersonal(int idEmergencia, int idPersonal, String rol) {
        this.idEmergencia = idEmergencia;
        this.idPersonal = idPersonal;
        this.rol = rol;
    }

    // Getters y Setters
    public int getIdEmergencia() { return idEmergencia; }
    public void setIdEmergencia(int idEmergencia) { this.idEmergencia = idEmergencia; }

    public int getIdPersonal() { return idPersonal; }
    public void setIdPersonal(int idPersonal) { this.idPersonal = idPersonal; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

