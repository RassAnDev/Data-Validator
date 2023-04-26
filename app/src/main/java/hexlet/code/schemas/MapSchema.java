package hexlet.code.schemas;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapSchema extends BaseSchema {
    private static Integer mapSize;
    private static Map<String, BaseSchema> formedSchemas;

    public MapSchema required() {
        addCheck("required", true);
        hasRequirements = true;
        return this;
    }

    public MapSchema sizeof(Integer size) {
        addCheck("sizeof", true);
        mapSize = size;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        addCheck("shape", true);
        formedSchemas = schemas;
        return this;
    }

    public boolean validate(Object value) {
        boolean isMap = value instanceof Map;
        List<Boolean> passedChecks = new LinkedList<>();

        for (Map.Entry<String, Boolean> check : checks.entrySet()) {
            if (Objects.equals(check.getKey(), "required")) {
                passedChecks.add(isMap && !((Map<?, ?>) value).containsValue(null));
            } else if (Objects.equals(check.getKey(), "sizeof")) {
                passedChecks.add(isMap && ((Map<?, ?>) value).size() == mapSize);
            } else if (Objects.equals(check.getKey(), "shape")) {
                passedChecks.add(isMap && validateShapeSchemas((Map<?, ?>) value));
            }
        }
        return passedChecks.stream().allMatch(v -> v.equals(true));
    }

    public boolean validateShapeSchemas(Map<?, ?> value) {
        return formedSchemas.entrySet().stream()
                .allMatch(v -> v.getValue().isValid(value.get(v.getKey())));
    }
}
