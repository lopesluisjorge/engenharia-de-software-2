package br.edu.ifma.es2.transportadora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifma.es2.transportadora.entity.Destino;

public interface DestinoRepository extends JpaRepository<Destino, Long> {

    @Query("FROM Destino c WHERE c.nome = :nome")
    List<Destino> buscaPor(String nome);

}
