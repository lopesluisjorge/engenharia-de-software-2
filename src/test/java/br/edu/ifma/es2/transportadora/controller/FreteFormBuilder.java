package br.edu.ifma.es2.transportadora.controller;

import br.edu.ifma.es2.transportadora.controller.form.FreteForm;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Destino;

public class FreteFormBuilder {

    private FreteForm frete;

    private FreteFormBuilder() {
        frete = new FreteForm();
    }

    public static FreteFormBuilder umFrete() {
        return new FreteFormBuilder();
    }

    public FreteFormBuilder paraOCliente(Cliente cliente) {
        frete.setClienteId(cliente.getId());
        return this;
    }

    public FreteFormBuilder comDestino(Destino destino) {
        frete.setDestinoId(destino.getId());
        return this;
    }

    public FreteFormBuilder comPeso(Double peso) {
        frete.setPeso(peso);
        return this;
    }

    public FreteFormBuilder comDescricao(String descricao) {
        frete.setDescricao(descricao);
        return this;
    }

    public FreteForm constroi() {
        return frete;
    }

}
