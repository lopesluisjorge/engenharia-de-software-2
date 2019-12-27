package br.edu.ifma.livraria.exception;

public class EmprestimoNaoRealizadoException extends RuntimeException {

    private static final long serialVersionUID = 1040899426570868587L;

    public EmprestimoNaoRealizadoException(String menssagem) {
        super(menssagem);
    }

    public EmprestimoNaoRealizadoException(String menssagem, Throwable causa) {
        super(menssagem, causa);
    }

}