package br.edu.ifma.es2.transportadora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifma.es2.transportadora.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("FROM Cliente c WHERE c.telefone = :telefone")
    List<Cliente> buscaPor(String telefone);

}
