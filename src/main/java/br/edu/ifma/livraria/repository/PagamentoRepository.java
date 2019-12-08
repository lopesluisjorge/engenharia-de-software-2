package br.edu.ifma.livraria.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Pagamento;

public class PagamentoRepository {

	private EntityManager manager;

    public PagamentoRepository(EntityManager manager) {
        this.manager = manager;
	}

	public void salva(Pagamento pagamento) {
        manager.persist(pagamento);
	}

	public List<Pagamento> porEmprestimo(Emprestimo emprestimo) {
		return manager.createQuery("FROM Pagamento p WHERE p.emprestimo.id = :emprestimoId", Pagamento.class)
				.setParameter("emprestimoId", emprestimo.getId())
				.getResultList();
	}

}
