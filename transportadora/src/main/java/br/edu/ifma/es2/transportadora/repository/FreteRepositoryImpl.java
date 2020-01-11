package br.edu.ifma.es2.transportadora.repository;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Frete;

public class FreteRepositoryImpl implements FreteRepositoryQuery {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Frete> doCliente(Cliente cliente) {
        var jpql = "SELECT f FROM Frete f, Cliente c WHERE c = :cliente AND f.cliente = c ORDER BY f.valor";

        var query = em.createQuery(jpql, Frete.class);
        query.setParameter("cliente", cliente);

        var resultado = query.getResultList();

        return Collections.unmodifiableList(resultado);
    }

}
