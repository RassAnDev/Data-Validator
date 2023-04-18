package hexlet.code.schemas;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StringSchema extends BaseSchema {
    public StringSchema required() {
        addCheck("required", true);
        hasRequirements = true;
        return this;
    }

    public StringSchema minLength(Integer length) {
        addCheck("minLength", length);
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck("contains", substring);
        return this;
    }

    public boolean isValid(Object value) {
        List<Boolean> passedChecks = new LinkedList<>();
        if (!hasRequirements) {
            return true;
        }

        if (value == null) {
            return false;
        }

        for (Map.Entry<String, Object> check : checks.entrySet()) {
            if (Objects.equals(check.getKey(), "required")) {
                passedChecks.add(value instanceof String && !((String) value).isEmpty());
            } else if (Objects.equals(check.getKey(), "minLength")) {
                passedChecks.add(check.getValue().equals(value.toString().length()));
            } else if (Objects.equals(check.getKey(), "contains")) {
                passedChecks.add(value.toString().contains(check.getValue().toString()));
            }
        }
        return passedChecks.stream().allMatch(v -> v.equals(true));
    }
}
