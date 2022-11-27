package cl.ubiobio.spf.Exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class APICustomException {
    private String message;
    private List<String> details;
    private HttpStatus httpStatus;

    public APICustomException(String message, List<String> details, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.details = details;
        this.httpStatus = httpStatus;
    }

    public APICustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
