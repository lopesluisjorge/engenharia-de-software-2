package br.edu.ifma.livraria.repository;

import javax.persistence.EntityManager;

import br.edu.ifma.livraria.modelo.Usuario;

public final class UsuarioRepository {

    private final EntityManager manager;

    public UsuarioRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Usuario salva(Usuario usuario) {
        manager.persist(usuario);
        return usuario;
    }

    public Usuario porMatricula(String matricula) {
        return manager.createQuery("FROM Usuario u WHERE u.matricula = :matricula", Usuario.class)
                .setParameter("matricula", matricula)
                .getSingleResult();
        
    }

}
