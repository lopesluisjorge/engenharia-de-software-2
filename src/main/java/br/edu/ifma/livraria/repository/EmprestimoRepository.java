package br.edu.ifma.livraria.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;

public class EmprestimoRepository {

    private EntityManager manager;

    public EmprestimoRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Emprestimo salva(Emprestimo emprestimo) {
        manager.persist(emprestimo);
        return emprestimo;
    }

    public List<Livro> livrosEmprestados() {
        return manager
                .createQuery("SELECT l FROM Livro l, Emprestimo e WHERE e.livro = l ORDER BY l.id", Livro.class)
                .getResultList();
    }

    public List<Livro> livrosEmAtraso() {
        return manager
                .createQuery("SELECT l FROM Livro l, Emprestimo e WHERE e.livro = l AND e.dataPrevista < now() AND e.dataDevolucao is null", Livro.class)
                .getResultList();
    }

}
