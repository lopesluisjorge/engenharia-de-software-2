package br.edu.ifma.es2.transportadora.controller.dto;

import java.math.BigDecimal;

import br.edu.ifma.es2.transportadora.entity.Frete;

public class FreteDto {

    private Long id;
    private String descricao;
    private Double peso;
    private BigDecimal valor;
    private ClienteDto cliente;
    private DestinoDto destino;

    FreteDto() {

    }

    public FreteDto(Frete frete) {
        id = frete.getId();
        descricao = frete.getDescricao();
        peso = frete.getPeso();
        valor = frete.getValor();
        cliente = new ClienteDto(frete.getCliente());
        destino = new DestinoDto(frete.getDestino());
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

    public ClienteDto getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDto cliente) {
        this.cliente = cliente;
    }

    public DestinoDto getDestino() {
        return destino;
    }

    public void setDestino(DestinoDto destino) {
        this.destino = destino;
    }

}
