package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseSchema {
    protected Map<String, Boolean> checks = new LinkedHashMap<>();
    protected boolean hasRequirements = false;

    protected abstract boolean validate(Object value);

    public final boolean isValid(Object value) {

        if (hasRequirements && value == null) {
            return false;
        }

        if (this instanceof StringSchema) {
            return this.validate(value);
        } else if (this instanceof NumberSchema) {
            return this.validate(value);
        } else if (this instanceof MapSchema) {
            return this.validate(value);
        }
        return false;
    }

    protected void addCheck(String name, Boolean value) {
        checks.put(name, value);
    }
}
