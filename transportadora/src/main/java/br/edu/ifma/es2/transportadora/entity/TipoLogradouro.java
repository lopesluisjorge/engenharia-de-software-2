package br.edu.ifma.es2.transportadora.entity;

public enum TipoLogradouro {

    RUA("Rua"), AVENIDA("Av."), TRAVESSA("Tv.");

    private String tipo;

    private TipoLogradouro(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

}
