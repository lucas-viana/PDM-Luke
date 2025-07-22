package br.edu.ifsuldeminas.mch.constraintlayouts.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.ifsuldeminas.mch.constraintlayouts.room.dao.EmocaoDAO;
import br.edu.ifsuldeminas.mch.constraintlayouts.room.entity.EmocaoEntity;

@Database(
    entities = {EmocaoEntity.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EmocaoDAO emocaoDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "luke_diario_database"
                    ).allowMainThreadQueries() // Para desenvolvimento - não recomendado em produção
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
