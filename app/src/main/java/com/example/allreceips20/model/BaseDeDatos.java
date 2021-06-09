package com.example.allreceips20.model;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Database(entities = {Receta.class}, version = 2, exportSchema = false)
public abstract class BaseDeDatos extends RoomDatabase {
    private static volatile BaseDeDatos db;

    static Executor executor = Executors.newSingleThreadExecutor();

    public abstract RecetaDao obetenerRecetaDao();

    public static BaseDeDatos getInstance(final Context context){

        if (db == null){
            synchronized (BaseDeDatos.class){
                if (db == null){
                    db = Room.databaseBuilder(context, BaseDeDatos.class, "app.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    insertarDatosIniciales(getInstance(context).obetenerRecetaDao());
                                }

                                @Override
                                public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                                    super.onDestructiveMigration(db);

                                    insertarDatosIniciales(getInstance(context).obetenerRecetaDao());
                                }
                            })
                            .build();
                }
            }
        }
        return db;
    }

    private static void insertarDatosIniciales(RecetaDao dao) {
        executor.execute(() -> {
            dao.insertarReceta(new Receta("CREMA DE CALABAZA ASADA AL CURRY",
                    "Pela la calabaza y elimina las semillas y los filamentos. Corta la pulpa en dados y ponlos en la placa del horno, con los ajos lavados y sin pelar y las cebolletas limpias y lavadas. Riega con un hilo de aceite y ásalos 30 minutos en el horno precalentado a 200º. Retira y deja templar.\n Corta en aros la parte verde más tierna de las cebolletas y resérvalos para decorar. Trocea el resto y pela los ajos. Tritura ambos con la calabaza hasta obtener un puré. Añade el caldo caliente y 1 cucharadita de curry, y cuece 10 minutos.\nTuesta las almendras sin aceite. Retíralas y aderézalas con unas gotas de aceite y una pizca de sal. Sirve la crema caliente espolvoreada con las almendras, los aros de cebolleta reservados y el sésamo. ",
                    "file:///android_asset/cremita.jpg",1));

            dao.insertarReceta(new Receta("Lubina al vapor con patatas y mojo verde",
                    "Precalienta el horno a 200º. Lava las patatas y córtalas en gajos. Colócalas en una fuente refractaria y condimenta con una pizca de pimentón, cebolla y ajo en polvo. Rocíalas con un hilo de aceite y hornea 25 minutos.\nPela los ajos y tritúralos, con el cilantro, la mitad del perejil, 3 cucharadas de aceite, 1 de vinagre y el comino hasta conseguir una pasta. Vierte el caldo en una cazuela y coloca el cestillo de cocción al vapor  encima.\nLava los filetes, sécalos y espolvoréalos con un poco de pimienta molida. Colócalos en el cestillo, añade unas ramas de perejil lavadas y cuece al vapor 8 minutos. Sírvelos junto con las patatas y el mojo verde.",
                    "file:///android_asset/lubinalucinante.jpg",1));

            dao.insertarReceta(new Receta("pavo con salsa de queso",
                    "Raspa las zanahorias y lávalas. Corta 2 en láminas a lo largo y escáldalas en agua hirviendo. Trocea las otras 2, cuécelas 20 minutos en agua salada y escúrrelas. Limpia la cebolleta, lávala, pícala y póchala en 2 cucharadas de aceite.\nVierte el vino y el caldo, y deja reducir. Agrega la zanahoria y los quesos. Cuece hasta que la salsa espese, tritúrala y pásala por el chino. Saltea las tiras de zanahoria con las espinacas y espolvorea con pimienta y tomillo.\nCorta el solomillo en rodajas gruesas, espolvoréalas con pimienta y ásalas a la plancha con unas gotas de aceite 3 minutos por cada lado. Sírvelo con la salsa de queso y las verduras.",
                    "file:///android_asset/pavo.jpg",1));

            dao.insertarReceta(new Receta("POTAJE RÁPIDO DE GARBANZOS :D",
                    "Para preparar este potaje rápido de garbanzos, empieza raspando las zanahorias con un pelaverduras. Luego, pela las patatas, lávalas y trocéalas. Limpia el puerro y las acelgas y trocéalos.\nPela y pica finitos los ajos. Mientras, calienta dos cucharadas de aceite de oliva en una cazuela.\nAgrega el ajo y, antes de que tome color, incorpora el puerro, las zanahorias y las patatas. Tapa y rehoga a fuego lento todo unos 10 minutos.\nA continuación, añade el curry, la cúrcuma y el comino, y remueve con una espátula de madera. Cubre con el caldo caliente e incorpora ahora las acelgas. Cuece, a fuego medio, durante 15 minutos, siempre tapado.\nEcha en este momento los garbanzos bien enjuagados y escurridos, y prolonga la cocción de todo el potaje unos 10 minutos más.\nAntes de servir, espolvorea con una pizca de perejil lavado, seco y picado.",
                    "file:///android_asset/pota.jpg",1));

            dao.insertarReceta(new Receta("Empanadillas de atun y pisto",
                    "Lava el calabacín, la berenjena y el pimiento y córtalos pequeños. Pela y pica la cebolla bien fina y ralla los tomates.\nEn una sartén con 4 cucharadas de aceite sofríe la cebolla y el pimiento 5 minutos. Echa el calabacín y la berenjena y tapa para rehogar 10 minutos, removiendo de vez en cuando. Añade el tomate, sala y sofríe 10 minutos. Enfría.\nEscurre el atún y añade el pisto. Coloca una porción de la mezcla en cada oblea y cierra presionando los extremos con un tenedor.\nBUENA IDEA: Fríelas 2 minutos por cada cara o píntalas con huevo batido y hornéalas.",
                    "file:///android_asset/empanadas.jpg",1));
        });
    }

    @Dao
    public interface RecetaDao {
        @Insert
        void insertarReceta(Receta receta);

        @Update
        void editar(Receta receta);

        @Query("SELECT * FROM Receta WHERE lista = 0")
        LiveData<List<Receta>> obtenerRecetas();

        @Query("SELECT * FROM Receta WHERE lista = 1")
        LiveData<List<Receta>> obtenerRecetasIniciales();


        @Delete
        void eliminarReceta(Receta receta);

        @Query("SELECT * FROM Receta")
        LiveData<List<Receta>> obtenerTodasRecetas();

    }
}
