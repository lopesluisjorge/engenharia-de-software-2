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

    public Cliente buscarPorId(Long id) {
        return clientes.findById(id).get();
    }

    @Transactional
    public Cliente salva(Cliente cliente) {
        return clientes.save(cliente);
    }

    @Transactional
    public Cliente atualiza(Long id, Cliente cliente) {
        var busca = clientes.findById(id);
        if (busca.isPresent()) {
            var clienteSalvo = busca.get();
            clienteSalvo.setNome(cliente.getNome());
            clienteSalvo.setEndereco(cliente.getEndereco());
            clienteSalvo.setTelefone(cliente.getTelefone());
            return clienteSalvo;
        }

        throw new RuntimeException("Cliente não encontrado.");
    }

}
