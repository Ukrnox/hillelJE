package nox.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="No such element!")
public class ValidationException extends RuntimeException {
    private String message;

    public ValidationException(String message) {
        this.message = message;
    }
}
