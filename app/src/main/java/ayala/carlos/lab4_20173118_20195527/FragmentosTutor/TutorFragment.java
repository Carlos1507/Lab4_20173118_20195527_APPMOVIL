package ayala.carlos.lab4_20173118_20195527.FragmentosTutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ayala.carlos.lab4_20173118_20195527.databinding.FragmentTutorBinding;

public class TutorFragment extends Fragment {
    FragmentTutorBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTutorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}