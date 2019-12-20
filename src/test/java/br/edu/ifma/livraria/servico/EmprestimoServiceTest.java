package br.edu.ifma.livraria.servico;

import static br.edu.ifma.livraria.databuilder.LivroBuilder.umLivro;
import static br.edu.ifma.livraria.databuilder.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifma.livraria.exception.EmprestimoNaoRealizadoException;
import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Usuario;
import br.edu.ifma.livraria.repository.EmprestimoRepository;

public class EmprestimoServiceTest {

    private EmprestimoService emprestimoService;

    @BeforeEach
    public void beforeEach() {
        var emprestimos = mock(EmprestimoRepository.class);
        emprestimoService = new EmprestimoService(emprestimos);
    }

    @Test
    public void deveEmprestarLivroQueNaoEstejaReservado() {
        Usuario usuario = umUsuario().comNome("James").constroi();
        Livro livro = umLivro().comTitulo("Clean Code").constroi();

        Emprestimo emprestimoRealizado = emprestimoService.emprestaLivro(usuario, livro);

        assertTrue(emprestimoRealizado.getLivro().isEmprestado());
        assertEquals(1, emprestimoRealizado.getLivro().getHistorico().size());
        assertEquals("Clean Code", emprestimoRealizado.getLivro().getTitulo());
        assertEquals("James", emprestimoRealizado.getUsuario().getNome());
    }

    @Test
    public void naoDeveEmprestarLivroQuePosuiReserva() {
        Usuario usuario = umUsuario().constroi();
        Livro livro = umLivro().constroi();

        livro.reservar();

        EmprestimoNaoRealizadoException ex = assertThrows(EmprestimoNaoRealizadoException.class,
                () -> emprestimoService.emprestaLivro(usuario, livro),
                "Deveria lançar EmprestimoNaoRealizadoException");

        assertTrue(ex.getMessage().equals("Livro Reservado"));
    }

    @Test
    public void naoDeveEmprestarLivroQueJaEstejaEmprestado() {
        Usuario usuario = umUsuario().constroi();
        Livro livro = umLivro().constroi();

        Emprestimo emprestimoRealizado = emprestimoService.emprestaLivro(usuario, livro);
        EmprestimoNaoRealizadoException ex = assertThrows(EmprestimoNaoRealizadoException.class,
                () -> emprestimoService.emprestaLivro(usuario, livro),
                "Deveria Lançar EmprestimoNaoRealizadoException");

        assertTrue(ex.getMessage().equals("Livro já está emprestado."));
        assertTrue(emprestimoRealizado.getLivro().isEmprestado());
    }

    @Test
    public void deveTerDataPrevistaCorreta() {
        Usuario usuario = umUsuario().constroi();
        Livro livro = umLivro().constroi();

        Emprestimo emprestimo = emprestimoService.emprestaLivro(usuario, livro);

        assertEquals(LocalDate.now().plusDays(7), emprestimo.getDataPrevista());
    }

    @Test
    public void deveTerUsuarioComUmEmprestimo() {
        Usuario usuario = umUsuario().constroi();
        Livro livro = umLivro().constroi();

        emprestimoService.emprestaLivro(usuario, livro);

        assertEquals(1, usuario.getEmprestimos().size());
    }

    @Test
    public void deveTerUsuarioComDoisEmprestimos() {
        Usuario usuario = umUsuario().constroi();
        Livro livro1 = umLivro().constroi();
        Livro livro2 = umLivro().constroi();

        emprestimoService.emprestaLivro(usuario, livro1);
        emprestimoService.emprestaLivro(usuario, livro2);

        assertEquals(2, usuario.getEmprestimos().size());
    }

    @Test
    public void naoDeveTerTresEmprestimosSimultaneosParaUmUsuario() {
        Usuario usuario = umUsuario().constroi();
        Livro livro1 = umLivro().constroi();
        Livro livro2 = umLivro().constroi();
        Livro livro3 = umLivro().constroi();

        emprestimoService.emprestaLivro(usuario, livro1);
        emprestimoService.emprestaLivro(usuario, livro2);
        EmprestimoNaoRealizadoException ex = assertThrows(EmprestimoNaoRealizadoException.class,
                () -> emprestimoService.emprestaLivro(usuario, livro3),
                "Deveria lançar EmprestimoNaoRealizadoException.");

        assertTrue(ex.getMessage().equals("Máximo de 2 empréstimos simultaneos."));
        assertEquals(2, usuario.getEmprestimos().size());
    }

    @Test
    public void usuarioDevePoderTerTerceiroEmprestimoQuandoDevolverAoMenosUmDeDoisLivrosAnteriormenteEmprestados() {
        Usuario usuario = umUsuario().constroi();
        Livro livro1 = umLivro().constroi();
        Livro livro2 = umLivro().constroi();
        Livro livro3 = umLivro().constroi();

        Emprestimo emprestimo1 = emprestimoService.emprestaLivro(usuario, livro1);
        Emprestimo emprestimo2 = emprestimoService.emprestaLivro(usuario, livro2);
        emprestimo1.setDataDevolucao(LocalDate.now());
        Emprestimo emprestimo3 = emprestimoService.emprestaLivro(usuario, livro3);

        assertNotNull(emprestimo1.getDataDevolucao());
        assertNull(emprestimo2.getDataDevolucao());
        assertNull(emprestimo3.getDataDevolucao());
    }

}
