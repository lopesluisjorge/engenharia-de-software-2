package br.edu.ifma.livraria.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class EmprestimoTest {

    @Test
    public void deveTrazerNomeEMatriculaDoUsuarioQuePegouLivroEmprestado() {
        final Usuario usuario = new Usuario("James", "A0001");
        final Livro livro = new Livro("Clean Code", "Robert C. Martin");
        final LocalDate dataEmprestimo = LocalDate.now();
        Emprestimo emprestimo = new Emprestimo(usuario, livro, dataEmprestimo);

        assertEquals("James", emprestimo.getUsuario().getNome());
        assertEquals("A0001", emprestimo.getUsuario().getMatricula());
    }

    @Test
    public void dataPrevistaParaDevolucaoDeveSerDeSeteDiasAposDataDoEmprestimo() {
        final Usuario usuario = new Usuario("James", "A0001");
        final Livro livro = new Livro("Clean Code", "Robert C. Martin");
        final LocalDate dataEmprestimo = LocalDate.now();
        Emprestimo emprestimo = new Emprestimo(usuario, livro, dataEmprestimo);

        assertEquals(LocalDate.now().plusDays(7), emprestimo.getDataPrevista());
    }

    @Test
    public void naoDeveTerDataDeDevolucaoAnteriorADataDeEmprestimo() {
        final Usuario usuario = new Usuario("James", "A0001");
        final Livro livro = new Livro("Clean Code", "Robert C. Martin");
        final LocalDate dataEmprestimo = LocalDate.now();
        Emprestimo emprestimo = new Emprestimo(usuario, livro, dataEmprestimo);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> emprestimo.setDataDevolucao(LocalDate.now().minusDays(1)),
                "Deveria Lançar IllegalArgumentException");

        assertEquals("Data de devolução deve ser posterior à data de empréstimo", ex.getMessage());
    }

    @Test
    public void deveTerUsuarioNaoNulo() {
        final Livro livro = new Livro("Clean Code", "Robert C. Martin");
        final LocalDate dataEmprestimo = LocalDate.now();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Emprestimo(null, livro, dataEmprestimo), "Deveria ter lançado um IllegalArgumentException");

        assertTrue(ex.getMessage().contains("Usuário Inválido"));
    }

    @Test
    public void deveTerLivroNaoNulo() {
        final Usuario usuario = new Usuario("James", "A0001");
        final LocalDate dataEmprestimo = LocalDate.now();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Emprestimo(usuario, null, dataEmprestimo), "Deveria ter lançado um IllegalArgumentException");

        assertTrue(ex.getMessage().contains("Livro Inválido"));
    }

    @Test
    public void deveTerDataDeEmprestimoNaoNula() {
        final Usuario usuario = new Usuario("James", "A0001");
        final Livro livro = new Livro("Clean Code", "Robert C. Martin");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Emprestimo(usuario, livro, null), "Deveria ter lançado um IllegalArgumentException");

        assertTrue(ex.getMessage().contains("Data de Empréstimo Inválida"));
    }

}