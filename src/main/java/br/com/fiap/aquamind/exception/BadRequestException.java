package br.com.fiap.aquamind.exception;

/**
 * Lançada quando a requisição do cliente é inválida por alguma regra de negócio
 * (por exemplo, falta de campos obrigatórios específicos ou alguma regra customizada).
 * Mapeada para HTTP 400.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
