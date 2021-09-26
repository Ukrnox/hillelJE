package nox.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such element!")
public class ElementNotFound extends RuntimeException{
    private String message;

    public ElementNotFound(String message) {
        this.message = message;
    }
}
