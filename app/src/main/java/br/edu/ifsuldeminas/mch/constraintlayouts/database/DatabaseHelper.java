package br.edu.ifsuldeminas.mch.constraintlayouts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "luke_diario.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela de emoções
    public static final String TABLE_EMOCOES = "emocoes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_EMOCAO = "emocao";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_NIVEL_INTENSIDADE = "nivel_intensidade";

    // Script de criação da tabela
    private static final String CREATE_TABLE_EMOCOES = 
        "CREATE TABLE " + TABLE_EMOCOES + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_DATA + " TEXT NOT NULL, " +
        COLUMN_EMOCAO + " TEXT NOT NULL, " +
        COLUMN_DESCRICAO + " TEXT, " +
        COLUMN_NIVEL_INTENSIDADE + " INTEGER DEFAULT 1" +
        ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EMOCOES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOCOES);
        onCreate(db);
    }
}
