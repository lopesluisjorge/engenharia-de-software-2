package br.edu.ifma.livraria.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Usuario;

public class EmprestimoRepository {

    private final EntityManager manager;

    public EmprestimoRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Emprestimo salva(Emprestimo emprestimo) {
        manager.persist(emprestimo);
        return emprestimo;
    }

    public List<Livro> livrosEmprestados() {
        var jpql = "SELECT l FROM Livro l, Emprestimo e WHERE e.livro = l ORDER BY l.id";

        return manager.createQuery(jpql, Livro.class).getResultList();
    }

    public List<Livro> livrosEmAtraso() {
        var jpql = "SELECT l FROM Livro l, Emprestimo e WHERE e.livro = l AND e.dataPrevista < NOW() AND e.dataDevolucao IS NULL";

        return manager.createQuery(jpql, Livro.class).getResultList();
    }

    public List<Emprestimo> emprestimosEmAtraso() {
        var jpql = "FROM Emprestimo e WHERE e.dataDevolucao IS NULL AND e.dataPrevista < NOW()";

        return manager.createQuery(jpql, Emprestimo.class).getResultList();
    }
    
    public List<Emprestimo> emprestimosEmAtraso(Usuario usuario) {
        // TODO Implements
        return null;
    }

    public List<Emprestimo> devolvidosEmAtraso(Usuario usuario) {
        var jpql = "FROM Emprestimo e FETCH JOIN e.usuario WHERE e.usuario = :usuario AND e.dataDevolucao > e.dataPrevista";

        return manager.createQuery(jpql, Emprestimo.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }

    public List<Livro> livrosPorPeriodo(LocalDate inicio, LocalDate fim) {
        var jpql = "SELECT DISTINCT l FROM Livro l, Emprestimo e WHERE e.livro = l AND e.dataEmprestimo BETWEEN :inicio AND :fim";

        return manager.createQuery(jpql, Livro.class)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();
    }

    public Set<Usuario> usuariosComEmprestimosEmAtraso() {
        // TODO Implements
        return null;
    }

}
