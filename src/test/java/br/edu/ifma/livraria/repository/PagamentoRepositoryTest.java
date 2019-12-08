package br.edu.ifma.livraria.repository;

import static br.edu.ifma.livraria.databuilder.EmprestimoBuilder.umEmprestimoDolivro;
import static br.edu.ifma.livraria.databuilder.LivroBuilder.umLivro;
import static br.edu.ifma.livraria.databuilder.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Pagamento;
import br.edu.ifma.livraria.modelo.Usuario;

public class PagamentoRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory emf;
    private UsuarioRepository usuarioRepository;
    private LivroRepository livroRepository;
    private EmprestimoRepository emprestimoRepository;
    private PagamentoRepository pagamentoRepository;

    @BeforeAll
    public static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("livrariaPU_test");
    }

    @BeforeEach
    public void beforeEach() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        usuarioRepository = new UsuarioRepository(manager);
        livroRepository = new LivroRepository(manager);
        emprestimoRepository = new EmprestimoRepository(manager);
        pagamentoRepository = new PagamentoRepository(manager);
    }

    @AfterEach
    public void afterEach() {
        manager.getTransaction().rollback();
    }

    @AfterAll
    public static void aferAll() {
        emf.close();
    }

    @Test
    public void deveAdionarPagamentoAEmprestimo() {
        Usuario usuario = umUsuario().constroi();
        Livro livro = umLivro().constroi();
        Emprestimo emprestimo = umEmprestimoDolivro(livro).paraUsuario(usuario).constroi();
        Pagamento pagamento = new Pagamento(emprestimo, new BigDecimal("2.00"), LocalDate.now());
        emprestimo.adicionaPagamento(pagamento);

        usuarioRepository.salva(usuario);
        livroRepository.salva(livro);
        emprestimoRepository.salva(emprestimo);
        pagamentoRepository.salva(pagamento);

        manager.flush();
        manager.clear();

        List<Pagamento> pagamentos = pagamentoRepository.porEmprestimo(emprestimo);

        assertEquals(1, pagamentos.size());
        assertEquals(new BigDecimal("2.00"), pagamentos.get(0).getValor());
    }
}
