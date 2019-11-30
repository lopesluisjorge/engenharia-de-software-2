package br.edu.ifma.livraria.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;

public final class LivroRepository {

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

    public List<Emprestimo> historicoDeEmprestimoDo(Livro livro) {
        TypedQuery<Emprestimo> query = manager
                .createQuery("SELECT e FROM Emprestimo e WHERE e.livro.id = :livroId", Emprestimo.class);
        query.setParameter("livroId", livro.getId());
        return query.getResultList();
    }

}
