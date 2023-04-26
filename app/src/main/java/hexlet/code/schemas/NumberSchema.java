package hexlet.code.schemas;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NumberSchema extends BaseSchema {
    private static Integer rangeFrom;
    private static Integer rangeTo;
    public NumberSchema required() {
        addCheck("required", true);
        hasRequirements = true;
        return this;
    }

    public NumberSchema positive() {
        addCheck("positive", true);
        return this;
    }

    public NumberSchema range(Integer from, Integer to) {
        rangeFrom = from;
        rangeTo = to;
        addCheck("range", true);
        return this;
    }

    public boolean validate(Object value) {
        boolean isNumber = value instanceof Number;
        Integer intValue = 0;
        List<Boolean> passedChecks = new LinkedList<>();

        if (isNumber) {
            intValue = ((Number) value).intValue();
        }

        for (Map.Entry<String, Boolean> check : checks.entrySet()) {
            if (Objects.equals(check.getKey(), "required")) {
                passedChecks.add(isNumber);
            } else if (Objects.equals(check.getKey(), "positive")) {
                passedChecks.add((isNumber && intValue > 0) || value == null);
            } else if (Objects.equals(check.getKey(), "range")) {
                passedChecks.add(isNumber && intValue >= rangeFrom && intValue <= rangeTo);
            }
        }
        return passedChecks.stream().allMatch(v -> v.equals(true));
    }
}
