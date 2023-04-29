package hexlet.code.schemas;
public final class NumberSchema extends BaseSchema {
    private static Integer rangeFrom;
    private static Integer rangeTo;
    public NumberSchema required() {
        hasRequirements = true;
        addCheck("required", value -> value instanceof Number);
        return this;
    }

    public NumberSchema positive() {
        addCheck("positive", value -> ((value instanceof Number
                && ((Number) value).intValue() > 0) || value == null));
        return this;
    }

    public NumberSchema range(Integer from, Integer to) {
        rangeFrom = from;
        rangeTo = to;
        addCheck("range", value -> (value instanceof Number && ((Number) value).intValue() >= rangeFrom)
                && ((Number) value).intValue() <= rangeTo);
        return this;
    }
}
