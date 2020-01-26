package br.edu.ifma.es2.transportadora.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.edu.ifma.es2.transportadora.entity.Frete;
import br.edu.ifma.es2.transportadora.service.ClienteService;
import br.edu.ifma.es2.transportadora.service.DestinoService;

public class FreteForm {

    @NotBlank(message = "descrição deve ser preenchida.")
    private String descricao;
    @NotNull(message = "peso deve ser preenchido.")
    @Positive(message = "peso deve ser maior que zero.")
    private Double peso;
    @NotNull(message = "clienteId deve ser preenchido.")
    private Long clienteId;
    @NotNull(message = "destinoId deve ser preenchido.")
    private Long destinoId;

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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId;
    }

    public Frete converter(ClienteService clienteRepo, DestinoService destinoRepo) {
        var cliente = clienteRepo.buscarPorId(clienteId);
        var destino = destinoRepo.buscaPorId(destinoId);

        return new Frete(descricao, peso, cliente, destino);
    }

}
