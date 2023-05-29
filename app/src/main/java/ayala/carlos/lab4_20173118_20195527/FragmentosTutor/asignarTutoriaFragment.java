package ayala.carlos.lab4_20173118_20195527.FragmentosTutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ayala.carlos.lab4_20173118_20195527.R;
public class asignarTutoriaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asignar_tutoria, container, false);
    }
}