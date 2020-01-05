package br.edu.ifma.es2.transportadora.databuilder;

import br.edu.ifma.es2.transportadora.entity.Endereco;
import br.edu.ifma.es2.transportadora.entity.TipoLogradouro;

public class EnderecoBuilder {

    private Endereco endereco;

    private EnderecoBuilder() {
        endereco = new Endereco();
        endereco.setTipoLogradouroEndereco(TipoLogradouro.AVENIDA);
        endereco.setLogradouroEndereco("Getúlio Vargas");
        endereco.setNumeroEndereco("4");
        endereco.setBairroEndereco("Monte Castelo");
        endereco.setCidadeEndereco("São Luís");
        endereco.setUfEndereco("MA");
    }
    
    public static EnderecoBuilder umEndereco() {
        return new EnderecoBuilder();
    }
    
    public Endereco constroi() {
        return endereco;
    }

}
