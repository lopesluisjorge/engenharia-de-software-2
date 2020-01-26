package br.edu.ifma.es2.transportadora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifma.es2.transportadora.entity.Destino;
import br.edu.ifma.es2.transportadora.repository.DestinoRepository;

@Service
public class DestinoService {

    @Autowired
    private DestinoRepository destinos;
    
    @Transactional(readOnly = true)
    public Destino buscaPorId(Long id) {
        return destinos.findById(id).orElse(null);
    }
}
