package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    @Test
    public void testStringSchema() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));

        schema.contains("foo");

        assertTrue(schema.isValid("what is the foobar?"));
        assertFalse(schema.isValid("what do you mean?"));
        assertFalse(schema.isValid(null));

        schema.minLength(19);

        assertTrue(schema.isValid("what is the foobar?"));
        assertFalse(schema.isValid("what do you mean?"));
        assertFalse(schema.isValid("sometimes i don't who am i and what i'm doing here"));
        assertFalse(schema.isValid(null));
    }
}
