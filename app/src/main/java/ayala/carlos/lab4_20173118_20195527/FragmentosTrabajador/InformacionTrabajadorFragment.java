package ayala.carlos.lab4_20173118_20195527.FragmentosTrabajador;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ayala.carlos.lab4_20173118_20195527.databinding.FragmentInformacionTrabajadorBinding;

public class InformacionTrabajadorFragment extends Fragment {
    FragmentInformacionTrabajadorBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInformacionTrabajadorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}