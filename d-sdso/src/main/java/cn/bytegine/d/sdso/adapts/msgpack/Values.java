package cn.bytegine.d.sdso.adapts.msgpack;

import org.msgpack.core.MessageNeverUsedFormatException;
import org.msgpack.value.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author sunkaihan
 * @version 1.0
 */
public abstract class Values {
    private Values() {
    }

    public static Value toValue(Object data) {
        if (data == null) {
            return ValueFactory.newNil();
        }
        if (data instanceof Boolean) {
            return ValueFactory.newBoolean((Boolean) data);
        }
        if (data instanceof Byte) {
            return ValueFactory.newInteger((Byte) data);
        }
        if (data instanceof Short) {
            return ValueFactory.newInteger((Short) data);
        }
        if (data instanceof Integer) {
            return ValueFactory.newInteger((Integer) data);
        }
        if (data instanceof Long) {
            return ValueFactory.newInteger((Long) data);
        }
        if (data instanceof Float) {
            return ValueFactory.newFloat((Float) data);
        }
        if (data instanceof Double) {
            return ValueFactory.newFloat((Double) data);
        }
        if (data instanceof String) {
            return ValueFactory.newString((String) data);
        }
        if (data instanceof byte[]) {
            return ValueFactory.newBinary((byte[]) data);
        }
        if (data.getClass().isArray()) {
            int len = Array.getLength(data);
            Value[] values = new Value[len];
            for (int idx = 0; idx < len; idx++) {
                values[idx] = toValue(Array.get(data, idx));
            }
            return ValueFactory.newArray(values);
        }
        if (data instanceof Collection<?>) {
            Collection<?> coll = (Collection<?>) data;
            int len = coll.size();
            Value[] values = new Value[len];
            int idx = 0;
            for (Object o : coll) {
                values[idx++] = toValue(o);
            }
            return ValueFactory.newArray(values);
        }
        if (data instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) data;
            ValueFactory.MapBuilder builder = ValueFactory.newMapBuilder();
            map.forEach((k, v) -> builder.put(toValue(k), toValue(v)));
            return builder.build();
        }
        throw new IllegalArgumentException("data is not supported type.");
    }

    public static Object toData(Value value) {
        switch (value.getValueType()) {
            case NIL:
                return null;
            case BOOLEAN:
                return value.asBooleanValue().getBoolean();
            case INTEGER:
                IntegerValue integerValue = value.asIntegerValue();
                if (integerValue.isInByteRange()) {
                    return integerValue.toInt();
                }
                if (integerValue.isInShortRange()) {
                    return integerValue.toInt();
                }
                if (integerValue.isInIntRange()) {
                    return integerValue.toInt();
                }
                if (integerValue.isInLongRange()) {
                    return integerValue.toLong();
                }
                return integerValue.asBigInteger();
            case FLOAT:
                FloatValue floatValue = value.asFloatValue();
                return floatValue.toDouble();
            case STRING:
                return value.asStringValue().asString();
            case BINARY:
                return value.asBinaryValue().asByteArray();
            case ARRAY: {
                ArrayValue arrayValue = value.asArrayValue();
                List<Object> data = new ArrayList<>(arrayValue.size());
                for (Value item : arrayValue) {
                    data.add(toData(item));
                }
                return data;
            }
            case MAP: {
                MapValue mapValue = value.asMapValue();
                Map<Object, Object> data = new HashMap<>(mapValue.size());
                for (Map.Entry<Value, Value> entry : mapValue.entrySet()) {
                    data.put(toData(entry.getKey()), toData(entry.getValue()));
                }
                return data;
            }
            default:
                throw new MessageNeverUsedFormatException("Unknown value type");
        }
    }

    public static <T> T toData(Value value, Class<T> clazz) {
        return clazz.cast(toData(value));
    }
}
