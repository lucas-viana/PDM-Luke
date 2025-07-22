package br.edu.ifsuldeminas.mch.constraintlayouts.model;

public class EmocaoModel {
    private int id;
    private String data;
    private String emocao;
    private String descricao;
    private int nivelIntensidade; // 1-5

    public EmocaoModel() {}

    public EmocaoModel(String data, String emocao, String descricao, int nivelIntensidade) {
        this.data = data;
        this.emocao = emocao;
        this.descricao = descricao;
        this.nivelIntensidade = nivelIntensidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getEmocao() { return emocao; }
    public void setEmocao(String emocao) { this.emocao = emocao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getNivelIntensidade() { return nivelIntensidade; }
    public void setNivelIntensidade(int nivelIntensidade) { this.nivelIntensidade = nivelIntensidade; }
}
