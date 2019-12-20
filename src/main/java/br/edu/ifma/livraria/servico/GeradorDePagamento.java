package br.edu.ifma.livraria.servico;

import java.time.LocalDate;

import br.edu.ifma.livraria.modelo.Pagamento;
import br.edu.ifma.livraria.modelo.Usuario;
import br.edu.ifma.livraria.repository.EmprestimoRepository;
import br.edu.ifma.livraria.repository.PagamentoRepository;

public class GeradorDePagamento {

    private final EmprestimoRepository emprestimos;
    private final PagamentoRepository pagamentos;

    public GeradorDePagamento(EmprestimoRepository emprestimos, PagamentoRepository pagamentos) {
        this.emprestimos = emprestimos;
        this.pagamentos = pagamentos;
    }

    public void geraPagamento(Usuario usuario) {
        var emprestimosDevolvidosEmAtraso = emprestimos.devolvidosEmAtraso(usuario);

        emprestimosDevolvidosEmAtraso.forEach(emprestimo -> {
            var pagamento = new Pagamento(emprestimo, emprestimo.getValorTotal(), LocalDate.now());
            pagamentos.salva(pagamento);
        });
    }
}
