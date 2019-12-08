package br.edu.ifma.livraria.servico;

import static br.edu.ifma.livraria.databuilder.LivroBuilder.umLivro;
import static br.edu.ifma.livraria.databuilder.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Usuario;

public class DevolucaoServiceTest {

    private Usuario usuario;
    private Livro livro;
    private DevolucaoService devolucaoService;
    private Emprestimo emprestimo;

    @BeforeEach
    public void beforeEach() {
        usuario = umUsuario().constroi();
        livro = umLivro().constroi();
        EmprestimoService emprestimoService = new EmprestimoService();
        emprestimo = emprestimoService.emprestaLivro(usuario, livro);
        devolucaoService = new DevolucaoService();
    }

    @Test
    public void deveDevolverLivroAntesDaDataPrevista() {
        devolucaoService.devolverLivro(emprestimo, LocalDate.now().plusDays(2));

        assertFalse(livro.isEmprestado());
        assertEquals(LocalDate.now().plusDays(2), emprestimo.getDataDevolucao());
        assertEquals(new BigDecimal("5.00"), emprestimo.getValorTotal());
    }

    @Test
    public void deveDevolverLivroNaDataPrevista() {
        devolucaoService.devolverLivro(emprestimo, LocalDate.now().plusDays(7));

        assertFalse(livro.isEmprestado());
        assertEquals(LocalDate.now().plusDays(7), emprestimo.getDataDevolucao());
        assertEquals(new BigDecimal("5.00"), emprestimo.getValorTotal());
    }

    @Test
    public void deveDevolverLivroUmDiaDepoisDaDataPrevista() {
        devolucaoService.devolverLivro(emprestimo, LocalDate.now().plusDays(8));

        assertFalse(livro.isEmprestado());
        assertEquals(LocalDate.now().plusDays(8), emprestimo.getDataDevolucao());
        assertEquals(new BigDecimal("5.40"), emprestimo.getValorTotal());
    }

    @Test
    public void deveDevolverLivroTrintaDiasDepoisDaDataPrevista() {
        devolucaoService.devolverLivro(emprestimo, LocalDate.now().plusDays(30));

        assertFalse(livro.isEmprestado());
        assertEquals(LocalDate.now().plusDays(30), emprestimo.getDataDevolucao());
        assertEquals(new BigDecimal("8.00"), emprestimo.getValorTotal());
    }

}