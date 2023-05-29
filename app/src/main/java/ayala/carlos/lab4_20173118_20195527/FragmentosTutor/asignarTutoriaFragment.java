package ayala.carlos.lab4_20173118_20195527.FragmentosTutor;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import ayala.carlos.lab4_20173118_20195527.R;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentAsignarTutoriaBinding;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentDescargarHorariosBinding;
import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadorDto;
import ayala.carlos.lab4_20173118_20195527.dtos.RespuestaDto;
import ayala.carlos.lab4_20173118_20195527.entities.Employees;
import ayala.carlos.lab4_20173118_20195527.services.TrabajadorService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class asignarTutoriaFragment extends Fragment {

    FragmentAsignarTutoriaBinding binding;

    TrabajadorService trabajadorService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajadorService.class);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAsignarTutoriaBinding.inflate(inflater, container, false);

        binding.btnasignar.setOnClickListener(view -> {
            binding.errorIDDescargar2.setText("");
            String idTutor = binding.idTextTutor.getText().toString();
            String idEmpleado = binding.idTextEmpleado.getText().toString();

            if ((!idTutor.equals("")) && (!idEmpleado.equals(""))){
                try {
                    int idTutorInt = Integer.parseInt(idTutor);
                    int idEmpleadoInt = Integer.parseInt(idEmpleado);

                    trabajadorService.asignar(idEmpleadoInt, idTutorInt).enqueue(new Callback<RespuestaDto>() {
                        @Override
                        public void onResponse(Call<RespuestaDto> call, Response<RespuestaDto> response) {
                            if (response.isSuccessful()){
                                RespuestaDto respuestaDto = response.body();
                                binding.errorIDDescargar2.setText(respuestaDto.getEstado());
                            }else {
                            binding.errorIDDescargar2.setText("No encontrado");
                            }
                        }
                        @Override
                        public void onFailure(Call<RespuestaDto> call, Throwable t) {
                        }
                    });

                }catch (Exception e){
                    binding.errorIDDescargar2.setText("Debe ingresar un número");
                }
            }else{
                binding.errorIDDescargar2.setText("Debe ingresar un ID");
            }

        });

        return binding.getRoot();
    }
}