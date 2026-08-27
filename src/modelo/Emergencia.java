package modelo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Emergencia {
    private int id;
    private Date fecha;
    private Time hora;
    private String direccion;
    private TipoEmergencia tipo;
    private String resultado;
    private List<Personal> personales;
    private List<Vehiculo> vehiculos;

    public Emergencia() {}

    public Emergencia(int id, Date fecha, Time hora, String direccion, TipoEmergencia tipo, String resultado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
        this.tipo = tipo;
        this.resultado = resultado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public TipoEmergencia getTipo() { return tipo; }
    public void setTipo(TipoEmergencia tipo) { this.tipo = tipo; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public List<Personal> getPersonales() { return personales; }
    public void setPersonales(List<Personal> personales) { this.personales = personales; }

    public List<Vehiculo> getVehiculos() { return vehiculos; }
    public void setVehiculos(List<Vehiculo> vehiculos) { this.vehiculos = vehiculos; }
}
