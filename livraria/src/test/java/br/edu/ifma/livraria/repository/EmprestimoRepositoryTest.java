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

public class EmprestimoRepositoryTest {

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
    public void deveSalvarEmprestimo() {
        Usuario usuario = umUsuario().constroi();
        Livro livro = umLivro().constroi();
        Emprestimo emprestimo = umEmprestimoDolivro(livro).paraUsuario(usuario).constroi();

        usuarioRepository.salva(usuario);
        livroRepository.salva(livro);
        emprestimoRepository.salva(emprestimo);

        manager.flush();
        manager.clear();

        assertNotNull(emprestimo.getId());
    }

    @Test
    public void deveTrazerListaDeLivrosEmprestados() {
        Usuario usuario = umUsuario().constroi();
        Livro livro1 = umLivro().comTitulo("The Mythical Man-Month").constroi();
        Livro livro2 = umLivro().comTitulo("Clean Code").constroi();
        Livro livro3 = umLivro().comTitulo("Code Complete").constroi();
        Livro livroNaoEmprestado = umLivro().constroi();
        Emprestimo emprestimo1 = umEmprestimoDolivro(livro1).paraUsuario(usuario).constroi();
        Emprestimo emprestimo2 = umEmprestimoDolivro(livro2).paraUsuario(usuario).constroi();
        Emprestimo emprestimo3 = umEmprestimoDolivro(livro3).paraUsuario(usuario).aDiasAtras(10).constroi();

        usuarioRepository.salva(usuario);
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
        Usuario usuario = umUsuario().constroi();
        Livro livro1 = umLivro().comTitulo("The Mythical Man-Month").constroi();
        Livro livro2 = umLivro().comTitulo("Clean Code").constroi();
        Livro livro3 = umLivro().comTitulo("Code Complete").constroi();
        Emprestimo emprestimo1 = umEmprestimoDolivro(livro1).paraUsuario(usuario).aDiasAtras(20).constroi();
        Emprestimo emprestimo2 = umEmprestimoDolivro(livro2).paraUsuario(usuario).aDiasAtras(8).constroi();
        Emprestimo emprestimo3 = umEmprestimoDolivro(livro3).paraUsuario(usuario).aDiasAtras(1).constroi();

        usuarioRepository.salva(usuario);
        livroRepository.salva(livro1);
        livroRepository.salva(livro2);
        livroRepository.salva(livro3);
        emprestimoRepository.salva(emprestimo1);
        emprestimoRepository.salva(emprestimo2);
        emprestimoRepository.salva(emprestimo3);

        manager.flush();
        manager.clear();

        List<Livro> livrosAtrasados = emprestimoRepository.livrosEmAtraso();

        assertEquals(2, livrosAtrasados.size());
        assertEquals("The Mythical Man-Month", livrosAtrasados.get(0).getTitulo());
        assertEquals("Clean Code", livrosAtrasados.get(1).getTitulo());
    }

}
