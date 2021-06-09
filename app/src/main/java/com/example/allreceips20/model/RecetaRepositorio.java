package com.example.allreceips20.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecetaRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();
    BaseDeDatos.RecetaDao recetaDao;

    public RecetaRepositorio(Application application) {
        recetaDao = BaseDeDatos.getInstance(application).obetenerRecetaDao();
    }

    public LiveData<List<Receta>> obtenerReceta() {
        return recetaDao.obtenerRecetas();
    }

    public LiveData<List<Receta>> obtenerRecetasIniciales() {
        return recetaDao.obtenerRecetasIniciales();
    }

    public void insertarReceta(String titulo, String descripcion, String portada) {
        executor.execute(() -> {
            recetaDao.insertarReceta(new Receta(titulo, descripcion, portada));
        });
    }

    public void eliminarReceta(Receta receta) {
        executor.execute(() -> {
            recetaDao.eliminarReceta(receta);
        });
    }

}
