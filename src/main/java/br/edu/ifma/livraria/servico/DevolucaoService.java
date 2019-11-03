package br.edu.ifma.livraria.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import br.edu.ifma.livraria.modelo.Emprestimo;

public class DevolucaoService {

    public void devolverLivro(Emprestimo emprestimo, LocalDate dataDevolucao) {
        emprestimo.getLivro().setEmprestado(false);
        emprestimo.setDataDevolucao(dataDevolucao);

        BigDecimal valorFixo = Emprestimo.VALOR_FIXO_PAGAMENTO;

        long diasEntreDataPrevistaEDevolucao = ChronoUnit.DAYS.between(emprestimo.getDataPrevista(), dataDevolucao);
        BigDecimal valorMulta = BigDecimal.ZERO;
        if (diasEntreDataPrevistaEDevolucao > 0) {
            valorMulta = Emprestimo.MULTA_POR_DIA_DE_ATRASO_NO_PAGAMENTO
                    .multiply(BigDecimal.valueOf(diasEntreDataPrevistaEDevolucao));
        }
        BigDecimal _60PerCentoDoValorFixo = valorFixo.multiply(BigDecimal.valueOf(0.6));
        if (valorMulta.compareTo(_60PerCentoDoValorFixo) == 1) {
            valorMulta = _60PerCentoDoValorFixo;
        }

        emprestimo.setValorPago(valorFixo.add(valorMulta).setScale(2));
    }

}