package br.com.fiap.aquamind.exception;

import java.time.LocalDateTime;

/**
 * Estrutura padrão para corpo de resposta de erro.
 */
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;     // breve descrição do tipo de erro (por ex.: "Not Found", "Bad Request")
    private String message;   // mensagem detalhada (por ex.: "Zona não encontrada com id = 5")
    private String path;      // URI que causou o erro, por ex.: "/api/zonas/5"

    public ErrorResponse() { }

    public ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path
    ) {
        this.timestamp = timestamp;
        this.status    = status;
        this.error     = error;
        this.message   = message;
        this.path      = path;
    }

    // ============================== GETTERS E SETTERS ==============================

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
