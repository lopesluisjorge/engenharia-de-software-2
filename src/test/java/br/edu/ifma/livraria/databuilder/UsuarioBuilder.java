package br.edu.ifma.livraria.databuilder;

import br.edu.ifma.livraria.modelo.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

    private UsuarioBuilder() {
        usuario = new Usuario("Usuário", "A0001");
    }

    public static UsuarioBuilder umUsuario() {
        return new UsuarioBuilder();
    }

    public UsuarioBuilder comNome(String nome) {
        usuario = new Usuario(nome, usuario.getMatricula());
        return this;
    }

    public UsuarioBuilder comMatricula(String matricula) {
        usuario = new Usuario(usuario.getNome(), matricula);
        return this;
    }

    public Usuario constroi() {
        return usuario;
    }

}
