package ayala.carlos.lab4_20173118_20195527.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class EmpleadoDescargarViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, Object>> empleadoJson = new MutableLiveData<>();

    public MutableLiveData<HashMap<String, Object>> getEmpleadoJson() {
        return empleadoJson;
    }

    public void setEmpleadoJson(MutableLiveData<HashMap<String, Object>> empleadoJson) {
        this.empleadoJson = empleadoJson;
    }
}
