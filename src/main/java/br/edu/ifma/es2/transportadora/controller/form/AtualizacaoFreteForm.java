package br.edu.ifma.es2.transportadora.controller.form;

import javax.validation.constraints.NotBlank;

public class AtualizacaoFreteForm {

    @NotBlank(message = "descrição deve ser preenchida.")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
