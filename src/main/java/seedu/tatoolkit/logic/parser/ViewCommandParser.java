package seedu.tatoolkit.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.commands.ViewCommand;
import seedu.tatoolkit.logic.parser.exceptions.InvalidIntegerException;
import seedu.tatoolkit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    public static final String MESSAGE_ONE_BASED_INDEXING =
            "Please input a 1-based index.";

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @param args The user input arguments.
     * @return A ViewCommand object representing the parsed command.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        try {
            index = ParserUtil.parseIndex(args, "person");
        } catch (ParseException | InvalidIntegerException pe) {
            throw new ParseException(pe.getMessage());
        }
        if (index.getOneBased() <= 0) {
            throw new ParseException(MESSAGE_ONE_BASED_INDEXING);
        }

        return new ViewCommand(index);
    }
}
