package seedu.tatoolkit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tatoolkit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_INDICES;

import java.util.List;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.commands.DeleteNoteCommand;
import seedu.tatoolkit.logic.parser.exceptions.InvalidIntegerException;
import seedu.tatoolkit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteNoteCommand object
 */
public class DeleteNoteCommandParser implements Parser<DeleteNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNoteCommand
     * and returns an DeleteNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDICES);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_INDICES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE));
        }

        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble(), "person");
        } catch (ParseException | InvalidIntegerException pe) {
            throw new ParseException(pe.getMessage());
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDICES);
        try {
            List<Index> notes = ParserUtil.parseIndices(argMultimap.getValue(PREFIX_INDICES).get(), "note");
            return new DeleteNoteCommand(personIndex, notes);
        } catch (InvalidIntegerException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
