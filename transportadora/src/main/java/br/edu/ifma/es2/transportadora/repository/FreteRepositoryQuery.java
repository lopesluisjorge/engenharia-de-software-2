package br.edu.ifma.es2.transportadora.repository;

import java.util.List;

import br.edu.ifma.es2.transportadora.entity.Destino;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Frete;

interface FreteRepositoryQuery {

    List<Frete> doCliente(Cliente cliente);

    Frete comMaiorValor(Cliente cliente);

    Destino cidadeComMaisFretes();

}
