package wof.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Headline must be set.")
public class HeadlineCannotBeEmptyException extends RuntimeException {
}
