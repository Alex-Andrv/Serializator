import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class Serializer {

    private static final String NEW_LINE = "\n";

    private static String getTabs(int cntTab) {
        return "\t".repeat(cntTab);
    }

    public static String toJson(Object ob) throws IllegalAccessException {
        return toJson(ob, 0);
    }

    private static String toJson(Object ob, int cntTab) throws IllegalAccessException {

        Class<?> cl = ob.getClass();

        if (cl.isPrimitive()
                || String.class.isAssignableFrom(cl)
                || Integer.class.isAssignableFrom(cl)
                || Boolean.class.isAssignableFrom(cl)
                || Long.class.isAssignableFrom(cl)) {
            return "\"" + ob + "\"";
        } else if (Set.class.isAssignableFrom(cl)) {
            return serializeSet((Set<?>) ob, cntTab);
        } else if (Map.class.isAssignableFrom(cl)) {
            return serializeMap((Map<?, ?>) ob, cntTab);
        } else if (SomeClass.class.isAssignableFrom(cl) || RemoteFile.class.isAssignableFrom(cl)) {
            return serializeClass(ob, cntTab);
        } else {
            throw new UnsupportedOperationException(
                    String.format("Type %s is unsupported", cl.getSimpleName()));
        }
    }

    private static String serializeClass(Object ob, int cntTab) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        sb.append("{").append(NEW_LINE);

        StringJoiner fieldJoiner = new StringJoiner("," + NEW_LINE);

        Class<?> cl = ob.getClass();

        for (Field field : cl.getDeclaredFields()) {
            field.setAccessible(true);

            Object value = null;

            try {
                value = field.get(ob);
            } catch (IllegalAccessException e) {
                System.err.printf("Can't asses to the field %s%n", field.getName());
                throw e;
            }

            fieldJoiner
                    .add(getTabs(cntTab + 1) +
                                    String.format("\"%s\": %s",
                                            field.getName(),
                                            toJson(value, cntTab + 1)));
        }

        sb.append(fieldJoiner);

        sb.append(NEW_LINE).append(getTabs(cntTab)).append("}");

        return sb.toString();
    }

    private static String serializeMap(Map<?, ?> map, int cntTab) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        StringJoiner mapJoiner = new StringJoiner(", ");
        for (var entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            // a bit scary, maybe need refactoring
            mapJoiner.add(
                    "{" + NEW_LINE +
                            getTabs(cntTab + 1) + "\"" + key + "\"" + ":" + " " + toJson(value, cntTab + 1) + NEW_LINE +
                            getTabs(cntTab) + "}");
        }

        sb.append(mapJoiner);

        sb.append("]");
        return sb.toString();
    }

    private static String serializeSet(Set<?> set, int cntTab) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        StringJoiner mapJoiner = new StringJoiner(", ");
        for (var elem : set) {
            mapJoiner.add(toJson(elem, cntTab));
        }

        sb.append(mapJoiner);

        sb.append("]");
        return sb.toString();
    }
}
