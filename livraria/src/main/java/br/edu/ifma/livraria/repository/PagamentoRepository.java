package br.edu.ifma.livraria.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Pagamento;

public class PagamentoRepository {

    private final EntityManager manager;

    public PagamentoRepository(EntityManager manager) {
        this.manager = manager;
    }

    public void salva(Pagamento pagamento) {
        manager.persist(pagamento);
    }

    public List<Pagamento> porEmprestimo(Emprestimo emprestimo) {
        var jpql = "FROM Pagamento p WHERE p.emprestimo.id = :emprestimoId";

        return manager.createQuery(jpql, Pagamento.class)
                .setParameter("emprestimoId", emprestimo.getId()).getResultList();
    }

}
