package modelo;

public class EmergenciaVehiculo {
    private int idEmergencia;
    private int idVehiculo;

    public EmergenciaVehiculo() {}

    public EmergenciaVehiculo(int idEmergencia, int idVehiculo) {
        this.idEmergencia = idEmergencia;
        this.idVehiculo = idVehiculo;
    }

    // Getters y Setters
    public int getIdEmergencia() { return idEmergencia; }
    public void setIdEmergencia(int idEmergencia) { this.idEmergencia = idEmergencia; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }
}
