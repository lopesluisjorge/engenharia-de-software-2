package br.edu.ifma.es2.transportadora.repository;

import static br.edu.ifma.es2.transportadora.databuilder.CidadeBuilder.umaCidade;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.ifma.es2.transportadora.entity.CidadeEntrega;

@DataJpaTest
class CidadeEntregaRepositoryTest {

    private CidadeEntrega cidade;
    @Autowired
    private CidadeEntregaRepository cidadeRepo;

    @BeforeEach
    public void setup() {
        cidade = umaCidade().comNome("São Luís").comUf("MA").comTaxa(new BigDecimal("10.00")).constroi();
    }

    @BeforeEach
    public void destroy() {
        cidadeRepo.deleteAll();
    }

    @Test
    public void deveSalvarCidade() {
        cidadeRepo.save(cidade);

        var salvos = cidadeRepo.findAll();

        assertEquals(1l, salvos.size());
        assertNotNull(salvos.get(0).getId());
        assertThat(salvos.get(0).getNome(), is("São Luís"));
    }

    @Test
    public void deveBuscarPorCidade() {
        cidadeRepo.save(cidade);
        cidadeRepo.save(umaCidade().comNome("São Luís").comUf("PA").comTaxa(new BigDecimal("15.00")).constroi());

        var salvos = cidadeRepo.buscaPor("São Luís");

        assertEquals(2l, salvos.size());
        assertNotNull(salvos.get(0).getId());
        assertThat(salvos.get(0).getUf(), is("MA"));
        assertThat(salvos.get(1).getUf(), is("PA"));
    }

}
