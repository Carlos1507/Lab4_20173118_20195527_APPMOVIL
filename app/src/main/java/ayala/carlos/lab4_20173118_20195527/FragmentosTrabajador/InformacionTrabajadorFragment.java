package ayala.carlos.lab4_20173118_20195527.FragmentosTrabajador;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import ayala.carlos.lab4_20173118_20195527.MainActivity;
import ayala.carlos.lab4_20173118_20195527.R;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentInformacionTrabajadorBinding;
import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadorDto;
import ayala.carlos.lab4_20173118_20195527.entities.Countries;
import ayala.carlos.lab4_20173118_20195527.entities.Departments;
import ayala.carlos.lab4_20173118_20195527.entities.Employees;
import ayala.carlos.lab4_20173118_20195527.entities.Locations;
import ayala.carlos.lab4_20173118_20195527.entities.Regions;
import ayala.carlos.lab4_20173118_20195527.services.TrabajadorService;
import ayala.carlos.lab4_20173118_20195527.viewModels.EmpleadoDescargarViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformacionTrabajadorFragment extends Fragment {
    String channelId = "channelHighPriorTrabajador";
    FragmentInformacionTrabajadorBinding binding;
    TrabajadorService trabajadorService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.35:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajadorService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInformacionTrabajadorBinding.inflate(inflater, container, false);

        ocultarLabelsyTextos(binding);
        binding.botonBuscarTrabajador.setOnClickListener(view -> {
            ocultarLabelsyTextos(binding);
            binding.errorID.setText("");
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
                                    binding.errorID.setText("Empleado inexistente");
                                }else {
                                    setearTextos(binding, busquedaTrabajadorDto.getEmpleado());
                                    mostrarLabelsyTextos(binding);
                                    HashMap<String, Object> datosDisplay = obtenerDatosDisplay(binding);
                                    if (busquedaTrabajadorDto.getEmpleado().getMeeting()==1){
                                        if (busquedaTrabajadorDto.getEmpleado().getMeetingDate()!=null &&
                                                !busquedaTrabajadorDto.getEmpleado().getMeetingDate().equals("")){
                                            lanzarNotificacionIfMeeting(busquedaTrabajadorDto.getEmpleado().getMeetingDate());
                                        }else {
                                            lanzarNotificacionIfMeeting("No se ha establecido la fecha y hora");
                                        }
                                    }
                                    binding.descargar.setOnClickListener(view1 -> {
                                        guardarDatosComoJson(datosDisplay, binding);
                                        Toast.makeText(getContext(), "Descarga Exitosa!", Toast.LENGTH_SHORT).show();
                                    });

                                }
                            }else {
                                Log.d("msg-txt","error en el ws");
                            }
                        }
                        @Override
                        public void onFailure(Call<BusquedaTrabajadorDto> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    binding.errorID.setText("Debe ingresar un número");
                }
            }else {
                binding.errorID.setText("Debe ingresar un ID");
            }

        });

        return binding.getRoot();
    }

    public void ocultarLabelsyTextos(FragmentInformacionTrabajadorBinding binding){
        binding.labelNombre.setVisibility(View.INVISIBLE);
        binding.labelApellido.setVisibility(View.INVISIBLE);
        binding.labelCorreo.setVisibility(View.INVISIBLE);
        binding.labelCelular.setVisibility(View.INVISIBLE);
        binding.labelPuesto.setVisibility(View.INVISIBLE);
        binding.labelSalario.setVisibility(View.INVISIBLE);
        binding.labelDepartamento.setVisibility(View.INVISIBLE);
        binding.labelPais.setVisibility(View.INVISIBLE);
        binding.labelRegion.setVisibility(View.INVISIBLE);

        binding.textoNombre.setVisibility(View.INVISIBLE);
        binding.textoApellido.setVisibility(View.INVISIBLE);
        binding.textoCorreo.setVisibility(View.INVISIBLE);
        binding.textoCelular.setVisibility(View.INVISIBLE);
        binding.textoPuesto.setVisibility(View.INVISIBLE);
        binding.textoSalario.setVisibility(View.INVISIBLE);
        binding.textoDepartamento.setVisibility(View.INVISIBLE);
        binding.textoPais.setVisibility(View.INVISIBLE);
        binding.textoRegion.setVisibility(View.INVISIBLE);

        binding.descargar.setVisibility(View.INVISIBLE);
    }
    public void mostrarLabelsyTextos(FragmentInformacionTrabajadorBinding binding){
        binding.labelNombre.setVisibility(View.VISIBLE);
        binding.labelApellido.setVisibility(View.VISIBLE);
        binding.labelCorreo.setVisibility(View.VISIBLE);
        binding.labelCelular.setVisibility(View.VISIBLE);
        binding.labelPuesto.setVisibility(View.VISIBLE);
        binding.labelSalario.setVisibility(View.VISIBLE);
        binding.labelDepartamento.setVisibility(View.VISIBLE);
        binding.labelPais.setVisibility(View.VISIBLE);
        binding.labelRegion.setVisibility(View.VISIBLE);

        binding.textoNombre.setVisibility(View.VISIBLE);
        binding.textoApellido.setVisibility(View.VISIBLE);
        binding.textoCorreo.setVisibility(View.VISIBLE);
        binding.textoCelular.setVisibility(View.VISIBLE);
        binding.textoPuesto.setVisibility(View.VISIBLE);
        binding.textoSalario.setVisibility(View.VISIBLE);
        binding.textoDepartamento.setVisibility(View.VISIBLE);
        binding.textoPais.setVisibility(View.VISIBLE);
        binding.textoRegion.setVisibility(View.VISIBLE);

        binding.descargar.setVisibility(View.VISIBLE);
    }
    public void setearTextos(FragmentInformacionTrabajadorBinding binding, Employees employee){
        binding.textoApellido.setText(employee.getLastName());
        binding.textoCorreo.setText(employee.getEmail());
        binding.textoPuesto.setText(employee.getJobId().getJobTitle());

        String firstName = employee.getFirstName();
        if (firstName!=null || !firstName.equals(""))
            binding.textoNombre.setText(employee.getFirstName());
        else
            binding.textoNombre.setText("Sin nombre");

        String phoneNumber = employee.getPhoneNumber();
        if (phoneNumber!=null || !phoneNumber.equals(""))
            binding.textoCelular.setText(employee.getPhoneNumber());
        else
            binding.textoCelular.setText("Sin celular");

        Float salario = employee.getSalary();
        if (salario!=null)
            binding.textoSalario.setText(String.valueOf(employee.getSalary()));
        else
            binding.textoSalario.setText("Sin información");

        Departments departmentId = employee.getDepartmentId();
        if (departmentId==null){
            binding.textoDepartamento.setText("Sin información");
            binding.textoPais.setText("Sin información");
            binding.textoRegion.setText("Sin información");
        }else {
            binding.textoDepartamento.setText(employee.getDepartmentId().getDepartmentName());
            Locations locationId = employee.getDepartmentId().getLocationId();
            if (locationId==null){
                binding.textoPais.setText("Sin información");
                binding.textoRegion.setText("Sin información");
            }else{
                Countries countryId = employee.getDepartmentId().getLocationId().getCountryId();
                if (countryId==null || countryId.equals("")){
                    binding.textoPais.setText("Sin información");
                    binding.textoRegion.setText("Sin información");
                }else{
                    binding.textoPais.setText(employee.getDepartmentId().getLocationId().getCountryId().getCountryName());
                    Regions regionId = employee.getDepartmentId().getLocationId().getCountryId().getRegionId();
                    if (regionId==null){
                        binding.textoRegion.setText("Sin información");
                    }else {
                        binding.textoRegion.setText(employee.getDepartmentId().getLocationId().getCountryId().getRegionId().getRegionName());
                    }
                }
            }
        }
    }

    public HashMap<String, Object> obtenerDatosDisplay(FragmentInformacionTrabajadorBinding binding){
        HashMap<String, Object> info = new HashMap<>();
        info.put("Id", binding.TextEditID.getText().toString());
        info.put("Nombre",binding.textoNombre.getText().toString());
        info.put("Apellido",binding.textoApellido.getText().toString());
        info.put("Correo",binding.textoCorreo.getText().toString());
        info.put("Celular",binding.textoCelular.getText().toString());
        info.put("Puesto", binding.textoPuesto.getText().toString());
        info.put("Salario", binding.textoSalario.getText().toString());
        info.put("Departamento",binding.textoDepartamento.getText().toString());
        info.put("Pais", binding.textoPais.getText().toString());
        info.put("Region", binding.textoRegion.getText().toString());
        return info;
    }

    public void guardarDatosComoJson(HashMap<String, Object> listaDatos, FragmentInformacionTrabajadorBinding binding){
        String idTrabajador = (String) listaDatos.get("Id");
        Gson gson = new Gson();
        String listaJson = gson.toJson(listaDatos);
        String fileNameJson = "DatosTrabajadorID"+idTrabajador+".txt";
        try(FileOutputStream fileOutputStream = this.getActivity().openFileOutput(fileNameJson, Context.MODE_PRIVATE);
            FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());){
            fileWriter.write(listaJson);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void lanzarNotificacionIfMeeting(String fecha_hora){
        Intent intent = new Intent(getActivity(), MainActivity.class);

        String[] fechaHoras = fecha_hora.split("T");
        String horasMinutos = fechaHoras[1].substring(0,5);
        String horas = horasMinutos.split(":")[0];
        String minutos = horasMinutos.split(":")[1];
        String fechaHoraMostrar = horas + " h";
        if (!minutos.equals("00"))
            fechaHoraMostrar+=(" "+ minutos+" min");


        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), channelId)
                .setSmallIcon(R.drawable.baseline_accessibility_24)
                .setContentTitle("Usted tiene una tutoría pendiente")
                .setContentText("Fecha: "+fechaHoras[0]+ " | Hora: "+fechaHoraMostrar)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManagerCompat.notify(1, builder.build());
        }
    }
}