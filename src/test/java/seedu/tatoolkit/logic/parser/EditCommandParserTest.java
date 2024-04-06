package seedu.tatoolkit.logic.parser;

import static seedu.tatoolkit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.CLASS_GROUP_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_CLASS_GROUP_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tatoolkit.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tatoolkit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tatoolkit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tatoolkit.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tatoolkit.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.Messages;
import seedu.tatoolkit.logic.commands.EditCommand;
import seedu.tatoolkit.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tatoolkit.model.person.Email;
import seedu.tatoolkit.model.person.Name;
import seedu.tatoolkit.model.person.Phone;
import seedu.tatoolkit.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    private String indexType = "person";

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, String.format(MESSAGE_INVALID_INDEX, indexType));

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, String.format(MESSAGE_INVALID_INDEX, indexType));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + CLASS_GROUP_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + NAME_DESC_AMY + TELEGRAM_DESC_AMY + GITHUB_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withClassGroup(VALID_CLASS_GROUP_AMY).withTelegram(VALID_TELEGRAM_AMY)
                .withGithub(VALID_GITHUB_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));
    }
}
