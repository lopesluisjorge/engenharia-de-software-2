package br.edu.ifma.es2.transportadora.repository;

import static br.edu.ifma.es2.transportadora.databuilder.DestinoBuilder.umaCidade;
import static br.edu.ifma.es2.transportadora.databuilder.ClienteBuilder.umCliente;
import static br.edu.ifma.es2.transportadora.databuilder.FreteBuilder.umFrete;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.ifma.es2.transportadora.entity.Destino;
import br.edu.ifma.es2.transportadora.entity.Cliente;
import br.edu.ifma.es2.transportadora.entity.Frete;

@DataJpaTest
class FreteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private DestinoRepository cidadeRepo;
    @Autowired
    private FreteRepository freteRepo;
    private Cliente cliente;
    private Destino cidade;
    private Frete frete;

    @BeforeEach
    public void setup() {
        cliente = umCliente().constroi();
        cidade = umaCidade().constroi();
        frete = umFrete().paraOCliente(cliente).comDestino(cidade).comValor(400.0d).constroi();
        clienteRepo.save(cliente);
        cidadeRepo.save(cidade);
    }

    @Test
    public void naoDeveSalvarFreteSemCliente() {
        frete.setCliente(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> freteRepo.save(frete),
                "Deveria Lançar ConstraintViolationException.");
        assertThat(ex.getMessage(), containsString("cliente deve ser preenchido"));
    }

    @Test
    public void naoDeveSalvarFreteSemDestino() {
        frete.setDestino(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> freteRepo.save(frete),
                "Deveria Lançar ConstraintViolationException.");
        assertThat(ex.getMessage(), containsString("destino deve ser preenchido"));
    }

    @Test
    public void naoDeveSalvarFreteSemDescricao() {
        frete.setDescricao(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> freteRepo.save(frete),
                "Deveria Lançar ConstraintViolationException.");

        assertThat(ex.getMessage(), containsString("descrição deve ser preenchida"));
    }

    @Test
    public void naoDeveSalvarFreteComDescricaoVazia() {
        frete.setDescricao("");

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> freteRepo.save(frete),
                "Deveria Lançar ConstraintViolationException.");

        assertThat(ex.getMessage(), containsString("descrição deve ser preenchida"));
    }

    @Test
    public void naoDeveSalvarFreteSemPeso() {
        frete.setPeso(null);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> freteRepo.save(frete),
                "Deveria Lançar ConstraintViolationException.");

        assertThat(ex.getMessage(), containsString("peso deve ser preenchido"));
    }

    @Test
    public void naoDeveSalvarFreteComPesoZero() {
        frete.setPeso(0d);

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> freteRepo.save(frete),
                "Deveria Lançar ConstraintViolationException.");

        assertThat(ex.getMessage(), containsString("peso deve ser maior que zero"));
    }

    @Test
    public void deveRecuperarFretesDoUsuarioOrdenadasPorValor() {
        var frete2 = umFrete().paraOCliente(cliente).comDestino(cidade).comValor(100.0d).constroi();
        var frete3 = umFrete().paraOCliente(cliente).comDestino(cidade).comValor(800.0d).constroi();

        var fretes = Arrays.asList(frete, frete2, frete3);

        freteRepo.saveAll(fretes);

        var fretesDoCliente = freteRepo.doCliente(cliente);

        assertEquals(3l, fretesDoCliente.size());
        assertThat(fretesDoCliente.get(0), is(frete3));
        assertThat(fretesDoCliente.get(1), is(frete));
        assertThat(fretesDoCliente.get(2), is(frete2));
    }

}
