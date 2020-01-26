package br.edu.ifma.es2.transportadora.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "descrição deve ser preenchida.")
    private String descricao;
    @NotNull(message = "peso deve ser preenchido.")
    @Positive(message = "peso deve ser maior que zero.")
    private Double peso;
    @NotNull(message = "valor deve ser preenchido.")
    @Positive(message = "valor deve ser maior que zero.")
    private BigDecimal valor;
    @NotNull(message = "cliente deve ser preenchido.")
    @ManyToOne
    private Cliente cliente;
    @NotNull(message = "destino deve ser preenchido.")
    @ManyToOne
    private Destino destino;

    public Frete() {

    }

    public Frete(String descricao, Double peso, Cliente cliente, Destino cidadeEntrega) {
        this.descricao = descricao;
        this.peso = peso;
        this.cliente = cliente;
        this.destino = cidadeEntrega;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Frete other = (Frete) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
