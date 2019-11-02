package br.edu.ifma.livraria.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    public void naoDeveTerEmprestimo() {
        Usuario usuario = new Usuario("James", "A0001");

        assertEquals(0, usuario.getEmprestimos().size());
    }

}