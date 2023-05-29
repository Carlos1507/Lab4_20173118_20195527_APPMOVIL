package ayala.carlos.lab4_20173118_20195527.FragmentosTrabajador;

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
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentDescargarHorariosBinding;
import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadorDto;
import ayala.carlos.lab4_20173118_20195527.entities.Employees;
import ayala.carlos.lab4_20173118_20195527.services.TrabajadorService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DescargarHorariosFragment extends Fragment {
    FragmentDescargarHorariosBinding binding;
    TrabajadorService trabajadorService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajadorService.class);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDescargarHorariosBinding.inflate(inflater, container, false);
        binding.botonBuscarTrabajador.setOnClickListener(view -> {
            binding.errorIDDescargar.setText("");
            String id = binding.TextEditID.getText().toString();
            if (!id.equals("")){
                try {
                    int idTrab = Integer.parseInt(id);
                    trabajadorService.buscarTrabajadorPorId(idTrab).enqueue(new Callback<BusquedaTrabajadorDto>() {
                        @Override
                        public void onResponse(Call<BusquedaTrabajadorDto> call, Response<BusquedaTrabajadorDto> response) {
                            if (response.isSuccessful()){
                                BusquedaTrabajadorDto busquedaTrabajadorDto = response.body();
                                if (busquedaTrabajadorDto.getRespuesta().equals("no encontrado")){
                                    binding.errorIDDescargar.setText("Empleado inexistente");
                                }else{
                                    Employees trabajador = busquedaTrabajadorDto.getEmpleado();
                                    if (trabajador.getMeeting()==1){
                                        Context context = getActivity();
                                        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                                                ContextCompat.checkSelfPermission(getActivity(),permission) == PackageManager.PERMISSION_GRANTED){
                                            String fileName = "horarios.jpg";
                                            String endPoint = "https://i.pinimg.com/564x/4e/8e/a5/4e8ea537c896aa277e6449bdca6c45da.jpg";
                                            Uri downloadUri = Uri.parse(endPoint);
                                            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                                            request.setAllowedOverRoaming(false);
                                            request.setTitle(fileName);
                                            request.setMimeType("image/jpg");
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator+fileName);
                                            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                            dm.enqueue(request);
                                        }else {
                                            ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted->{
                                                if (isGranted){
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                                                            ContextCompat.checkSelfPermission(getActivity(),permission) == PackageManager.PERMISSION_GRANTED){
                                                        String fileName = "horarios.jpg";
                                                        String endPoint = "https://i.pinimg.com/564x/4e/8e/a5/4e8ea537c896aa277e6449bdca6c45da.jpg";
                                                        Uri downloadUri = Uri.parse(endPoint);
                                                        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                                                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                                                        request.setAllowedOverRoaming(false);
                                                        request.setTitle(fileName);
                                                        request.setMimeType("image/jpg");
                                                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator+fileName);
                                                        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                                        dm.enqueue(request);
                                                    }
                                                }else {
                                                    Log.e("msg-test", "Permiso denegado");
                                                }
                                            });
                                        }
                                    }else {
                                        binding.errorIDDescargar.setText("No cuenta con tutorías pendientes");
                                    }
                                }
                            }else {
                                binding.errorIDDescargar.setText("No encontrado");
                            }
                        }

                        @Override
                        public void onFailure(Call<BusquedaTrabajadorDto> call, Throwable t) {

                        }
                    });
                }catch (Exception e){
                    binding.errorIDDescargar.setText("Debe ingresar un número");
                }
            }else {
                binding.errorIDDescargar.setText("Debe ingresar un ID");
            }
        });
        return binding.getRoot();
    }
}