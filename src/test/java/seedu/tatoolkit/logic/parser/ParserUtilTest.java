package seedu.tatoolkit.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tatoolkit.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.tatoolkit.testutil.Assert.assertThrows;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tatoolkit.testutil.TypicalIndexes.INDEX_LIST;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.tatoolkit.commons.core.index.Index;
import seedu.tatoolkit.logic.parser.exceptions.InvalidIntegerException;
import seedu.tatoolkit.logic.parser.exceptions.ParseException;
import seedu.tatoolkit.model.attendance.Week;
import seedu.tatoolkit.model.person.Email;
import seedu.tatoolkit.model.person.Name;
import seedu.tatoolkit.model.person.Phone;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_WEEK = "1";
    private static final String INVALID_WEEK = "100";
    private static final String VALID_INDICES = "1, 2, 3";
    private static final String INVALID_INDICES = "1, 2, @";

    private static final String WHITESPACE = " \t\r\n";

    private String indexType = "person";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a", indexType));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsInvalidIntegerException() {
        assertThrows(InvalidIntegerException.class, String.format(MESSAGE_INVALID_INDEX, indexType), ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1), indexType));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1", indexType));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  ", indexType));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Optional<Phone> expectedPhone = Optional.of(new Phone(VALID_PHONE));
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Optional<Phone> expectedPhone = Optional.of(new Phone(VALID_PHONE));
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseWeek_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWeek((String) null));
    }

    @Test
    public void parseWeek_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek(INVALID_WEEK));
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek("not a number"));
    }

    @Test
    public void parseWeek_validValueWithoutWhitespace_returnsWeek() throws Exception {
        Index index = Index.fromOneBased(Integer.parseInt(VALID_WEEK));
        Week expectedWeek = new Week(index);
        assertEquals(expectedWeek, ParserUtil.parseWeek(VALID_WEEK));
    }

    @Test
    public void parseWeek_validValueWithWhitespace_returnsWeek() throws Exception {
        String weekWithWhitespace = WHITESPACE + VALID_WEEK + WHITESPACE;
        Index index = Index.fromOneBased(Integer.parseInt(VALID_WEEK));
        Week expectedWeek = new Week(index);
        assertEquals(expectedWeek, ParserUtil.parseWeek(weekWithWhitespace));
    }

    @Test
    public void parseIndices_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIndices(null, indexType));
    }

    @Test
    public void parseIndices_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndices("a b", indexType));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndices("1, b", indexType));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndices("1 2 3", indexType));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndices("   ", indexType));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndices(" ,,  ", indexType));
    }

    @Test
    public void parseIndices_outOfRangeInput_throwsParseException() {
        assertThrows(InvalidIntegerException.class, String.format(MESSAGE_INVALID_INDEX, indexType), ()
                -> ParserUtil.parseIndices(Long.toString(Integer.MAX_VALUE + 1), indexType));

        assertThrows(InvalidIntegerException.class, String.format(MESSAGE_INVALID_INDEX, indexType), ()
                -> ParserUtil.parseIndices("1, " + Long.toString(Integer.MAX_VALUE + 1), indexType));
    }

    @Test
    public void parseIndices_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_LIST, ParserUtil.parseIndices("1,2,3", indexType));

        // Leading and trailing whitespaces
        assertEquals(INDEX_LIST, ParserUtil.parseIndices(" 1 , 2 , 3 ", indexType));

        // Trailing comma
        assertEquals(INDEX_LIST, ParserUtil.parseIndices(" 1 , 2 , 3, ", indexType));
    }
}
