package ayala.carlos.lab4_20173118_20195527.services;

import java.util.List;

import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadorDto;
import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadoresDto;
import ayala.carlos.lab4_20173118_20195527.dtos.RespuestaDto;
import ayala.carlos.lab4_20173118_20195527.entities.Employees;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface TrabajadorService {
    @GET("/trabajador/verInfo")
    Call<BusquedaTrabajadorDto> buscarTrabajadorPorId(@Query("id") int id);

    @GET("/trabajador/verTodo")
    Call<BusquedaTrabajadoresDto> buscarTrabajadorTodos();

    @PUT("/tutor/asignarTutoria")
    Call<RespuestaDto> asignar(@Query("idEmployee") int idTrab, @Query("idTutor") int idTutor);
}
