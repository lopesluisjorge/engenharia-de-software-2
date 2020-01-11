package br.edu.ifma.es2.transportadora.databuilder;

import java.math.BigDecimal;

import br.edu.ifma.es2.transportadora.entity.CidadeEntrega;

public class CidadeBuilder {
    
    private CidadeEntrega cidade;

    private CidadeBuilder() {
        cidade = new CidadeEntrega();
        cidade.setNome("São Luís");
        cidade.setUf("MA");
        cidade.setTaxa(new BigDecimal("10.00"));
    }

    public static CidadeBuilder umaCidade() {
        return new CidadeBuilder();
    }

    public CidadeBuilder comNome(String nome) {
        cidade.setNome(nome);
        return this;
    }
    
    public CidadeBuilder comUf(String uf) {
        cidade.setUf(uf);
        return this;
    }
    
    public CidadeBuilder comTaxa(BigDecimal taxa) {
        cidade.setTaxa(taxa);
        return this;
    }
    public CidadeBuilder comTaxa(Double taxa) {
        return comTaxa(new BigDecimal(taxa.toString()));
    }

    public CidadeEntrega constroi() {
        return cidade;
    }


}
