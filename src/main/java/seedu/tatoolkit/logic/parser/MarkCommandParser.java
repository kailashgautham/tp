package seedu.tatoolkit.logic.parser;

import static seedu.tatoolkit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_ABSENT;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_PRESENT;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.List;
import java.util.logging.Logger;

import seedu.tatoolkit.commons.core.LogsCenter;
import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.commands.MarkCommand;
import seedu.tatoolkit.logic.parser.exceptions.InvalidIntegerException;
import seedu.tatoolkit.logic.parser.exceptions.ParseException;
import seedu.tatoolkit.model.attendance.Week;

/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    private static final Logger logger = LogsCenter.getLogger(MarkCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns an MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEEK, PREFIX_PRESENT, PREFIX_ABSENT);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_WEEK)
            || (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PRESENT)
                && !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_ABSENT))
            || (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PRESENT)
                && argMultimap.getValue(PREFIX_PRESENT).get().isEmpty())
            || (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_ABSENT)
                && argMultimap.getValue(PREFIX_ABSENT).get().isEmpty()) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEEK, PREFIX_PRESENT, PREFIX_ABSENT);
        Week week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
        try {
            List<Index> presentIndices = ParserUtil.parseIndices(
                    argMultimap.getValue(PREFIX_PRESENT).orElse(""), "person");
            List<Index> absentIndices = ParserUtil.parseIndices(
                    argMultimap.getValue(PREFIX_ABSENT).orElse(""), "person");
            if (presentIndices.isEmpty() && absentIndices.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
            }
            return new MarkCommand(week, presentIndices, absentIndices);
        } catch (InvalidIntegerException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
