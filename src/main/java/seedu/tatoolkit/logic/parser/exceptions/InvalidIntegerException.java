package seedu.tatoolkit.logic.parser.exceptions;

import seedu.tatoolkit.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class InvalidIntegerException extends IllegalValueException {

    public InvalidIntegerException(String message) {
        super(message);
    }

    public InvalidIntegerException(String message, Throwable cause) {
        super(message, cause);
    }
}
