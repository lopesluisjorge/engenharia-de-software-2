package br.edu.ifma.es2.transportadora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clientes;

    @Transactional
    public Cliente salva(Cliente cliente) {
        return clientes.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clientes.findById(id).get();
    }

}
