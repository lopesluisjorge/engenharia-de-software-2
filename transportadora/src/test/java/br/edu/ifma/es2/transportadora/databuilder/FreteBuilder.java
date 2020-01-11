package br.edu.ifma.es2.transportadora.databuilder;

import java.math.BigDecimal;

import br.edu.ifma.es2.transportadora.entity.CidadeEntrega;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Frete;

public class FreteBuilder {

    private Frete frete;

    public FreteBuilder() {
        frete = new Frete();
        frete.setCidadeEntrega(CidadeBuilder.umaCidade().constroi());
        frete.setCliente(ClienteBuilder.umCliente().constroi());
        frete.setDescricao("Um Frete");
        frete.setPeso(100.0d);
        frete.setValor(new BigDecimal(100));
    }

    public static FreteBuilder umFrete() {
        return new FreteBuilder();
    }

    public FreteBuilder paraOCliente(Cliente cliente) {
        frete.setCliente(cliente);
        return this;
    }

    public FreteBuilder comValor(Double valor) {
        var bigDecimal = new BigDecimal(valor.toString());
        frete.setValor(bigDecimal);
        return this;
    }

    public FreteBuilder comDestino(CidadeEntrega cidade) {
        frete.setCidadeEntrega(cidade);
        return this;
    }

    public FreteBuilder comPeso(double peso) {
        frete.setPeso(peso);
        return this;
    }

    public Frete constroi() {
        return frete;
    }


}
