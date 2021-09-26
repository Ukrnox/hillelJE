package nox.services;

import lombok.extern.slf4j.Slf4j;
import nox.exceptions.ValidationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidationService {
    public Long idValidation(String id) throws ValidationException {
        try {
            return Long.parseLong(id);
        }
        catch (NumberFormatException e) {
            throw new ValidationException("Wrong id format");
        }
    }
}