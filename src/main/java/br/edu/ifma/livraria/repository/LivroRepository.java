package br.edu.ifma.livraria.repository;

import java.util.List;

import javax.persistence.EntityManager;

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
        return manager
                .createQuery("FROM Emprestimo e WHERE e.livro = :livro ORDER BY e.id", Emprestimo.class)
                .setParameter("livro", livro)
                .getResultList();
    }

}
