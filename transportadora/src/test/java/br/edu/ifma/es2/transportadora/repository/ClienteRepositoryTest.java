package br.edu.ifma.es2.transportadora.repository;

import static br.edu.ifma.es2.transportadora.databuilder.ClienteBuilder.umCliente;
import static br.edu.ifma.es2.transportadora.databuilder.EnderecoBuilder.umEndereco;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.ifma.es2.transportadora.entity.Cliente;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepo;
    private Cliente cliente;
    
    @BeforeEach
    public void before() {
        cliente = umCliente().comNome("João")
                             .comTelefone("98988778787")
                             .comEndereco(umEndereco().constroi())
                             .constroi();
    }

    @AfterEach
    public void after() {
        clienteRepo.deleteAll();
    }

    @Test
    public void deveCadastrarCliente() {
        clienteRepo.save(cliente);

        var salvos = clienteRepo.findAll();

        assertEquals(1, salvos.size());
        assertNotNull(salvos.get(0).getId());
        assertThat("João", is(salvos.get(0).getNome()));
        assertThat("98988778787", is(salvos.get(0).getTelefone()));
        assertThat(umEndereco().constroi(), is(salvos.get(0).getEndereco()));
    }

    @Test
    public void deveBuscarPorTelefone() {
        clienteRepo.save(cliente);

        var buscado = clienteRepo.findByTelefone("98988778787");

        assertEquals(1l, buscado.size());
        assertEquals("João", buscado.get(0).getNome());
    }

    @Test
    public void naoDeveEncontrarPorTelefoneNaoCadastrado() {
        var buscado = clienteRepo.findByTelefone("98988778787");
        assertEquals(0l, buscado.size());
    }

}
