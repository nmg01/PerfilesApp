package com.example.allreceips20;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.allreceips20.model.Receta;
import com.example.allreceips20.model.RecetaRepositorio;

import java.util.List;

public class PerfilViewModel extends AndroidViewModel {

    public MutableLiveData<Uri> imagenSeleccionada = new MutableLiveData<>();

    MutableLiveData<Receta> recetaSeleccionado = new MutableLiveData<>();

    RecetaRepositorio recetaRepositorio;

    public PerfilViewModel(@NonNull Application application) {
        super(application);

        recetaRepositorio = new RecetaRepositorio(application);
    }

    public LiveData<List<Receta>> obtenerReceta() {
        return recetaRepositorio.obtenerReceta();
    }

    public LiveData<List<Receta>> obtenerRecetasIniciales() {
        return recetaRepositorio.obtenerRecetasIniciales();
    }

    public void insertarReceta(String titulo, String descripcion, String portada) {
        recetaRepositorio.insertarReceta(titulo, descripcion, portada);
    }

    public void eliminarReceta(Receta receta){
        recetaRepositorio.eliminarReceta(receta);
    }

    public void establecerImagenSeleccionada(Uri uri){
        imagenSeleccionada.setValue(uri);
    }

    void seleccionar(Receta receta){
        recetaSeleccionado.setValue(receta);
    }

    MutableLiveData<Receta> seleccionado(){
        return recetaSeleccionado;
    }

}
