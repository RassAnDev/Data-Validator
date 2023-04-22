package hexlet.code;

import hexlet.code.schemas.NumberSchema;
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

    @Test
    public void testNumberSchema() {
        Validator validator = new Validator();
        NumberSchema schema = validator.number();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(12));
        assertTrue(schema.isValid(-1));
        assertTrue(schema.isValid("1"));
        assertTrue(schema.isValid(""));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("5"));
        assertTrue(schema.isValid(-10));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(8));
        assertTrue(schema.isValid(10.0));

        schema.positive();

        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-5));
        assertFalse(schema.isValid(-3.0));
        assertTrue(schema.isValid(1));
        assertTrue(schema.isValid(4.0));

        schema.range(3, 8);

        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-5));
        assertFalse(schema.isValid("1"));
        assertFalse(schema.isValid(11));
        assertTrue(schema.isValid(3));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(8));
    }
}
