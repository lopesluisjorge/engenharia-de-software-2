package br.edu.ifma.livraria.repository;

import static org.junit.Assert.assertNotNull;
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

public class EmprestimoRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory emf;
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
    public void deveSalvarEmprestimo() {
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro = new Livro("The Mythical Man-Month", "Fred Brooks");
        Emprestimo emprestimo = new Emprestimo(usuario, livro, LocalDate.now());

        livroRepository.salva(livro);
        emprestimoRepository.salva(emprestimo);

        manager.flush();
        manager.clear();

        assertNotNull(emprestimo.getId());
    }

    @Test
    public void deveTrazerListaDeLivrosEmprestados() {
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro1 = new Livro("The Mythical Man-Month", "Fred Brooks");
        Livro livro2 = new Livro("Clean Code", "Robert C. Martin");
        Livro livro3 = new Livro("Code Complete", "Steve McConnell");
        Livro livroNaoEmprestado = new Livro("Refactoring", "Martin Fowler");

        Emprestimo emprestimo1 = new Emprestimo(usuario, livro1, LocalDate.now());
        Emprestimo emprestimo2 = new Emprestimo(usuario, livro2, LocalDate.now());
        Emprestimo emprestimo3 = new Emprestimo(usuario, livro3, LocalDate.now().minusDays(10));

        livroRepository.salva(livro1);
        livroRepository.salva(livro2);
        livroRepository.salva(livro3);
        livroRepository.salva(livroNaoEmprestado);
        emprestimoRepository.salva(emprestimo1);
        emprestimoRepository.salva(emprestimo2);
        emprestimoRepository.salva(emprestimo3);

        manager.flush();
        manager.clear();

        List<Livro> livrosEmprestados = emprestimoRepository.livrosEmprestados();

        assertEquals(3, livrosEmprestados.size());
        assertEquals("The Mythical Man-Month", livrosEmprestados.get(0).getTitulo());
        assertEquals("Clean Code", livrosEmprestados.get(1).getTitulo());
        assertEquals("Code Complete", livrosEmprestados.get(2).getTitulo());
    }

    @Test
    public void deveTrazerListaDeLivrosEmAtrasoNaDevolucao() {
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro1 = new Livro("The Mythical Man-Month", "Fred Brooks");
        Livro livro2 = new Livro("Clean Code", "Robert C. Martin");
        Livro livro3 = new Livro("Code Complete", "Steve McConnell");
        Emprestimo emprestimo1 = new Emprestimo(usuario, livro1, LocalDate.now().minusMonths(1));
        Emprestimo emprestimo2 = new Emprestimo(usuario, livro2, LocalDate.now().minusDays(10));
        Emprestimo emprestimo3 = new Emprestimo(usuario, livro3, LocalDate.now().minusDays(1));

        livroRepository.salva(livro1);
        livroRepository.salva(livro2);
        livroRepository.salva(livro3);
        emprestimoRepository.salva(emprestimo1);
        emprestimoRepository.salva(emprestimo2);
        emprestimoRepository.salva(emprestimo3);

        manager.flush();
        manager.clear();

        List<Livro> livrosEmprestados = emprestimoRepository.livrosEmAtraso();

        assertEquals(2, livrosEmprestados.size());
        assertEquals("The Mythical Man-Month", livrosEmprestados.get(0).getTitulo());
        assertEquals("Clean Code", livrosEmprestados.get(1).getTitulo());
    }

}
