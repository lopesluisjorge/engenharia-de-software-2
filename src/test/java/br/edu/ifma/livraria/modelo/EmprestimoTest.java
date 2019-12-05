package br.edu.ifma.livraria.modelo;

import static br.edu.ifma.livraria.databuilder.EmprestimoBuilder.umEmprestimoDolivro;
import static br.edu.ifma.livraria.databuilder.LivroBuilder.umLivro;
import static br.edu.ifma.livraria.databuilder.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class EmprestimoTest {

    @Test
    public void deveTrazerNomeEMatriculaDoUsuarioQuePegouLivroEmprestado() {
        Usuario usuario = umUsuario().comNome("James").comMatricula("A0001").constroi();
        Livro livro = umLivro().constroi();
        Emprestimo emprestimo = umEmprestimoDolivro(livro).paraUsuario(usuario).constroi();

        assertEquals("James", emprestimo.getUsuario().getNome());
        assertEquals("A0001", emprestimo.getUsuario().getMatricula());
    }

    @Test
    public void dataPrevistaParaDevolucaoDeveSerDeSeteDiasAposDataDoEmprestimo() {
        Livro livro = umLivro().constroi();
        Emprestimo emprestimo = umEmprestimoDolivro(livro).constroi();

        assertEquals(LocalDate.now().plusDays(7), emprestimo.getDataPrevista());
    }

    @Test
    public void naoDeveTerDataDeDevolucaoAnteriorADataDeEmprestimo() {
        Livro livro = umLivro().comTitulo("The Mythical Man-Month").constroi();
        Emprestimo emprestimo = umEmprestimoDolivro(livro).constroi();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> emprestimo.setDataDevolucao(LocalDate.now().minusDays(1)),
                "Deveria Lançar IllegalArgumentException");

        assertEquals("Data de devolução deve ser posterior à data de empréstimo", ex.getMessage());
    }

    @Test
    public void deveTerUsuarioNaoNulo() {
        Livro livro = umLivro().constroi();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> umEmprestimoDolivro(livro).paraUsuario(null).constroi(),
                "Deveria ter lançado um IllegalArgumentException");

        assertTrue(ex.getMessage().contains("Usuário Inválido"));
    }

    @Test
    public void deveTerLivroNaoNulo() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> umEmprestimoDolivro(null).constroi(), "Deveria ter lançado um IllegalArgumentException");

        assertTrue(ex.getMessage().contains("Livro Inválido"));
    }

    @Test
    public void deveTerDataDeEmprestimoNaoNula() {
        Usuario usuario = umUsuario().constroi();
        Livro livro = umLivro().constroi();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Emprestimo(usuario, livro, null), "Deveria ter lançado um IllegalArgumentException");

        assertTrue(ex.getMessage().contains("Data de Empréstimo Inválida"));
    }

}