package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseSchema {
    protected Map<String, Object> checks = new LinkedHashMap<>();
    protected boolean hasRequirements = false;

    public abstract boolean isValid(Object value);

    public void addCheck(String name, Object value) {
        checks.put(name, value);
    }
}
