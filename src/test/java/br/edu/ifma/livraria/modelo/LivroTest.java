package br.edu.ifma.livraria.modelo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LivroTest {
    
    @Test
    public void deveEstarEmprestado() {
        Livro livro = new Livro();
        livro.setEmprestado(true);

        assertTrue(livro.isEmprestado());
    }

    @Test
    public void naoPodeEstarEmprestado() {
        Livro livro = new Livro();

        assertFalse(livro.isEmprestado());
    }

    @Test
    public void deveEstarReservado() {
        Livro livro = new Livro();
        livro.setReservado(true);

        assertTrue(livro.isReservado());
    }

    @Test
    public void naoPodeEstarReservado() {
        Livro livro = new Livro();

        assertFalse(livro.isReservado());
    }

}