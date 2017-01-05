package wof.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Category name not supported.")
public class UnsupportedCategoryNameException extends RuntimeException {
}
