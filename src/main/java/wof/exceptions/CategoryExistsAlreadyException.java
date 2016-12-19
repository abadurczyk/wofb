package wof.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Category exists already")
public class CategoryExistsAlreadyException extends RuntimeException {
}
