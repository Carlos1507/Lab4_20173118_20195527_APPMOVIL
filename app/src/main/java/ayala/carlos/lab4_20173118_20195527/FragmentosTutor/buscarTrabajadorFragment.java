package ayala.carlos.lab4_20173118_20195527.FragmentosTutor;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import ayala.carlos.lab4_20173118_20195527.R;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentBuscarTrabajadorBinding;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentInformacionTrabajadorBinding;
import ayala.carlos.lab4_20173118_20195527.dtos.BusquedaTrabajadorDto;
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

public class buscarTrabajadorFragment extends Fragment {

    String channelId = "channelHighPriorTutor";
    FragmentBuscarTrabajadorBinding binding;

    TrabajadorService trabajadorService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajadorService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBuscarTrabajadorBinding.inflate(inflater, container, false);

        binding.descargarDatos.setOnClickListener(view -> {
            String id = binding.textIDtrabajador.getText().toString();
            if (!id.equals("")){
                try{
                    int idTrab = Integer.parseInt(id);
                    trabajadorService.buscarTrabajadorPorId(idTrab).enqueue(new Callback<BusquedaTrabajadorDto>() {
                        @Override
                        public void onResponse(Call<BusquedaTrabajadorDto> call, Response<BusquedaTrabajadorDto> response) {
                            if (response.isSuccessful()){
                                BusquedaTrabajadorDto busquedaTrabajadorDto = response.body();
                                if (busquedaTrabajadorDto.getRespuesta().equals("no encontrado")){
                                    binding.errorID2.setText("Empleado inexistente");
                                }else {
                                    HashMap<String, Object> datosDisplay = obtenerDatosDisplay(busquedaTrabajadorDto.getEmpleado());
                                    guardarDatosComoJson(datosDisplay, binding);
                                    Toast.makeText(getContext(), "Descarga Exitosa!", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                binding.errorID2.setTextSize(24);
                                binding.errorID2.setText("No encontrado");
                            }
                        }

                        @Override
                        public void onFailure(Call<BusquedaTrabajadorDto> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    binding.errorID2.setText("Debe ingresar un número");
                }
            }else {
                binding.errorID2.setText("Debe ingresar un ID");
            }
        });


        //return inflater.inflate(R.layout.fragment_buscar_trabajador, container, false);
        return binding.getRoot();
    }



    public HashMap<String, Object> obtenerDatosDisplay(Employees employee){

        HashMap<String, Object> info = new HashMap<>();

        info.put("Id", employee.getEmployeeId());

        String firstName = employee.getFirstName();
        if (firstName!=null || !firstName.equals(""))
            info.put("Nombre", employee.getEmployeeId());
        else
            info.put("Nombre", "Sin nombre");

        info.put("Apellido",employee.getLastName());
        info.put("Correo", employee.getEmail());

        String phoneNumber = employee.getPhoneNumber();
        if (phoneNumber!=null || !phoneNumber.equals(""))
            info.put("Celular",employee.getPhoneNumber());
        else
            info.put("Celular", "Sin celular");

        info.put("Puesto", employee.getJobId().getJobTitle());

        Float salario = employee.getSalary();
        if (salario!=null)
            info.put("Salario", String.valueOf(employee.getSalary()));
        else
            info.put("Salario", "Sin informacion");

        Departments departmentId = employee.getDepartmentId();
        if (departmentId==null){
            info.put("Departamento", "Sin informacion");
            info.put("Pais", "Sin informacion");
            info.put("Region", "Sin informacion");
        }else {
            info.put("Departamento", employee.getDepartmentId().getDepartmentName());
            Locations locationId = employee.getDepartmentId().getLocationId();
            if (locationId==null){
                info.put("Pais", "Sin informacion");
                info.put("Region", "Sin informacion");
            }else{
                Countries countryId = employee.getDepartmentId().getLocationId().getCountryId();
                if (countryId==null || countryId.equals("")){
                    info.put("Pais", "Sin informacion");
                    info.put("Region", "Sin informacion");
                }else{
                    info.put("Pais", employee.getDepartmentId().getLocationId().getCountryId().getCountryName());
                    Regions regionId = employee.getDepartmentId().getLocationId().getCountryId().getRegionId();
                    if (regionId==null){
                        info.put("Region", "Sin informacion");
                    }else {
                        info.put("Region", employee.getDepartmentId().getLocationId().getCountryId().getRegionId().getRegionName());
                    }
                }
            }
        }

        return info;
    }

    public void guardarDatosComoJson(HashMap<String, Object> listaDatos, FragmentBuscarTrabajadorBinding binding){
        String idTrabajador = (String) listaDatos.get("Id");
        Gson gson = new Gson();
        String listaJson = gson.toJson(listaDatos);
        String fileNameJson = "informacionDe"+idTrabajador+".txt";
        try(FileOutputStream fileOutputStream = this.getActivity().openFileOutput(fileNameJson, Context.MODE_PRIVATE);
            FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());){
            fileWriter.write(listaJson);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}