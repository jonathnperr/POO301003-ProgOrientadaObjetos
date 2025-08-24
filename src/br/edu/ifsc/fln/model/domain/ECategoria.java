package br.edu.ifsc.fln.model.domain;

/**
 *
 * @author J
 */
public enum ECategoria {
    PEQUENO(1, "Pequeno"), MEDIO(2, "Médio"), GRANDE(3, "Grande"), MOTO(4, "Moto"), PADRAO(5, "Padrão");
    
    private int id;
    private String descricao;
    
    private ECategoria(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
    
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
