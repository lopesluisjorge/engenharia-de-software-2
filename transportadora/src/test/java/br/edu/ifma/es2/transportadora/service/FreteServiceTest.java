package br.edu.ifma.es2.transportadora.service;

import static br.edu.ifma.es2.transportadora.databuilder.CidadeBuilder.umaCidade;
import static br.edu.ifma.es2.transportadora.databuilder.ClienteBuilder.umCliente;
import static br.edu.ifma.es2.transportadora.databuilder.FreteBuilder.umFrete;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import br.edu.ifma.es2.transportadora.entity.CidadeEntrega;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Frete;
import br.edu.ifma.es2.transportadora.repository.CidadeEntregaRepository;
import br.edu.ifma.es2.transportadora.repository.ClienteRepository;

@SpringBootTest
class FreteServiceTest {

    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private CidadeEntregaRepository cidadeRepo;
    @Autowired
    private FreteService freteService;
    private Cliente cliente;
    private CidadeEntrega destino;
    private Frete frete;

    @BeforeEach
    private void setup() {
        cliente = umCliente().constroi();
        destino = umaCidade().constroi();
        frete = umFrete().paraOCliente(cliente).comDestino(destino).comValor(400.0d).constroi();
    }

    @Test
    public void naoDeveCadastarFreteSemCliente() {
        cidadeRepo.save(destino);

        var ex = assertThrows(DataAccessException.class, () -> freteService.cadastrar(frete));

        assertThat(ex.getMessage(), containsString("Not-null property references a transient value"));
    }

    @Test
    public void naoDeveCadastarFreteSemCidadeDestino() {
        clienteRepo.save(cliente);

        var ex = assertThrows(DataAccessException.class, () -> freteService.cadastrar(frete));

        assertThat(ex.getMessage(), containsString("Not-null property references a transient value"));
    }

    @Test
    void deveCadastrarUmFrete() {
        clienteRepo.save(cliente);
        cidadeRepo.save(destino);

        freteService.cadastrar(frete);

        assertNotNull(frete.getId());
    }

    @Test
    public void deveRecuperarOValorDoFrete() {
        destino.setTaxa(new BigDecimal("20.00"));
        frete = umFrete().paraOCliente(cliente).comDestino(destino).comPeso(100.0d).constroi();

        var valorFrete = freteService.calculaValorDoFrete(frete);

        assertThat(valorFrete, is(new BigDecimal("1020.00")));
    }

    @Test
    public void deveTrazerOFreteComMaiorValor() {
        destino.setTaxa(new BigDecimal("20.00"));

        clienteRepo.save(cliente);
        cidadeRepo.save(destino);

        frete = umFrete().paraOCliente(cliente).comDestino(destino).comPeso(100.0d).constroi();
        var frete2 = umFrete().paraOCliente(cliente).comDestino(destino).comPeso(800.0d).constroi();
        var frete3 = umFrete().paraOCliente(cliente).comDestino(destino).comPeso(200.0d).constroi();

        Arrays.asList(frete, frete2, frete3).forEach(frete -> freteService.cadastrar(frete));

        var maiorFrete = freteService.comMaiorValorDo(cliente);

        assertThat(maiorFrete.getValor(), is(new BigDecimal("8020.00")));
    }
    
    @Test
    public void deveTrazerACidadeComMaisFretes() {
        destino.setNome("São Luís");
        destino.setUf("MA");
        var destino2 = umaCidade().comNome("São Paulo").comUf("SP").constroi();
        
        clienteRepo.save(cliente);
        cidadeRepo.save(destino);
        cidadeRepo.save(destino2);

        frete = umFrete().paraOCliente(cliente).comDestino(destino).comPeso(100.0d).constroi();
        var frete2 = umFrete().paraOCliente(cliente).comDestino(destino2).comPeso(800.0d).constroi();
        var frete3 = umFrete().paraOCliente(cliente).comDestino(destino2).comPeso(200.0d).constroi();

        Arrays.asList(frete, frete2, frete3).forEach(frete -> freteService.cadastrar(frete));

        var cidade = freteService.cidadeComMaisFretes();

        assertThat(cidade, is(destino2));
    }

}
