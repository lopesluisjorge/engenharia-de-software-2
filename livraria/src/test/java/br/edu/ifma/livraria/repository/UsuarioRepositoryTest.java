package br.edu.ifma.livraria.repository;

import static br.edu.ifma.livraria.databuilder.UsuarioBuilder.umUsuario;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifma.livraria.modelo.Usuario;

public class UsuarioRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory emf;
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    public static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("livrariaPU_test");
    }

    @BeforeEach
    public void beforeEach() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        usuarioRepository = new UsuarioRepository(manager);
    }

    @AfterEach
    public void afterEach() {
        manager.getTransaction().rollback();
    }

    @AfterAll
    public static void aferAll() {
        emf.close();
    }

    @Test
    public void deveSalvarUsuario() {
        Usuario usuario = umUsuario().comNome("James").comMatricula("A0001").constroi();

        usuarioRepository.salva(usuario);

        manager.flush();
        manager.clear();

        Usuario usuarioSalvo = usuarioRepository.porMatricula("A0001");

        assertNotNull(usuarioSalvo);
    }
}
