package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema {
    private static Integer mapSize;

    public MapSchema required() {
        hasRequirements = true;
        addCheck("required", value -> value instanceof Map && !((Map<?, ?>) value).containsValue(null));
        return this;
    }

    public MapSchema sizeof(Integer size) {
        mapSize = size;
        addCheck("sizeof", value -> value instanceof Map && ((Map<?, ?>) value).size() == mapSize);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        this.checks.remove("required");

        addCheck("shape", value -> value instanceof Map
                && schemas.entrySet().stream()
                .allMatch(v -> v.getValue().isValid(((Map<?, ?>) value).get(v.getKey()))));
        return this;
    }
}
