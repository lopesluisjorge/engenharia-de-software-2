package br.edu.ifma.es2.transportadora.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Frete {

    @Id
    @GeneratedValue
    private Long id;
    private String descricao;
    private Double peso;
    private BigDecimal valor;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private CidadeEntrega cidadeEntrega;

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CidadeEntrega getCidadeEntrega() {
        return cidadeEntrega;
    }

    public void setCidadeEntrega(CidadeEntrega cidadeEntrega) {
        this.cidadeEntrega = cidadeEntrega;
    }

}
