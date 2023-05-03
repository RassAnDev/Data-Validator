package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    public StringSchema required() {
        hasRequirements = true;
        addCheck("required", value -> value instanceof String && !((String) value).isEmpty());
        return this;
    }

    public StringSchema minLength(Integer length) {
        addCheck("minLength", value -> ((String) value).length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck("contains", value -> ((String) value).contains(substring));
        return this;
    }
}
