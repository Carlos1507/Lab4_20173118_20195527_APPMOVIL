package ayala.carlos.lab4_20173118_20195527.dtos;

import java.util.List;

import ayala.carlos.lab4_20173118_20195527.entities.Employees;

public class BusquedaTrabajadoresDto {
    private List<Employees> empleado;
    private String respuesta;


    public List<Employees> getEmpleados() {
        return empleado;
    }

    public void setEmpleados(List<Employees> empleados) {
        this.empleado = empleados;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
