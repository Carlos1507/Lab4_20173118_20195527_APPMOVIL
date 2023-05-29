package ayala.carlos.lab4_20173118_20195527.FragmentosTutor;

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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ayala.carlos.lab4_20173118_20195527.FragmentosTrabajador.TrabajadorFragment;
import ayala.carlos.lab4_20173118_20195527.MainActivity;
import ayala.carlos.lab4_20173118_20195527.R;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentBuscarTrabajadorBinding;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentTutorBinding;
import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadorDto;
import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadoresDto;
import ayala.carlos.lab4_20173118_20195527.entities.Countries;
import ayala.carlos.lab4_20173118_20195527.entities.Departments;
import ayala.carlos.lab4_20173118_20195527.entities.Employees;
import ayala.carlos.lab4_20173118_20195527.entities.Locations;
import ayala.carlos.lab4_20173118_20195527.entities.Regions;
import ayala.carlos.lab4_20173118_20195527.services.TrabajadorService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TutorFragment extends Fragment {
    FragmentTutorBinding binding;

    String channelId2 = "channelHighPriorTutor";


    TrabajadorService trabajadorService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajadorService.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   //     binding = FragmentTutorBinding.inflate(inflater, container, false);
        binding = FragmentTutorBinding.inflate(inflater, container, false);
        lanzarNotificacionInicio();
        NavController navController = NavHostFragment.findNavController(TutorFragment.this);
        //AL HACER CLICK EN EL BOTON DESCARGAR LISTA DE TRABAJADORES


        binding.botonDescargar.setOnClickListener(view -> {
            trabajadorService.buscarTrabajadorTodos().enqueue(new Callback<BusquedaTrabajadoresDto>() {
                @Override
                public void onResponse(Call<BusquedaTrabajadoresDto> call, Response<BusquedaTrabajadoresDto> response) {
                    BusquedaTrabajadoresDto busquedaTrabajadoresDto = response.body();
                    HashMap<String, HashMap<String, Object>> datosDisplay = obtenerDatosDisplay2(busquedaTrabajadoresDto.getEmpleados());
                    guardarDatosComoJson2(datosDisplay, binding);
                    Toast.makeText(getContext(), "Descarga Exitosa!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<BusquedaTrabajadoresDto> call, Throwable t) {

                }
            });
        });

        /*
        binding.botonDescargar.setOnClickListener(view -> {
            trabajadorService.buscarTrabajadorTodos().enqueue(new Callback<List<Employees>>() {
                @Override
                public void onResponse(Call<List<Employees>> call, Response<List<Employees>> response) {
                    System.out.println("tamaño + "+response.body().size());
                    List<Employees> listaTrabajadores = response.body();
                    HashMap<String, HashMap<String, Object>> datosDisplay = obtenerDatosDisplay2(listaTrabajadores);
                    guardarDatosComoJson2(datosDisplay, binding);
                    Toast.makeText(getContext(), "Descarga Exitosa!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<Employees>> call, Throwable t) {

                }
            });
        });

         */
         //AL HACER CLICK EN EL BOTON BUSCAR TRABAJADOR
        binding.botonBuscar.setOnClickListener(view -> {
            navController.navigate(R.id.action_tutorFragment_to_buscarTrabajadorFragment);
        });

        //AL HACER CLICK EN EL BOTON ASIGNAR TUTORIA
        binding.botonAsignar.setOnClickListener(view -> {
            navController.navigate(R.id.action_tutorFragment_to_asignarTutoriaFragment);
        });

        return binding.getRoot();
    }


    public void lanzarNotificacionInicio(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), channelId2)
                .setSmallIcon(R.drawable.baseline_accessibility_24)
                .setContentTitle("Tutor")
                .setContentText("Está entrando en modo Tutor")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManagerCompat.notify(1, builder.build());
        }
    }

    public HashMap<String, HashMap<String, Object>> obtenerDatosDisplay2(List<Employees> employees){

        HashMap<String, HashMap<String, Object>> diccionario = new HashMap<>();

        for(int i = 0; i < employees.size(); i++) {
            HashMap<String, Object> info = new HashMap<>();

            info.put("Id", employees.get(i).getEmployeeId().toString());
            String firstName = employees.get(i).getFirstName();
            if (firstName!=null || !firstName.equals(""))
                info.put("Nombre", employees.get(i).getEmployeeId().toString());
            else
                info.put("Nombre", "Sin nombre");

            info.put("Apellido",employees.get(i).getLastName().toString());
            info.put("Correo", employees.get(i).getEmail().toString());

            String phoneNumber = employees.get(i).getPhoneNumber();
            if (phoneNumber!=null || !phoneNumber.equals(""))
                info.put("Celular",employees.get(i).getPhoneNumber().toString());
            else
                info.put("Celular", "Sin celular");

            info.put("Puesto", employees.get(i).getJobId().getJobTitle().toString());

            Float salario = employees.get(i).getSalary();
            if (salario!=null)
                info.put("Salario", String.valueOf(employees.get(i).getSalary()));
            else
                info.put("Salario", "Sin informacion");

            Departments departmentId = employees.get(i).getDepartmentId();
            if (departmentId==null){
                info.put("Departamento", "Sin informacion");
                info.put("Pais", "Sin informacion");
                info.put("Region", "Sin informacion");
            }else {
                info.put("Departamento", employees.get(i).getDepartmentId().getDepartmentName().toString());
                Locations locationId = employees.get(i).getDepartmentId().getLocationId();
                if (locationId==null){
                    info.put("Pais", "Sin informacion");
                    info.put("Region", "Sin informacion");
                }else{
                    Countries countryId = employees.get(i).getDepartmentId().getLocationId().getCountryId();
                    if (countryId==null || countryId.equals("")){
                        info.put("Pais", "Sin informacion");
                        info.put("Region", "Sin informacion");
                    }else{
                        info.put("Pais", employees.get(i).getDepartmentId().getLocationId().getCountryId().getCountryName().toString());
                        Regions regionId = employees.get(i).getDepartmentId().getLocationId().getCountryId().getRegionId();
                        if (regionId==null){
                            info.put("Region", "Sin informacion");
                        }else {
                            info.put("Region", employees.get(i).getDepartmentId().getLocationId().getCountryId().getRegionId().getRegionName().toString());
                        }
                    }
                }
            }
            diccionario.put("Trabajador" + String.valueOf(i), info);
        }
        return diccionario;
    }

    public void guardarDatosComoJson2(HashMap<String, HashMap<String, Object>> listaDatos, FragmentTutorBinding binding){
        Gson gson = new Gson();
        String listaJson = gson.toJson(listaDatos);
        String fileNameJson = "listaDeTrabajadores.txt";
        try(FileOutputStream fileOutputStream = this.getActivity().openFileOutput(fileNameJson, Context.MODE_PRIVATE);
            FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());){
            fileWriter.write(listaJson);
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}