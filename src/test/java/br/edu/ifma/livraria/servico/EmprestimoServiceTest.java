package br.edu.ifma.livraria.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.edu.ifma.livraria.exception.EmprestimoNaoRealizadoException;
import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Usuario;

public class EmprestimoServiceTest {

    @Test
    public void deveEmprestarLivroQueNaoEstejaReservado() {
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro = new Livro("Clean Code", "Robert C. Martin");
        EmprestimoService emprestimoService = new EmprestimoService();

        Emprestimo emprestimoRealizado = emprestimoService.emprestaLivro(usuario, livro);

        assertTrue(emprestimoRealizado.getLivro().isEmprestado());
        assertEquals(1, emprestimoRealizado.getLivro().getHistorico().size());
        assertEquals("Clean Code", emprestimoRealizado.getLivro().getTitulo());
        assertEquals("James", emprestimoRealizado.getUsuario().getNome());
    }

    @Test
    public void naoDeveEmprestarLivroQuePosuiReserva() {
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro = new Livro("Clean Code", "Robert C. Martin");
        EmprestimoService emprestimoService = new EmprestimoService();

        livro.setReservado(true);

        EmprestimoNaoRealizadoException ex = assertThrows(EmprestimoNaoRealizadoException.class,
                () -> emprestimoService.emprestaLivro(usuario, livro),
                "Deveria lançar EmprestimoNaoRealizadoException");

        assertTrue(ex.getMessage().equals("Livro Reservado"));
    }

    @Test
    public void deveTerDataPrevistaCorreta() {
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro = new Livro("Clean Code", "Robert C. Martin");
        EmprestimoService emprestimoService = new EmprestimoService();

        Emprestimo emprestimo = emprestimoService.emprestaLivro(usuario, livro);

        assertEquals(LocalDate.now().plusDays(7), emprestimo.getDataPrevista());
    }

    @Test
    public void deveTerUsuarioComUmEmprestimo() {
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro = new Livro("Clean Code", "Robert C. Martin");
        EmprestimoService emprestimoService = new EmprestimoService();

        emprestimoService.emprestaLivro(usuario, livro);

        assertEquals(1, usuario.getEmprestimos().size());
    }

    @Test
    public void deveTerUsuarioComDoisEmprestimos() {
        EmprestimoService emprestimoService = new EmprestimoService();
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro = new Livro("Clean Code", "Robert C. Martin");
        Livro livro2 = new Livro("Domain Driven Design", "Eric Evans");

        emprestimoService.emprestaLivro(usuario, livro);
        emprestimoService.emprestaLivro(usuario, livro2);

        assertEquals(2, usuario.getEmprestimos().size());
    }

    @Test
    public void naoDeveTerTresEmprestimosSimultaneosParaUmUsuario() {
        EmprestimoService emprestimoService = new EmprestimoService();
        Usuario usuario = new Usuario("James", "A0001");
        Livro livro = new Livro("Clean Code", "Robert C. Martin");
        Livro livro2 = new Livro("Domain Driven Design", "Eric Evans");
        Livro livro3 = new Livro("The Pragmatic Programmer", "Andrew Hunt & David Thomas");

        emprestimoService.emprestaLivro(usuario, livro);
        emprestimoService.emprestaLivro(usuario, livro2);
        EmprestimoNaoRealizadoException ex = assertThrows(EmprestimoNaoRealizadoException.class,
                () -> emprestimoService.emprestaLivro(usuario, livro3),
                "Deveria lançar EmprestimoNaoRealizadoException.");

        assertTrue(ex.getMessage().equals("Máximo de 2 empréstimos simultaneos."));
    }

}