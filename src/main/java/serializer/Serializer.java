package serializer;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class Serializer {

    private static final String NEW_LINE = "\n";

    public static String getTabs(int cntTab) {
        return "\t".repeat(cntTab);
    }

    public static String toJson(@Nonnull Class<?> clazz) throws IllegalAccessException {
        return toJson(clazz, 0);
    }

    public static String toJson(@Nonnull Class<?> clazz, int cntTab) throws IllegalAccessException {
        if (Serializable.class.isAssignableFrom(clazz)) {
            return serializeSerializableClass(clazz, cntTab);
        } else {
            return "{}";
        }
    }

    public static String toJson(@Nonnull Type type, int cntTab) throws IllegalAccessException {
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (Serializable.class.isAssignableFrom(clazz)) {
                return serializeSerializableClass(clazz, cntTab);
            } else {
                return "\"" + clazz.getSimpleName() + "\"";
            }
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();

            if (!(rawType instanceof Class)) {
                throw new RuntimeException("Unsupported type: " + parameterizedType.getTypeName());
            }

            Class<?> rawTypeClass = (Class<?>) rawType;

            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            if (Set.class.isAssignableFrom(rawTypeClass)) {
                return serializeSet(actualTypeArguments, cntTab);
            } else if (Map.class.isAssignableFrom(rawTypeClass)) {
                return serializeMap(actualTypeArguments, cntTab);
            } else {
                throw new UnsupportedOperationException("Unsupported parameterized type: " + type.getTypeName());
            }
        } else {
            throw new UnsupportedOperationException("Unsupported type: " + type.getTypeName());
        }
    }

    private static String serializeSerializableClass(@Nonnull Class<?> clazz, int cntTab) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        sb.append("{").append(NEW_LINE);

        StringJoiner fieldJoiner = new StringJoiner("," + NEW_LINE);

        for (Field field : clazz.getDeclaredFields()) {
            fieldJoiner
                    .add(getTabs(cntTab + 1) +
                            String.format("\"%s\": %s",
                                    field.getName(),
                                    toJson(field.getGenericType(), cntTab + 1)));
        }

        sb.append(fieldJoiner);

        sb.append(NEW_LINE).append(getTabs(cntTab)).append("}");

        return sb.toString();
    }

    private static String serializeMap(@Nonnull Type[] actualTypeArguments, int cntTab) throws IllegalAccessException {
        if (actualTypeArguments.length != 2) {
            throw new RuntimeException("Unsupported format exception");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        sb.append("{").append(NEW_LINE);

        sb.append(getTabs(cntTab + 1))
                .append(toJson(actualTypeArguments[0], cntTab + 1))
                .append(":")
                .append(" ")
                .append(toJson(actualTypeArguments[1], cntTab + 1))
                .append(NEW_LINE)
                .append(getTabs(cntTab))
                .append("}");


        sb.append("]");
        return sb.toString();
    }

    private static String serializeSet(@Nonnull Type[] actualTypeArguments, int cntTab) throws IllegalAccessException {
        if (actualTypeArguments.length != 1) {
            throw new RuntimeException("Unsupported format exception");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("[");

        sb.append(toJson(actualTypeArguments[0], cntTab));

        sb.append("]");
        return sb.toString();
    }
}
