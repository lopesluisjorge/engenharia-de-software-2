package br.edu.ifma.es2.transportadora.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifma.es2.transportadora.controller.form.FreteForm;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Destino;
import br.edu.ifma.es2.transportadora.entity.Frete;
import br.edu.ifma.es2.transportadora.repository.FreteRepository;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepo;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private DestinoService destinoService;

    public final Integer VALOR_FIXO = 10;

    @Transactional(readOnly = true)
    public Frete buscaPorId(Long id) {
        return freteRepo.getOne(id);
    }

    public Frete cadastrar(FreteForm freteForm) {
        var frete = freteForm.converter(clienteService, destinoService);

        return cadastrar(frete);
    }

    @Transactional
    Frete cadastrar(Frete frete) {
        var valor = calculaValorDoFrete(frete);

        frete.setValor(valor);

        return freteRepo.save(frete);
    }

    public BigDecimal calculaValorDoFrete(Frete frete) {
        var peso = new BigDecimal(frete.getPeso().toString());
        var fixo = new BigDecimal(VALOR_FIXO.toString());
        var taxa = frete.getDestino().getTaxa();

        return (peso.multiply(fixo)).add(taxa).setScale(2);
    }

    @Transactional(readOnly = true)
    public Frete comMaiorValorDo(Cliente cliente) {
        return freteRepo.comMaiorValor(cliente);
    }

    @Transactional(readOnly = true)
    public Destino cidadeComMaisFretes() {
        return freteRepo.cidadeComMaisFretes();
    }

}
