package com.example.allreceips20;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.allreceips20.databinding.FragmentMiRecetaViewBinding;


public class MiRecetaViewFragment extends Fragment {

    private FragmentMiRecetaViewBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiRecetaViewBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        PerfilViewModel perfilViewModel = new ViewModelProvider(requireActivity()).get(PerfilViewModel.class);

        perfilViewModel.seleccionado().observe(getViewLifecycleOwner(), receta -> {
            binding.titulo.setText(receta.titulo);
            binding.descripcion.setText(receta.descripcion);
            binding.portada.setImageURI(Uri.parse(receta.portada));


        });
    }
}