package br.edu.ifma.es2.transportadora.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

    private TipoLogradouro tipoLogradouroEndereco;
    private String logradouroEndereco;
    private String numeroEndereco;
    private String bairroEndereco;
    private String cidadeEndereco;
    private String ufEndereco;

    public TipoLogradouro getTipoLogradouroEndereco() {
        return tipoLogradouroEndereco;
    }

    public void setTipoLogradouroEndereco(TipoLogradouro tipoLogradouroEndereco) {
        this.tipoLogradouroEndereco = tipoLogradouroEndereco;
    }

    public String getLogradouroEndereco() {
        return logradouroEndereco;
    }

    public void setLogradouroEndereco(String logradouroEndereco) {
        this.logradouroEndereco = logradouroEndereco;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getBairroEndereco() {
        return bairroEndereco;
    }

    public void setBairroEndereco(String bairroEndereco) {
        this.bairroEndereco = bairroEndereco;
    }

    public String getCidadeEndereco() {
        return cidadeEndereco;
    }

    public void setCidadeEndereco(String cidadeEndereco) {
        this.cidadeEndereco = cidadeEndereco;
    }

    public String getUfEndereco() {
        return ufEndereco;
    }

    public void setUfEndereco(String ufEndereco) {
        this.ufEndereco = ufEndereco;
    }

    @Override
    public String toString() {
        return tipoLogradouroEndereco.getTipo() + " " + 
                logradouroEndereco + ", " + numeroEndereco + ", " 
                + bairroEndereco + ", " + cidadeEndereco + " - " + ufEndereco;
    }

}
