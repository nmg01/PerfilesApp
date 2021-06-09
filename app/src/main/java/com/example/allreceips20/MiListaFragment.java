package com.example.allreceips20;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.allreceips20.databinding.FragmentMiListaBinding;
import com.example.allreceips20.databinding.ViewholderRecetaBinding;
import com.example.allreceips20.model.Receta;

import java.util.List;

public class MiListaFragment extends Fragment {
    NavController navController;

    private FragmentMiListaBinding binding;
    private PerfilViewModel perfilViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiListaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        perfilViewModel = new ViewModelProvider(requireActivity()).get(PerfilViewModel.class);
        navController = Navigation.findNavController(view);

        RecetaAdapter recetaAdapter = new RecetaAdapter();
        binding.listaReceta.setAdapter(recetaAdapter);

        perfilViewModel.obtenerReceta().observe(getViewLifecycleOwner(), receta ->{
            recetaAdapter.setRecetaList(receta);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Receta receta = recetaAdapter.obtenerElemento(posicion);
                perfilViewModel.eliminarReceta(receta);
            }
        }).attachToRecyclerView(binding.listaReceta);
    }

    class RecetaAdapter extends RecyclerView.Adapter<RecetaViewHolder>{

        List<Receta> recetaList;

        @NonNull
        @Override
        public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecetaViewHolder(ViewholderRecetaBinding.inflate(getLayoutInflater(),parent,false));

        }

        @Override
        public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {
            Receta receta = recetaList.get(position);
            holder.binding.titulo.setText(receta.titulo);

            Glide.with(holder.itemView).load(receta.portada).into(holder.binding.portada);

            // CUANDO DAS CLICL EN LA RECETA
            holder.itemView.setOnClickListener(v -> {
                perfilViewModel.seleccionar(receta);
                navController.navigate(R.id.action_miListaFragment_to_miRecetaViewFragment);
            });

        }

        @Override
        public int getItemCount() {
            return recetaList == null ? 0 : recetaList.size();
        }

        void setRecetaList(List<Receta> recetaList){
            this.recetaList = recetaList;
            notifyDataSetChanged();
        }

        public Receta obtenerElemento(int posicion){
            return recetaList.get(posicion);
        }
    }

    class RecetaViewHolder extends RecyclerView.ViewHolder{
        ViewholderRecetaBinding binding;

        public RecetaViewHolder(@NonNull ViewholderRecetaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}