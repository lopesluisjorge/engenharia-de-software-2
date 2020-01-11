package br.edu.ifma.es2.transportadora.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Endereco {

    private TipoLogradouro tipoLogradouroEndereco;
    private String logradouroEndereco;
    private String numeroEndereco;
    private String bairroEndereco;
    @NotBlank(message = "cidadeEndereco deve ser preenchida.")
    private String cidadeEndereco;
    @NotBlank(message = "ufEndereco deve ser preenchida.")
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
        return tipoLogradouroEndereco.getTipo() + " " + logradouroEndereco + ", " + numeroEndereco + " - "
                + bairroEndereco + ", " + cidadeEndereco + " - " + ufEndereco;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bairroEndereco == null) ? 0 : bairroEndereco.hashCode());
        result = prime * result + ((cidadeEndereco == null) ? 0 : cidadeEndereco.hashCode());
        result = prime * result + ((logradouroEndereco == null) ? 0 : logradouroEndereco.hashCode());
        result = prime * result + ((numeroEndereco == null) ? 0 : numeroEndereco.hashCode());
        result = prime * result + ((tipoLogradouroEndereco == null) ? 0 : tipoLogradouroEndereco.hashCode());
        result = prime * result + ((ufEndereco == null) ? 0 : ufEndereco.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Endereco other = (Endereco) obj;
        if (bairroEndereco == null) {
            if (other.bairroEndereco != null)
                return false;
        } else if (!bairroEndereco.equals(other.bairroEndereco))
            return false;
        if (cidadeEndereco == null) {
            if (other.cidadeEndereco != null)
                return false;
        } else if (!cidadeEndereco.equals(other.cidadeEndereco))
            return false;
        if (logradouroEndereco == null) {
            if (other.logradouroEndereco != null)
                return false;
        } else if (!logradouroEndereco.equals(other.logradouroEndereco))
            return false;
        if (numeroEndereco == null) {
            if (other.numeroEndereco != null)
                return false;
        } else if (!numeroEndereco.equals(other.numeroEndereco))
            return false;
        if (tipoLogradouroEndereco != other.tipoLogradouroEndereco)
            return false;
        if (ufEndereco == null) {
            if (other.ufEndereco != null)
                return false;
        } else if (!ufEndereco.equals(other.ufEndereco))
            return false;
        return true;
    }

}
