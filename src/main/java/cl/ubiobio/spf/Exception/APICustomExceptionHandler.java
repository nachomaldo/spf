package cl.ubiobio.spf.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class APICustomExceptionHandler extends ResponseEntityExceptionHandler  {
    private HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    private HttpStatus noContent = HttpStatus.NO_CONTENT;
    private HttpStatus notFound = HttpStatus.NOT_FOUND;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<String> errorDetail = new ArrayList<>();
        errorDetail.add(ex.getLocalizedMessage());

        APICustomException exception = new APICustomException("El cuerpo de la petición no ha sido detectado, o es inválido",
                errorDetail, badRequest);
        return new ResponseEntity<>(exception, badRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errorDetails = new ArrayList<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            errorDetails.add(objectError.getDefaultMessage());
        }

        APICustomException exception = new APICustomException("Uno o más argumentos ingresados son inválidos",
                errorDetails, badRequest);
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errorDetails = new ArrayList<>();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errorDetails.add(violation.getMessage());
        }

        APICustomException exception = new APICustomException("Error de restricción", errorDetails, badRequest);
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler(value = {PedidoConflictException.class})
    public ResponseEntity<Object> handleAppointmentConflictException(PedidoConflictException ex) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        APICustomException exception = new APICustomException(ex.getMessage(), conflict);

        return new ResponseEntity<>(exception, conflict);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handlePatientNotFoundException(ResourceNotFoundException ex) {
        APICustomException exception = new APICustomException(ex.getMessage(), notFound);
        return new ResponseEntity<>(exception, notFound);
    }

    @ExceptionHandler(value = {InvalidIdException.class})
    public ResponseEntity<Object> handleMissingIdException(InvalidIdException ex) {
        APICustomException exception = new APICustomException(ex.getMessage(), badRequest);
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler(value = {ResourceDeletedException.class})
    public ResponseEntity<Object> handleResourceDeletedException(ResourceDeletedException ex) {
        APICustomException exception = new APICustomException(ex.getMessage(), badRequest);
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler(value = {ResourceDeletionNotPossibleException.class})
    public ResponseEntity<Object> handleResourceDeletionNotPossibleException(ResourceDeletionNotPossibleException ex) {
        APICustomException exception = new APICustomException(ex.getMessage(), badRequest);
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler(value = {InvalidParameterException.class})
    public ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException ex) {
        APICustomException exception = new APICustomException(ex.getMessage(), badRequest);
        return new ResponseEntity<>(exception, badRequest);
    }
}
