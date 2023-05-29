package ayala.carlos.lab4_20173118_20195527.dtos;

import ayala.carlos.lab4_20173118_20195527.entities.Employees;

public class BusquedaTrabajadorDto {
    private Employees empleado;
    private String respuesta;


    public Employees getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Employees empleado) {
        this.empleado = empleado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
