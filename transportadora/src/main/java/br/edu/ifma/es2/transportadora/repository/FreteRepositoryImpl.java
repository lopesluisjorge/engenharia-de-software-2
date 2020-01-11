package br.edu.ifma.es2.transportadora.repository;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifma.es2.transportadora.entity.CidadeEntrega;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Frete;

public class FreteRepositoryImpl implements FreteRepositoryQuery {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Frete> doCliente(Cliente cliente) {
        var jpql = "FROM Frete f WHERE f.cliente = :cliente ORDER BY f.valor DESC NULLS LAST";

        var query = em.createQuery(jpql, Frete.class);
        query.setParameter("cliente", cliente);

        var resultado = query.getResultList();

        return Collections.unmodifiableList(resultado);
    }

    @Override
    public Frete comMaiorValor(Cliente cliente) {
        var jpql = "FROM Frete f WHERE f.cliente = :cliente AND f.valor = (SELECT MAX(agf.valor) FROM Frete agf WHERE agf.cliente = :cliente)";

        var query = em.createQuery(jpql, Frete.class);
        query.setParameter("cliente", cliente);

        return query.getSingleResult();
    }

    @Override
    public CidadeEntrega cidadeComMaisFretes() {
        var jpql = "SELECT COUNT(f), f.cidadeEntrega FROM Frete f GROUP BY f.cidadeEntrega ORDER BY COUNT(f) DESC";

        var query = em.createQuery(jpql, Object[].class);
        query.setMaxResults(1);

        var resultado = query.getResultList();

        return (CidadeEntrega) resultado.get(0)[1];
    }

}
