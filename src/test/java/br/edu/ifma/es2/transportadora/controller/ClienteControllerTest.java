package br.edu.ifma.es2.transportadora.controller;

import static br.edu.ifma.es2.transportadora.databuilder.ClienteBuilder.umCliente;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import br.edu.ifma.es2.transportadora.controller.dto.ClienteDto;
import br.edu.ifma.es2.transportadora.controller.form.ClienteForm;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ClienteForm cliente;

    @BeforeEach
    public void setup() {
        cliente = umCliente().constroiForm();
    }

    @Test
    public void naoDeveCadastratClienteSemNome() {
        cliente.setNome(null);
        var entity = new HttpEntity<>(cliente);
        var resposta = restTemplate.exchange("/clientes", HttpMethod.POST, entity, List.class);
        assertThat(resposta.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    public void naoDeveCadastratClienteComNomeInvalido() {
        cliente.setNome("");
        var entity = new HttpEntity<>(cliente);
        var resposta = restTemplate.exchange("/clientes", HttpMethod.POST, entity, List.class);
        assertThat(resposta.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    public void deveCadastratCliente() {
        var resposta = restTemplate.postForEntity("/clientes", cliente, ClienteDto.class);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(cliente.getNome(), resposta.getBody().getNome());
        // assertEquals(cliente.getEndereco().toString(), resposta.getBody().getEndereco());
        assertEquals(cliente.getTelefone(), resposta.getBody().getTelefone());
    }

}
