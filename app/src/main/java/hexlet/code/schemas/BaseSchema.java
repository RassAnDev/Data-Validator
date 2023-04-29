package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema {
    protected Map<String, Predicate> checks = new LinkedHashMap<>();
    protected boolean hasRequirements = false;

    public final boolean isValid(Object value) {

        if (hasRequirements && value == null) {
            return false;
        }

        return checks.values().stream().allMatch(check -> check.test(value));
    }

    protected void addCheck(String name, Predicate validate) {
        checks.put(name, validate);
    }
}
