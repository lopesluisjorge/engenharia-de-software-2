package br.edu.ifma.es2.transportadora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifma.es2.transportadora.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByTelefone(String telefone);

}
