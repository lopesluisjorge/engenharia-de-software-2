package br.edu.ifma.es2.transportadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifma.es2.transportadora.entity.Frete;

public interface FreteRepository extends JpaRepository<Frete, Long>, FreteRepositoryQuery {

}
