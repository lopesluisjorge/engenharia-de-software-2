package br.edu.ifma.livraria.servico;

import java.util.List;

import org.apache.logging.log4j.Logger;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Usuario;
import br.edu.ifma.livraria.repository.EmprestimoRepository;

public class CobrancaService {

    private final EnviadorDeEmail enviadorDeEmail;
    private final EmprestimoRepository emprestimos;
    private final Logger logger;

    public CobrancaService(EnviadorDeEmail enviadorDeEmail, EmprestimoRepository emprestimos, Logger logger) {
        this.enviadorDeEmail = enviadorDeEmail;
        this.emprestimos = emprestimos;
        this.logger = logger;
    }

    public void notificaUsuariosEmAtraso() {
        var usuariosEmAtraso = emprestimos.usuariosComEmprestimosEmAtraso();
        
        usuariosEmAtraso.forEach(usuario -> notificarEmAtraso(usuario));
    }

    private void notificarEmAtraso(final Usuario usuario) {
        try {
            enviadorDeEmail.notificar(usuario, TipoNotificacao.ATRASO);
        } catch (RuntimeException e) {
            logger.warn("Falha ao enviar email para " + usuario.getEmail(), e);
        }
    }


}
