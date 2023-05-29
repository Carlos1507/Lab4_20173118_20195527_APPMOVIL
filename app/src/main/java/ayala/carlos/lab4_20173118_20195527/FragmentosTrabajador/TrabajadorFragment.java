package ayala.carlos.lab4_20173118_20195527.FragmentosTrabajador;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import ayala.carlos.lab4_20173118_20195527.MainActivity;
import ayala.carlos.lab4_20173118_20195527.R;
import ayala.carlos.lab4_20173118_20195527.databinding.FragmentTrabajadorBinding;

public class TrabajadorFragment extends Fragment {
    FragmentTrabajadorBinding binding;
    String channelId = "channelHighPriorTrabajador";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrabajadorBinding.inflate(inflater, container, false);


        lanzarNotificacionInicio();
        NavController navController = NavHostFragment.findNavController(TrabajadorFragment.this);


        binding.buttonInformacion.setOnClickListener(view -> {
            navController.navigate(R.id.action_trabajadorFragment_to_informacionTrabajadorFragment);
        });

        binding.buttonDescargarHorarios.setOnClickListener(view -> {

            navController.navigate(R.id.action_trabajadorFragment_to_descargarHorariosFragment2);
        });

        return binding.getRoot();
    }

    public void lanzarNotificacionInicio(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), channelId)
                .setSmallIcon(R.drawable.baseline_accessibility_24)
                .setContentTitle("Empleado")
                .setContentText("Está entrando en modo Empleado")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            notificationManagerCompat.notify(1, builder.build());
        }
    }


}