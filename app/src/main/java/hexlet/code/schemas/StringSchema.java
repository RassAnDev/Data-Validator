package hexlet.code.schemas;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StringSchema extends BaseSchema {
    private static Integer requiredMinLength;
    private static String requiredSubstring;

    public StringSchema required() {
        addCheck("required", true);
        hasRequirements = true;
        return this;
    }

    public StringSchema minLength(Integer length) {
        requiredMinLength = length;
        addCheck("minLength", true);
        return this;
    }

    public StringSchema contains(String substring) {
        requiredSubstring = substring;
        addCheck("contains", true);
        return this;
    }

    public boolean validate(Object value) {
        boolean isString = value instanceof String;
        List<Boolean> passedChecks = new LinkedList<>();

        for (Map.Entry<String, Boolean> check : checks.entrySet()) {
            if (Objects.equals(check.getKey(), "required")) {
                passedChecks.add(isString && !((String) value).isEmpty());
            } else if (Objects.equals(check.getKey(), "minLength")) {
                passedChecks.add(isString && value.toString().length() >= requiredMinLength);
            } else if (Objects.equals(check.getKey(), "contains")) {
                passedChecks.add(isString && value.toString().contains(requiredSubstring));
            }
        }
        return passedChecks.stream().allMatch(v -> v.equals(true));
    }
}
