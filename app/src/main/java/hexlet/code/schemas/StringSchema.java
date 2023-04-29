package hexlet.code.schemas;

public class StringSchema extends BaseSchema {
    private static Integer requiredMinLength;
    private static String requiredSubstring;

    public StringSchema required() {
        hasRequirements = true;
        addCheck("required", value -> value instanceof String && !((String) value).isEmpty());
        return this;
    }

    public StringSchema minLength(Integer length) {
        requiredMinLength = length;
        addCheck("minLength", value -> value instanceof String
                && value.toString().length() >= requiredMinLength);
        return this;
    }

    public StringSchema contains(String substring) {
        requiredSubstring = substring;
        addCheck("contains", value -> value instanceof String
                && value.toString().contains(requiredSubstring));
        return this;
    }
}
