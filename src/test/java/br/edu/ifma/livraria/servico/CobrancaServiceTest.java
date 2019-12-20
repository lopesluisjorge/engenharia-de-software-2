package br.edu.ifma.livraria.servico;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.ifma.livraria.databuilder.UsuarioBuilder;
import br.edu.ifma.livraria.repository.EmprestimoRepository;

class CobrancaServiceTest {

    @InjectMocks
    private CobrancaService cobrancaService;
    @Mock
    private EnviadorDeEmail enviadorDeEmail;
    @Mock
    private EmprestimoRepository emprestimos;
    @Mock
    private Logger logger;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);

        cobrancaService = new CobrancaService(enviadorDeEmail, emprestimos, logger);
    }

    @Test
    void deveNotificarUsuariosEmAtraso() {
        var usuario1 = UsuarioBuilder.umUsuario().comEmail("joao@exaple.com").constroi();
        var usuario2 = UsuarioBuilder.umUsuario().comEmail("luis@exaple.com").constroi();

        var usuarios = Arrays.asList(usuario1, usuario2).stream().collect(Collectors.toSet());

        when(emprestimos.usuariosComEmprestimosEmAtraso()).thenReturn(usuarios);

        cobrancaService.notificaUsuariosEmAtraso();

        verify(emprestimos, times(1)).usuariosComEmprestimosEmAtraso();
        verify(enviadorDeEmail, times(1)).notificar(usuario1, TipoNotificacao.ATRASO);
        verify(enviadorDeEmail, times(1)).notificar(usuario2, TipoNotificacao.ATRASO);
        verifyNoMoreInteractions(emprestimos);
        verifyNoMoreInteractions(enviadorDeEmail);
    }

    @Test
    void deveNotificarUsuariosEmAtrasoMesmoComErrosNosEnviosAnteriores() {
        var usuario1 = UsuarioBuilder.umUsuario().comEmail("joao@exaple.com").constroi();
        var usuario2 = UsuarioBuilder.umUsuario().comEmail("luis@exaple.com").constroi();

        var usuarios = Arrays.asList(usuario1, usuario2).stream().collect(Collectors.toSet());

        when(emprestimos.usuariosComEmprestimosEmAtraso()).thenReturn(usuarios);
        
        RuntimeException excessaoLancada = new RuntimeException("Email Rejeitado");
        when(enviadorDeEmail.notificar(usuario1, TipoNotificacao.ATRASO)).thenThrow(excessaoLancada);

        cobrancaService.notificaUsuariosEmAtraso();

        verify(emprestimos, times(1)).usuariosComEmprestimosEmAtraso();
        verify(enviadorDeEmail, times(1)).notificar(usuario1, TipoNotificacao.ATRASO);
        verify(logger, times(1)).warn("Falha ao enviar email para " + usuario1.getEmail(), excessaoLancada);
        verify(enviadorDeEmail, times(1)).notificar(usuario2, TipoNotificacao.ATRASO);
    }

}
