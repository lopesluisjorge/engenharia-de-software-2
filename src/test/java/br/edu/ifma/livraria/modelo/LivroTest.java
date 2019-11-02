package br.edu.ifma.livraria.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LivroTest {

    private Livro livro;

    @BeforeEach
    public void beforeAll() {
        livro = new Livro("Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin");
    }

    @Test
    public void deveEstarEmprestado() {
        livro.setEmprestado(true);

        assertTrue(livro.isEmprestado());
    }

    @Test
    public void naoPodeEstarEmprestado() {
        assertFalse(livro.isEmprestado());
    }

    @Test
    public void deveEstarReservado() {
        livro.setReservado(true);

        assertTrue(livro.isReservado());
    }

    @Test
    public void naoPodeEstarReservado() {
        assertFalse(livro.isReservado());
    }

    @Test
    public void deveAdicionarEmprestimoAHistoricoDeEmprestimosVazio() {
        Usuario usuario = new Usuario("James", "A0001");

        Emprestimo emprestimo = new Emprestimo(usuario, livro, LocalDate.now());
        livro.adicionaEmprestimo(emprestimo);

        assertEquals(1, livro.getHistorico().size());
        assertEquals("James", emprestimo.getUsuario().getNome());
        assertEquals(livro, emprestimo.getLivro());
        assertEquals(LocalDate.now(), emprestimo.getDataEmprestimo());
    }

}