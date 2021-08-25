package nox.services;

import nox.exceptions.ValidationException;
import org.springframework.stereotype.Service;

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