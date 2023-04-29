package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    @Test
    public void testStringSchema() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(1));
        assertTrue(schema.isValid(new ArrayList<>()));
        assertTrue(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid("   "));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(1));
        assertFalse(schema.isValid(new ArrayList<>()));
        assertFalse(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid("some string"));

        schema.contains("foo");

        assertFalse(schema.isValid("what do you mean?"));
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid("what is the foobar?"));

        schema.minLength(19);

        assertFalse(schema.isValid("what do you mean?"));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("foobar?"));
        assertFalse(schema.isValid("sometimes I don't know who am I."));
        assertTrue(schema.isValid("what is the foobar?"));
        assertTrue(schema.isValid("sometimes I don't know what I'm doing here and what is the foobar."));
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
        assertTrue(schema.isValid(new ArrayList<>()));
        assertTrue(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid("   "));

        schema.positive();

        assertFalse(schema.isValid("1"));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-5));
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(25));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("5"));
        assertFalse(schema.isValid(new ArrayList<>()));
        assertFalse(schema.isValid(new HashMap<>()));
        assertFalse(schema.isValid(-10));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-3.0));
        assertTrue(schema.isValid(8));
        assertTrue(schema.isValid(10.0));

        schema.range(3, 8);

        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-5));
        assertFalse(schema.isValid("1"));
        assertFalse(schema.isValid(11));
        assertTrue(schema.isValid(3));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(8));
    }

    @Test
    public void testMapSchema() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        Map<String, String> dataMap = new HashMap<>();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(1));
        assertTrue(schema.isValid(new ArrayList<>()));
        assertTrue(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid("   "));

        schema.required();

        assertFalse(schema.isValid(null));
        dataMap.put("key", null);
        assertFalse(schema.isValid(dataMap));
        assertFalse(schema.isValid(new ArrayList<>()));
        assertTrue(schema.isValid(new HashMap<>()));
        dataMap.put("key", "value");
        assertTrue(schema.isValid(dataMap));

        schema.sizeof(2);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));
        assertFalse(schema.isValid(dataMap));
        dataMap.put("key1", "value1");
        dataMap.put("key2", "value2");
        assertFalse(schema.isValid(dataMap));
        dataMap.remove("key2");
        assertTrue(schema.isValid(dataMap));
    }

    @Test
    public void testMapSchemaWithShapedSchemas() {
        Validator validator = new Validator();
        MapSchema schema = validator.map().required();
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required().contains("An").minLength(3));
        schemas.put("age", validator.number().positive().range(10, 50));

        schema.shape(schemas);

        Map<String, Object> human = new HashMap<>();
        human.put("name", "Andrew");
        human.put("age", -5);
        assertFalse(schema.isValid(human));

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "");
        human1.put("age", null);
        assertFalse(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "");
        human2.put("age", -3);
        assertFalse(schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "Andrew");
        human3.put("age", "1");
        assertFalse(schema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", new ArrayList<>());
        human4.put("age", new ArrayList<>());
        assertFalse(schema.isValid(human4));

        Map<String, Object> human5 = new HashMap<>();
        human5.put("name", "Andrew");
        human5.put("age", 9);
        assertFalse(schema.isValid(human5));

        Map<String, Object> human6 = new HashMap<>();
        human6.put("name", "Max");
        human6.put("age", 35);
        assertFalse(schema.isValid(human6));

        Map<String, Object> human7 = new HashMap<>();
        human7.put("name", null);
        human7.put("age", 22);
        assertFalse(schema.isValid(human7));

        assertFalse(schema.isValid(new HashMap<>()));

        Map<String, Object> human8 = new HashMap<>();
        human8.put("name", "Andrew");
        human8.put("age", 25);
        assertTrue(schema.isValid(human8));

        Map<String, BaseSchema> schemas1 = new HashMap<>();
        schemas1.put("name", validator.string().required().contains("An").minLength(3));
        schemas1.put("age", validator.number().positive());

        schema.shape(schemas1);

        Map<String, Object> human9 = new HashMap<>();
        human9.put("name", "Andrew");
        human9.put("age", null);
        assertTrue(schema.isValid(human9));

        schema.required();

        Map<String, Object> human10 = new HashMap<>();
        human10.put("name", "Andrew");
        human10.put("age", null);
        assertFalse(schema.isValid(human10));

        Map<String, BaseSchema> schemas2 = new HashMap<>();
        schemas2.put("name", validator.string().required().minLength(3));
        schemas2.put("age", validator.number().required().positive());

        schema.shape(schemas2);

        Map<String, Object> human11 = new HashMap<>();
        human11.put("name", "Andrew");
        human11.put("age", null);
        assertFalse(schema.isValid(human11));

        Map<String, Object> human12 = new HashMap<>();
        human12.put("name", "An");
        human12.put("age", 10);
        assertFalse(schema.isValid(human12));

        Map<String, Object> human13 = new HashMap<>();
        human13.put("name", "Andrew");
        human13.put("age", 25);
        assertTrue(schema.isValid(human13));
    }
}
