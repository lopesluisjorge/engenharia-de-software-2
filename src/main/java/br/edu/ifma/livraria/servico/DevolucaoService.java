package br.edu.ifma.livraria.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.repository.EmprestimoRepository;

public class DevolucaoService {

    private final EmprestimoRepository emprestimos;

    public DevolucaoService(EmprestimoRepository emprestimos) {
        this.emprestimos = emprestimos;
    }

    public void devolverLivro(Emprestimo emprestimo, LocalDate dataDevolucao) {
        emprestimo.getLivro().devolver();
        emprestimo.setDataDevolucao(dataDevolucao);

        var valorTotal = Emprestimo.VALOR_FIXO_PAGAMENTO
                .add(calcularValorDaMulta(emprestimo, dataDevolucao))
                .setScale(2);

        emprestimo.setValorTotal(valorTotal);

        emprestimos.salva(emprestimo);
    }

    private BigDecimal calcularValorDaMulta(Emprestimo emprestimo, LocalDate dataDevolucao) {
        var diferencaDeDias = ChronoUnit.DAYS.between(emprestimo.getDataPrevista(), dataDevolucao);

        if (diferencaDeDias < 0) diferencaDeDias = 0;

        var valorMulta = BigDecimal.ZERO;
        var valorMaximoDaMulta = Emprestimo.VALOR_FIXO_PAGAMENTO.multiply(BigDecimal.valueOf(0.6));

        valorMulta = Emprestimo.MULTA_POR_DIA_DE_ATRASO.multiply(BigDecimal.valueOf(diferencaDeDias));

        if (valorMulta.compareTo(valorMaximoDaMulta) == 1) {
            valorMulta = valorMaximoDaMulta;
        }

        return valorMulta;
    }

}