package br.edu.ifsuldeminas.mch.constraintlayouts.model;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class Meta {
    private String id;
    private String titulo;
    private String descricao;
    private boolean concluida;
    @ServerTimestamp
    private Date dataCriacao;

    public Meta() {
    }

    public Meta(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = false;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }

    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }
}
