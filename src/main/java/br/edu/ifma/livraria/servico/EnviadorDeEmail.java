package br.edu.ifma.livraria.servico;

import br.edu.ifma.livraria.modelo.Usuario;

public interface EnviadorDeEmail {

    Email notificar(final Usuario usuario, TipoNotificacao tipo);

}
