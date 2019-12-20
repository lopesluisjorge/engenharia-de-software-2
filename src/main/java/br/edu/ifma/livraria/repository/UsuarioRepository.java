package br.edu.ifma.livraria.repository;

import javax.persistence.EntityManager;

import br.edu.ifma.livraria.modelo.Usuario;

public class UsuarioRepository {

    private final EntityManager manager;

    public UsuarioRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Usuario salva(Usuario usuario) {
        manager.persist(usuario);
        return usuario;
    }

    public Usuario porMatricula(String matricula) {
        var jpql = "FROM Usuario u WHERE u.matricula = :matricula";

        return manager.createQuery(jpql, Usuario.class)
                .setParameter("matricula", matricula)
                .getSingleResult();
    }

}
