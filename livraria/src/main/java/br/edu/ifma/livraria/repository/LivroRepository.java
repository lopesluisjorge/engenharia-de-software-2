package br.edu.ifma.livraria.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;

public class LivroRepository {

    private final EntityManager manager;

    public LivroRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Livro salva(Livro livro) {
        manager.persist(livro);
        return livro;
    }

    public Livro atualiza(Livro livroSalvo) {
        manager.merge(livroSalvo);
        return livroSalvo;
    }

    public Livro porId(Long id) {
        var jpql = "FROM Livro l WHERE l.id = :id";

        return manager.createQuery(jpql, Livro.class).setParameter("id", id).getSingleResult();
    }

    public List<Emprestimo> historicoDeEmprestimoDo(Livro livro) {
        var jpql = "FROM Emprestimo e WHERE e.livro = :livro ORDER BY e.id";

        return manager.createQuery(jpql, Emprestimo.class).setParameter("livro", livro).getResultList();
    }

}
