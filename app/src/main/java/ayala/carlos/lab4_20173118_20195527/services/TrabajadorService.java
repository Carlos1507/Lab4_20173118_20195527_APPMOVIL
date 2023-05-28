package ayala.carlos.lab4_20173118_20195527.services;

import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadorDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrabajadorService {
    @GET("/trabajador/verInfo")
    Call<BusquedaTrabajadorDto> buscarTrabajadorPorId(@Query("id") int id);
}
