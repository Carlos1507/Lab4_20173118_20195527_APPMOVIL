package ayala.carlos.lab4_20173118_20195527.dtos;

import java.util.List;

import ayala.carlos.lab4_20173118_20195527.entities.Employees;

public class BusquedaTrabajadoresDto {
    private List<Employees> empleados;
    private String respuesta;


    public List<Employees> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Employees> empleados) {
        this.empleados = empleados;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
