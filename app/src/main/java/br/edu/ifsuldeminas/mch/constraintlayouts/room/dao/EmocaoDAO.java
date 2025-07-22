package br.edu.ifsuldeminas.mch.constraintlayouts.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.ifsuldeminas.mch.constraintlayouts.room.entity.EmocaoEntity;

@Dao
public interface EmocaoDAO {

    @Insert
    long inserirEmocao(EmocaoEntity emocao);

    @Update
    void atualizarEmocao(EmocaoEntity emocao);

    @Delete
    void deletarEmocao(EmocaoEntity emocao);

    @Query("SELECT * FROM emocoes ORDER BY data DESC, hora DESC")
    List<EmocaoEntity> listarTodasEmocoes();

    @Query("SELECT * FROM emocoes WHERE id = :id")
    EmocaoEntity buscarEmocaoPorId(int id);

    @Query("SELECT * FROM emocoes WHERE data = :data ORDER BY hora DESC")
    List<EmocaoEntity> buscarEmocoesPorData(String data);

    @Query("SELECT * FROM emocoes WHERE emocao = :tipoEmocao ORDER BY data DESC")
    List<EmocaoEntity> buscarEmocoesPorTipo(String tipoEmocao);

    @Query("DELETE FROM emocoes")
    void limparTodasEmocoes();

    @Query("SELECT COUNT(*) FROM emocoes")
    int contarEmocoes();
}
