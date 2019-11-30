package br.edu.ifma.livraria.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import br.edu.ifma.livraria.modelo.Usuario;

public class LivroRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory emf;
    private LivroRepository livroRepository;

    @BeforeAll
    public static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("livrariaPU_test");
    }

    @BeforeEach
    public void beforeEach() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        livroRepository = new LivroRepository(manager);
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
        final Livro livro = new Livro("Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin");

        final Livro livroSalvo = livroRepository.salva(livro);

        manager.flush();
        manager.clear();

        List<Emprestimo> historico = livroRepository.historicoDeEmprestimoDo(livroSalvo);

        assertEquals("Clean Code: A Handbook of Agile Software Craftsmanship", livroSalvo.getTitulo());
        assertEquals(0, historico.size());
    }

    @Test
    public void deveAtualizarLivro() {
        final Livro livro = new Livro("Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin");

        final Livro livroSalvo = livroRepository.salva(livro);

        manager.flush();
        manager.clear();

        livroSalvo.setTitulo("The Mithycal Man Month");
        livroSalvo.setAutor("Fred Brooks");

        final Livro livroAtualizado = livroRepository.atualiza(livroSalvo);

        manager.flush();
        manager.clear();

        assertEquals("The Mithycal Man Month", livroAtualizado.getTitulo());
        assertEquals("Fred Brooks", livroAtualizado.getAutor());
    }

    @Test
    public void deveRecuperarHistoricoDeEmprestimosDoLivro() {
        final Livro livro = new Livro();
        livro.setTitulo("The Mithycal Man Month");
        livro.adicionaEmprestimo(new Emprestimo(new Usuario("Jon", "1"), livro, LocalDate.now()));

        final Livro livroSalvo = livroRepository.salva(livro);

        manager.flush();
        manager.clear();

        List<Emprestimo> historico = livroRepository.historicoDeEmprestimoDo(livroSalvo);
        
        assertEquals(1, historico.size());
        assertEquals("The Mithycal Man Month", historico.get(0).getLivro().getTitulo());
    }

}
