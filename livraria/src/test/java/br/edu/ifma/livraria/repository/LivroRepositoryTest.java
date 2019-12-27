package br.edu.ifma.livraria.repository;

import static br.edu.ifma.livraria.databuilder.EmprestimoBuilder.umEmprestimoDolivro;
import static br.edu.ifma.livraria.databuilder.LivroBuilder.umLivro;
import static br.edu.ifma.livraria.databuilder.UsuarioBuilder.umUsuario;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import br.edu.ifma.livraria.modelo.Usuario;

public class LivroRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory emf;
    private UsuarioRepository usuarioRepository;
    private LivroRepository livroRepository;
    private EmprestimoRepository emprestimoRepository;

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
    public void deveSalvarLivroSemHistoricoDeEmprestimo() {
        Livro livro = umLivro().comTitulo("Clean Code").constroi();

        livroRepository.salva(livro);

        manager.flush();
        manager.clear();

        List<Emprestimo> historico = livroRepository.historicoDeEmprestimoDo(livro);

        assertNotNull(livro.getId());
        assertEquals(0, historico.size());
    }

    @Test
    public void deveAtualizarLivro() {
        Livro livro = umLivro().constroi();

        livroRepository.salva(livro);

        Livro livroSalvo = livroRepository.porId(livro.getId());

        livroSalvo.setTitulo("The Mithycal Man Month");
        livroSalvo.setAutor("Fred Brooks");

        livroSalvo = livroRepository.atualiza(livroSalvo);

        manager.flush();
        manager.clear();

        assertEquals("The Mithycal Man Month", livroSalvo.getTitulo());
        assertEquals("Fred Brooks", livroSalvo.getAutor());
    }

    @Test
    public void deveRecuperarHistoricoDeEmprestimosDoLivro() {
        Usuario usuario1 = umUsuario().comNome("Jon").comMatricula("A0001").constroi();
        Usuario usuario2 = umUsuario().comNome("James").comMatricula("A0002").constroi();
        Livro livro = umLivro().constroi();
        Emprestimo emprestimo1 = umEmprestimoDolivro(livro).paraUsuario(usuario1).constroi();
        Emprestimo emprestimo2 = umEmprestimoDolivro(livro).paraUsuario(usuario2).constroi();

        usuarioRepository.salva(usuario1);
        usuarioRepository.salva(usuario2);
        livroRepository.salva(livro);
        emprestimoRepository.salva(emprestimo1);
        emprestimoRepository.salva(emprestimo2);

        manager.flush();
        manager.clear();

        List<Emprestimo> historico = livroRepository.historicoDeEmprestimoDo(livro);

        assertEquals(2, historico.size());
        assertEquals("Jon", historico.get(0).getUsuario().getNome());
        assertEquals("James", historico.get(1).getUsuario().getNome());
    }

}
