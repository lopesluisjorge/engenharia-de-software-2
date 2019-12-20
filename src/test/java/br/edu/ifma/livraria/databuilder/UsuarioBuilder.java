package br.edu.ifma.livraria.databuilder;

import br.edu.ifma.livraria.modelo.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

    private UsuarioBuilder() {
        usuario = new Usuario("Usuário", "A0001", "usuario@exemplo");
    }

    public static UsuarioBuilder umUsuario() {
        return new UsuarioBuilder();
    }

    public UsuarioBuilder comNome(String nome) {
        usuario = new Usuario(nome, usuario.getMatricula(), usuario.getEmail());
        return this;
    }

    public UsuarioBuilder comMatricula(String matricula) {
        usuario = new Usuario(usuario.getNome(), matricula, usuario.getEmail());
        return this;
    }
    
    public UsuarioBuilder comEmail(String email) {
        usuario = new Usuario(usuario.getNome(), usuario.getMatricula(), email);
        return this;
    }

    public Usuario constroi() {
        return usuario;
    }

}
