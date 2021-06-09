package com.example.allreceips20;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.allreceips20.databinding.FragmentInsertarRecetaBinding;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class perfilFragment extends Fragment {
    private FragmentInsertarRecetaBinding binding;
    private Uri imagenSeleccionada;
    private PerfilViewModel perfilViewModel;
    private NavController navController;
    private List<String> tagStrings = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding=FragmentInsertarRecetaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        perfilViewModel = new ViewModelProvider(requireActivity()).get(PerfilViewModel.class);

        navController = Navigation.findNavController(view);

        binding.insertar.setOnClickListener(v -> {

            if (imagenSeleccionada != null) {
                String titulo = binding.titulo.getText().toString();
                String descripcion = binding.descripcion.getText().toString();

                perfilViewModel.insertarReceta(titulo, descripcion, imagenSeleccionada.toString());

                perfilViewModel.establecerImagenSeleccionada(null);
                navController.popBackStack();
            } else {
                Toast.makeText(requireContext(), "Selecciona una imagen", Toast.LENGTH_SHORT).show();
            }
        });

        binding.portada.setOnClickListener(v ->{
            lanzadorGaleria.launch(new String[]{"image/*"});
        });

        perfilViewModel.imagenSeleccionada.observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                imagenSeleccionada = uri;
                Glide.with(requireView()).load(uri).into(binding.portada);
            }
        });
    }

    private final ActivityResultLauncher<String[]> lanzadorGaleria = registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
        requireContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        perfilViewModel.establecerImagenSeleccionada(uri);
    });

}