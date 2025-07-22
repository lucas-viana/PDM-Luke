package br.edu.ifsuldeminas.mch.constraintlayouts.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "emocoes")
public class EmocaoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String emocao;
    private String descricao;
    private String data;
    private String hora;
    private int nivelIntensidade; // 1-5

    public EmocaoEntity() {
        // Construtor vazio necessário para o Room
    }

    @Ignore
    public EmocaoEntity(String emocao, String descricao, String data, String hora, int nivelIntensidade) {
        this.emocao = emocao;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.nivelIntensidade = nivelIntensidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmocao() { return emocao; }
    public void setEmocao(String emocao) { this.emocao = emocao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public int getNivelIntensidade() { return nivelIntensidade; }
    public void setNivelIntensidade(int nivelIntensidade) { this.nivelIntensidade = nivelIntensidade; }
}
