package br.edu.ifsuldeminas.mch.constraintlayouts.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.constraintlayouts.database.DatabaseHelper;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.EmocaoModel;

public class EmocaoDAO {
    private DatabaseHelper dbHelper;

    public EmocaoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long inserirEmocao(EmocaoModel emocao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DATA, emocao.getData());
        values.put(DatabaseHelper.COLUMN_EMOCAO, emocao.getEmocao());
        values.put(DatabaseHelper.COLUMN_DESCRICAO, emocao.getDescricao());
        values.put(DatabaseHelper.COLUMN_NIVEL_INTENSIDADE, emocao.getNivelIntensidade());
        
        long id = db.insert(DatabaseHelper.TABLE_EMOCOES, null, values);
        db.close();
        return id;
    }

    public List<EmocaoModel> listarEmocoes() {
        List<EmocaoModel> emocoes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        Cursor cursor = db.query(DatabaseHelper.TABLE_EMOCOES, null, null, null, null, null, 
            DatabaseHelper.COLUMN_DATA + " DESC");
        
        if (cursor.moveToFirst()) {
            do {
                EmocaoModel emocao = new EmocaoModel();
                emocao.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                emocao.setData(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA)));
                emocao.setEmocao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMOCAO)));
                emocao.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRICAO)));
                emocao.setNivelIntensidade(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NIVEL_INTENSIDADE)));
                emocoes.add(emocao);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return emocoes;
    }

    public void deletarEmocao(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_EMOCOES, DatabaseHelper.COLUMN_ID + " = ?", 
            new String[]{String.valueOf(id)});
        db.close();
    }
}
