package br.edu.ifma.es2.transportadora.service;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifma.es2.transportadora.entity.CidadeEntrega;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Frete;
import br.edu.ifma.es2.transportadora.repository.FreteRepository;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepo;

    public final Integer VALOR_FIXO = 10;

    public Frete cadastrar(@Valid Frete frete) {
        var valor = calculaValorDoFrete(frete);

        frete.setValor(valor);

        return freteRepo.save(frete);
    }

    public BigDecimal calculaValorDoFrete(Frete frete) {
        var peso = new BigDecimal(frete.getPeso().toString());
        var fixo = new BigDecimal(VALOR_FIXO.toString());
        var taxa = frete.getCidadeEntrega().getTaxa();

        return (peso.multiply(fixo)).add(taxa).setScale(2);
    }

    public Frete comMaiorValorDo(Cliente cliente) {
        return freteRepo.comMaiorValor(cliente);
    }

    public CidadeEntrega cidadeComMaisFretes() {
        return freteRepo.cidadeComMaisFretes();
    }

}
