package br.edu.ifma.livraria.modelo;

import static br.edu.ifma.livraria.databuilder.LivroBuilder.umLivro;
import static br.edu.ifma.livraria.databuilder.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LivroTest {

    private Livro livro;

    @BeforeEach
    public void beforeEach() {
        livro = umLivro().comTitulo("Clean Code").doAutor("Robert Martin").constroi();
    }

    @Test
    public void naoPodeEstarEmprestado() {
        assertFalse(livro.isEmprestado());
    }

    @Test
    public void deveEstarReservado() {
        livro.reservar();

        assertTrue(livro.isReservado());
    }

    @Test
    public void naoPodeEstarReservado() {
        assertFalse(livro.isReservado());
    }

    @Test
    public void deveAdicionarEmprestimoAHistoricoDeEmprestimosVazio() {
        Usuario usuario = umUsuario().comNome("James").constroi();

        Emprestimo emprestimo = new Emprestimo(usuario, livro, LocalDate.now());
        livro.adicionaEmprestimo(emprestimo);

        assertEquals(1, livro.getHistorico().size());
        assertEquals("James", emprestimo.getUsuario().getNome());
        assertEquals(livro, emprestimo.getLivro());
        assertEquals(LocalDate.now(), emprestimo.getDataEmprestimo());
    }

}