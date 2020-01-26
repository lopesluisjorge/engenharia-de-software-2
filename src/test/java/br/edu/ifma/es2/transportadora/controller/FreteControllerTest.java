package br.edu.ifma.es2.transportadora.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import br.edu.ifma.es2.transportadora.controller.dto.FreteDto;
import br.edu.ifma.es2.transportadora.controller.form.FreteForm;
import br.edu.ifma.es2.transportadora.databuilder.ClienteBuilder;
import br.edu.ifma.es2.transportadora.databuilder.DestinoBuilder;
import br.edu.ifma.es2.transportadora.databuilder.FreteBuilder;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Destino;
import br.edu.ifma.es2.transportadora.repository.ClienteRepository;
import br.edu.ifma.es2.transportadora.repository.DestinoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FreteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private DestinoRepository destinoRepo;
    private Cliente cliente;
    private Destino destino;
    private FreteForm freteForm;

    @BeforeEach
    public void setup() {
        cliente = ClienteBuilder.umCliente().constroi();
        destino = DestinoBuilder.umaCidade().comTaxa(10d).constroi();
        clienteRepo.save(cliente);
        destinoRepo.save(destino);

        freteForm = FreteBuilder.umFrete()
                .paraOCliente(cliente)
                .comDestino(destino)
                .comDescricao("Descrição")
                .comPeso(100d)
                .constroiForm();
    }

    @Test
    public void deveCriarFrete() {
        var entidade = restTemplate.postForEntity("/fretes", freteForm, FreteDto.class);

        assertThat(entidade.getStatusCode(), is(HttpStatus.CREATED));
        assertEquals(1, entidade.getBody().getId());
        assertThat(entidade.getBody().getValor(), is(new BigDecimal("1010.00")));
    }

    @Test
    public void naoDeveCadastarFreteSemCliente() {
        freteForm.setClienteId(null);

        HttpEntity<FreteForm> entity = new HttpEntity<>(freteForm);
        var resposta = restTemplate.exchange("/fretes", HttpMethod.POST, entity, List.class);
        assertThat(resposta.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(resposta.getBody().get(0), is("clienteId deve ser preenchido."));
        assertEquals(1, resposta.getBody().size());

    }

    @Test
    public void naoDeveCadastarFreteSemCidadeDestino() {
        freteForm.setDestinoId(null);

        HttpEntity<FreteForm> entity = new HttpEntity<>(freteForm);
        var resposta = restTemplate.exchange("/fretes", HttpMethod.POST, entity, List.class);
        assertThat(resposta.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(resposta.getBody().get(0), is("destinoId deve ser preenchido."));
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    public void naoDeveCadastarFreteSemDescricao() {
        freteForm.setDescricao(null);

        HttpEntity<FreteForm> entity = new HttpEntity<>(freteForm);
        var resposta = restTemplate.exchange("/fretes", HttpMethod.POST, entity, List.class);
        assertThat(resposta.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(resposta.getBody().get(0), is("descrição deve ser preenchida."));
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    public void naoDeveCadastarFreteComDescricaoVazia() {
        freteForm.setDescricao("");

        HttpEntity<FreteForm> entity = new HttpEntity<>(freteForm);
        var resposta = restTemplate.exchange("/fretes", HttpMethod.POST, entity, List.class);
        assertThat(resposta.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(resposta.getBody().get(0), is("descrição deve ser preenchida."));
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    public void naoDeveCadastarFreteSemPeso() {
        freteForm.setPeso(null);

        HttpEntity<FreteForm> entity = new HttpEntity<>(freteForm);
        var resposta = restTemplate.exchange("/fretes", HttpMethod.POST, entity, List.class);
        assertThat(resposta.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(resposta.getBody().get(0), is("peso deve ser preenchido."));
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    public void naoDeveCadastarFreteComPesoMenorOuIgualAZero() {
        freteForm.setPeso(0d);

        HttpEntity<FreteForm> entity = new HttpEntity<>(freteForm);
        var resposta = restTemplate.exchange("/fretes", HttpMethod.POST, entity, List.class);
        assertThat(resposta.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(resposta.getBody().get(0), is("peso deve ser maior que zero."));
        assertEquals(1, resposta.getBody().size());
    }

}
