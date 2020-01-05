package br.edu.ifma.es2.transportadora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifma.es2.transportadora.entity.CidadeEntrega;

public interface CidadeEntregaRepository extends JpaRepository<CidadeEntrega, Long> {

    @Query("FROM CidadeEntrega c WHERE c.nome = :nome")
    List<CidadeEntrega> buscaPor(String nome);

}
