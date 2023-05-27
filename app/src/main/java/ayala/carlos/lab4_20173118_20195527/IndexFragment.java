package ayala.carlos.lab4_20173118_20195527;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ayala.carlos.lab4_20173118_20195527.databinding.FragmentIndexBinding;


public class IndexFragment extends Fragment {
    FragmentIndexBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIndexBinding.inflate(inflater, container, false);

        NavController navController = NavHostFragment.findNavController(IndexFragment.this);

        binding.buttonTrabajador.setOnClickListener(view -> {
            navController.navigate(R.id.action_indexFragment_to_trabajadorFragment);
        });

        binding.buttonTutor.setOnClickListener(view ->{
            navController.navigate(R.id.action_indexFragment_to_tutorFragment);
        });

        return binding.getRoot();
    }
}