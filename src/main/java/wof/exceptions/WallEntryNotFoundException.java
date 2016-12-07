package wof.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "WallEntry not found.")
public class WallEntryNotFoundException extends RuntimeException {
}
