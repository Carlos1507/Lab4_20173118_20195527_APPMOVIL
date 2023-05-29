package ayala.carlos.lab4_20173118_20195527.FragmentosTutor;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.PendingIntent;
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

import ayala.carlos.lab4_20173118_20195527.FragmentosTrabajador.TrabajadorFragment;
import ayala.carlos.lab4_20173118_20195527.MainActivity;
import ayala.carlos.lab4_20173118_20195527.R;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentTutorBinding;

public class TutorFragment extends Fragment {
    FragmentTutorBinding binding;
    String channelId2 = "channelHighPriorTutor";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTutorBinding.inflate(inflater, container, false);

        lanzarNotificacionInicio();
        NavController navController = NavHostFragment.findNavController(TutorFragment.this);

        //AL HACER CLICK EN EL BOTON DESCARGAR LISTA DE TRABAJADORES
        binding.botonDescargar.setOnClickListener(view -> {

        });

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

}