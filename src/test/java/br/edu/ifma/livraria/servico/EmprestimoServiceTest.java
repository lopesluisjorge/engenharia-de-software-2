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

}