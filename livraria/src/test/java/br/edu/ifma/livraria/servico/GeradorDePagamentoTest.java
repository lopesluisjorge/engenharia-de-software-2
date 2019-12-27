package br.edu.ifma.livraria.servico;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import br.edu.ifma.livraria.databuilder.EmprestimoBuilder;
import br.edu.ifma.livraria.databuilder.LivroBuilder;
import br.edu.ifma.livraria.databuilder.UsuarioBuilder;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Pagamento;
import br.edu.ifma.livraria.modelo.Usuario;
import br.edu.ifma.livraria.repository.EmprestimoRepository;
import br.edu.ifma.livraria.repository.PagamentoRepository;

public class GeradorDePagamentoTest {

    private GeradorDePagamento geradorDePagamento;
    private Usuario usuario;
    private Livro livro;
    private EmprestimoRepository emprestimos;
    private PagamentoRepository pagamentos;

    @BeforeEach
    public void beforeEach() {
        usuario = UsuarioBuilder.umUsuario().constroi();
        livro = LivroBuilder.umLivro().constroi();
        emprestimos = mock(EmprestimoRepository.class);
        pagamentos = mock(PagamentoRepository.class);
        geradorDePagamento = new GeradorDePagamento(emprestimos, pagamentos);
    }

    @Test
    public void deveGerarPagamentosParaEmprestimosDevolvodosEmAtraso() {
        var emprestimo = EmprestimoBuilder.umEmprestimoDolivro(livro).aDiasAtras(8).constroi();
        new DevolucaoService(emprestimos).devolverLivro(emprestimo, LocalDate.now());

        when(emprestimos.devolvidosEmAtraso(usuario)).thenReturn(Arrays.asList(emprestimo));

        geradorDePagamento.geraPagamento(usuario);

        ArgumentCaptor<Pagamento> capturadorDePagamento = ArgumentCaptor.forClass(Pagamento.class);

        verify(pagamentos, times(1)).salva(capturadorDePagamento.capture());

        var pagamento = capturadorDePagamento.getValue();

        assertThat(pagamento.getEmprestimo(), equalTo(emprestimo));
        assertThat(pagamento.getValor(), equalTo(new BigDecimal("5.40")));
    }

}
