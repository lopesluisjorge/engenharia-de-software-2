package br.edu.ifma.livraria.modelo;

import static br.edu.ifma.livraria.databuilder.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    public void naoDeveTerEmprestimo() {
        Usuario usuario = umUsuario().constroi();

        assertEquals(0, usuario.getEmprestimos().size());
    }

}