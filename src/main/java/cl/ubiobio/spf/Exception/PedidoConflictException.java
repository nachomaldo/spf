package cl.ubiobio.spf.Exception;


// Excepción para manejar el conflicto de horarios.
public class PedidoConflictException extends RuntimeException {

    public PedidoConflictException(String message) {
        super(message);
    }

    public PedidoConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}