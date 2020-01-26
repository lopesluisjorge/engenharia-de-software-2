package br.edu.ifma.es2.transportadora.databuilder;

import static br.edu.ifma.es2.transportadora.databuilder.EnderecoBuilder.umEndereco;

import br.edu.ifma.es2.transportadora.controller.form.ClienteForm;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Endereco;

public class ClienteBuilder {

    private Cliente cliente;

    private ClienteBuilder() {
        var nome = "Jon";
        var endereco = umEndereco().constroi();
        var telefone = "98988778787";
        cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);
    }

    public static ClienteBuilder umCliente() {
        return new ClienteBuilder();
    }

    public ClienteBuilder comNome(String nome) {
        cliente.setNome(nome);
        return this;
    }

    public ClienteBuilder comEndereco(Endereco endereco) {
        cliente.setEndereco(endereco);
        return this;
    }

    public ClienteBuilder comTelefone(String telefone) {
        cliente.setTelefone(telefone);
        return this;
    }

    public Cliente constroi() {
        return cliente;
    }

    public ClienteForm constroiForm() {
        var form = new ClienteForm();
        form.setNome(cliente.getNome());
        form.setEndereco(cliente.getEndereco());
        form.setTelefone(cliente.getTelefone());
        return form;
    }

}
