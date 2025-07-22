package br.edu.ifsuldeminas.mch.constraintlayouts.modelLayout;

public class AcaoPersonagem {
    public int imagemResId;
    public String nomeAcao;
    public String descricao;
    public String categoria;
    public boolean isImportante;

    public AcaoPersonagem(int imagemResId, String nomeAcao) {
        this.imagemResId = imagemResId;
        this.nomeAcao = nomeAcao;
        this.descricao = "";
        this.categoria = "Geral";
        this.isImportante = false;
    }
    
    public AcaoPersonagem(int imagemResId, String nomeAcao, String descricao, String categoria) {
        this.imagemResId = imagemResId;
        this.nomeAcao = nomeAcao;
        this.descricao = descricao;
        this.categoria = categoria;
        this.isImportante = false;
    }
    
    public AcaoPersonagem(int imagemResId, String nomeAcao, String descricao, String categoria, boolean isImportante) {
        this.imagemResId = imagemResId;
        this.nomeAcao = nomeAcao;
        this.descricao = descricao;
        this.categoria = categoria;
        this.isImportante = isImportante;
    }
    
    // Métodos getters para compatibilidade com adapter
    public int getImagemId() {
        return imagemResId;
    }
    
    public String getTitulo() {
        return nomeAcao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public boolean isImportante() {
        return isImportante;
    }
}