package br.edu.ifma.es2.transportadora.databuilder;

import java.math.BigDecimal;

import br.edu.ifma.es2.transportadora.entity.Destino;

public class DestinoBuilder {
    
    private Destino cidade;

    private DestinoBuilder() {
        cidade = new Destino();
        cidade.setNome("São Luís");
        cidade.setUf("MA");
        cidade.setTaxa(new BigDecimal("10.00"));
    }

    public static DestinoBuilder umaCidade() {
        return new DestinoBuilder();
    }

    public DestinoBuilder comNome(String nome) {
        cidade.setNome(nome);
        return this;
    }
    
    public DestinoBuilder comUf(String uf) {
        cidade.setUf(uf);
        return this;
    }
    
    public DestinoBuilder comTaxa(BigDecimal taxa) {
        cidade.setTaxa(taxa);
        return this;
    }
    public DestinoBuilder comTaxa(Double taxa) {
        return comTaxa(new BigDecimal(taxa.toString()));
    }

    public Destino constroi() {
        return cidade;
    }


}
