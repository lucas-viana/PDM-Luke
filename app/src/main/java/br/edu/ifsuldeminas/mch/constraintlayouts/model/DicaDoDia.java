package br.edu.ifsuldeminas.mch.constraintlayouts.model;

import com.google.gson.annotations.SerializedName;

public class DicaDoDia {
    @SerializedName("content")
    private String texto;
    
    @SerializedName("text")
    private String textoAlternativo;
    
    @SerializedName("author")
    private String autor;
    
    @SerializedName("a")
    private String autorAlternativo;
    
    public DicaDoDia() {
        // Construtor vazio
    }
    
    public DicaDoDia(String texto, String autor) {
        this.texto = texto;
        this.autor = autor;
    }
    
    // Getters e Setters
    public String getTexto() {
        // Prioriza 'content', depois 'text'
        if (texto != null && !texto.isEmpty()) {
            return texto;
        } else if (textoAlternativo != null && !textoAlternativo.isEmpty()) {
            return textoAlternativo;
        }
        return "Dica não disponível";
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public String getAutor() {
        // Prioriza 'author', depois 'a'
        if (autor != null && !autor.isEmpty()) {
            return autor;
        } else if (autorAlternativo != null && !autorAlternativo.isEmpty()) {
            return autorAlternativo;
        }
        return "Autor Desconhecido";
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
}
