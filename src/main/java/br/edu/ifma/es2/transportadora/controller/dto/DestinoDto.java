package br.edu.ifma.es2.transportadora.controller.dto;

import br.edu.ifma.es2.transportadora.entity.Destino;

public class DestinoDto {

    private Long id;
    private String nome;
    private String uf;

    DestinoDto() {

    }

    public DestinoDto(Destino destino) {
        id = destino.getId();
        nome = destino.getNome();
        uf = destino.getUf();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}
