package br.edu.ifma.es2.transportadora.controller.dto;

import br.edu.ifma.es2.transportadora.entity.Cliente;

public class ClienteDto {

    private Long id;
    private String nome;
    private String endereco;
    private String telefone;

    ClienteDto() {

    }

    public ClienteDto(Cliente cliente) {
        id = cliente.getId();
        nome = cliente.getNome();
        endereco = cliente.getEndereco().toString();
        telefone = cliente.getTelefone();
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
