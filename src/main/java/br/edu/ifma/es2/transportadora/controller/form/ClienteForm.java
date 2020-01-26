package br.edu.ifma.es2.transportadora.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Endereco;

public class ClienteForm {

    @NotBlank(message = "nome deve ser preenchido.")
    private String nome;
    @NotNull(message = "endereco deve ser preenchido.")
    private Endereco endereco;
    @NotNull(message = "telefone deve ser preenchido.")
    @Pattern(message = "telefone deve ter o formato {regexp}.", regexp = "(\\d{2})9?(\\d{8})")
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cliente converte() {
        var cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);
        return cliente;
    }

}
