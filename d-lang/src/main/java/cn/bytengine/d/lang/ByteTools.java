package cn.bytengine.d.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ByteTools {
    private ByteTools() {
    }

    public static final ByteOrder DEFAULT_ORDER = ByteOrder.LITTLE_ENDIAN;
    public static final ByteOrder CPU_ENDIAN = "little".equals(System.getProperty("sun.cpu.endian")) ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;

    public static byte intToByte(int intValue) {
        return (byte) intValue;
    }

    public static int byteToUnsignedInt(byte byteValue) {
        // Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return byteValue & 0xFF;
    }

    public static short bytesToShort(byte[] bytes) {
        return bytesToShort(bytes, DEFAULT_ORDER);
    }

    public static short bytesToShort(final byte[] bytes, final ByteOrder byteOrder) {
        return bytesToShort(bytes, 0, byteOrder);
    }

    public static short bytesToShort(final byte[] bytes, final int start, final ByteOrder byteOrder) {
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            //小端模式，数据的高字节保存在内存的高地址中，而数据的低字节保存在内存的低地址中
            return (short) (bytes[start] & 0xff | (bytes[start + 1] & 0xff) << Byte.SIZE);
        } else {
            return (short) (bytes[start + 1] & 0xff | (bytes[start] & 0xff) << Byte.SIZE);
        }
    }

    public static byte[] shortToBytes(short shortValue) {
        return shortToBytes(shortValue, DEFAULT_ORDER);
    }

    public static byte[] shortToBytes(short shortValue, ByteOrder byteOrder) {
        byte[] b = new byte[Short.BYTES];
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            b[0] = (byte) (shortValue & 0xff);
            b[1] = (byte) ((shortValue >> Byte.SIZE) & 0xff);
        } else {
            b[1] = (byte) (shortValue & 0xff);
            b[0] = (byte) ((shortValue >> Byte.SIZE) & 0xff);
        }
        return b;
    }

    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, DEFAULT_ORDER);
    }

    public static int bytesToInt(byte[] bytes, ByteOrder byteOrder) {
        return bytesToInt(bytes, 0, byteOrder);
    }

    public static int bytesToInt(byte[] bytes, int start, ByteOrder byteOrder) {
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            return bytes[start] & 0xFF | //
                    (bytes[1 + start] & 0xFF) << 8 | //
                    (bytes[2 + start] & 0xFF) << 16 | //
                    (bytes[3 + start] & 0xFF) << 24; //
        } else {
            return bytes[3 + start] & 0xFF | //
                    (bytes[2 + start] & 0xFF) << 8 | //
                    (bytes[1 + start] & 0xFF) << 16 | //
                    (bytes[start] & 0xFF) << 24; //
        }

    }

    public static byte[] intToBytes(int intValue) {
        return intToBytes(intValue, DEFAULT_ORDER);
    }

    public static byte[] intToBytes(int intValue, ByteOrder byteOrder) {

        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            return new byte[]{ //
                    (byte) (intValue & 0xFF), //
                    (byte) ((intValue >> 8) & 0xFF), //
                    (byte) ((intValue >> 16) & 0xFF), //
                    (byte) ((intValue >> 24) & 0xFF) //
            };

        } else {
            return new byte[]{ //
                    (byte) ((intValue >> 24) & 0xFF), //
                    (byte) ((intValue >> 16) & 0xFF), //
                    (byte) ((intValue >> 8) & 0xFF), //
                    (byte) (intValue & 0xFF) //
            };
        }

    }

    public static byte[] longToBytes(long longValue) {
        return longToBytes(longValue, DEFAULT_ORDER);
    }

    public static byte[] longToBytes(long longValue, ByteOrder byteOrder) {
        byte[] result = new byte[Long.BYTES];
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) (longValue & 0xFF);
                longValue >>= Byte.SIZE;
            }
        } else {
            for (int i = (result.length - 1); i >= 0; i--) {
                result[i] = (byte) (longValue & 0xFF);
                longValue >>= Byte.SIZE;
            }
        }
        return result;
    }

    public static long bytesToLong(byte[] bytes) {
        return bytesToLong(bytes, DEFAULT_ORDER);
    }

    public static long bytesToLong(byte[] bytes, ByteOrder byteOrder) {
        return bytesToLong(bytes, 0, byteOrder);
    }

    public static long bytesToLong(byte[] bytes, int start, ByteOrder byteOrder) {
        long values = 0;
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            for (int i = (Long.BYTES - 1); i >= 0; i--) {
                values <<= Byte.SIZE;
                values |= (bytes[i + start] & 0xff);
            }
        } else {
            for (int i = 0; i < Long.BYTES; i++) {
                values <<= Byte.SIZE;
                values |= (bytes[i + start] & 0xff);
            }
        }

        return values;
    }

    public static byte[] floatToBytes(float floatValue) {
        return floatToBytes(floatValue, DEFAULT_ORDER);
    }

    public static byte[] floatToBytes(float floatValue, ByteOrder byteOrder) {
        return intToBytes(Float.floatToIntBits(floatValue), byteOrder);
    }

    public static float bytesToFloat(byte[] bytes) {
        return bytesToFloat(bytes, DEFAULT_ORDER);
    }

    public static float bytesToFloat(byte[] bytes, ByteOrder byteOrder) {
        return Float.intBitsToFloat(bytesToInt(bytes, byteOrder));
    }

    public static byte[] doubleToBytes(double doubleValue) {
        return doubleToBytes(doubleValue, DEFAULT_ORDER);
    }

    public static byte[] doubleToBytes(double doubleValue, ByteOrder byteOrder) {
        return longToBytes(Double.doubleToLongBits(doubleValue), byteOrder);
    }

    public static double bytesToDouble(byte[] bytes) {
        return bytesToDouble(bytes, DEFAULT_ORDER);
    }

    public static double bytesToDouble(byte[] bytes, ByteOrder byteOrder) {
        return Double.longBitsToDouble(bytesToLong(bytes, byteOrder));
    }

    public static byte[] numberToBytes(Number number) {
        return numberToBytes(number, DEFAULT_ORDER);
    }

    public static byte[] numberToBytes(Number number, ByteOrder byteOrder) {
        if (number instanceof Byte) {
            return new byte[]{number.byteValue()};
        } else if (number instanceof Double) {
            return doubleToBytes((Double) number, byteOrder);
        } else if (number instanceof Long) {
            return longToBytes((Long) number, byteOrder);
        } else if (number instanceof Integer) {
            return intToBytes((Integer) number, byteOrder);
        } else if (number instanceof Short) {
            return shortToBytes((Short) number, byteOrder);
        } else if (number instanceof Float) {
            return floatToBytes((Float) number, byteOrder);
        } else {
            return doubleToBytes(number.doubleValue(), byteOrder);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T bytesToNumber(byte[] bytes, Class<T> targetClass, ByteOrder byteOrder) throws IllegalArgumentException {
        Number number;
        if (Byte.class == targetClass) {
            number = bytes[0];
        } else if (Short.class == targetClass) {
            number = bytesToShort(bytes, byteOrder);
        } else if (Integer.class == targetClass) {
            number = bytesToInt(bytes, byteOrder);
        } else if (AtomicInteger.class == targetClass) {
            number = new AtomicInteger(bytesToInt(bytes, byteOrder));
        } else if (Long.class == targetClass) {
            number = bytesToLong(bytes, byteOrder);
        } else if (AtomicLong.class == targetClass) {
            number = new AtomicLong(bytesToLong(bytes, byteOrder));
        } else if (LongAdder.class == targetClass) {
            final LongAdder longValue = new LongAdder();
            longValue.add(bytesToLong(bytes, byteOrder));
            number = longValue;
        } else if (Float.class == targetClass) {
            number = bytesToFloat(bytes, byteOrder);
        } else if (Double.class == targetClass) {
            number = bytesToDouble(bytes, byteOrder);
        } else if (DoubleAdder.class == targetClass) {
            final DoubleAdder doubleAdder = new DoubleAdder();
            doubleAdder.add(bytesToDouble(bytes, byteOrder));
            number = doubleAdder;
        } else if (BigDecimal.class == targetClass) {
            number = NumberTools.toBigDecimal(bytesToDouble(bytes, byteOrder));
        } else if (BigInteger.class == targetClass) {
            number = BigInteger.valueOf(bytesToLong(bytes, byteOrder));
        } else if (Number.class == targetClass) {
            // 用户没有明确类型具体类型，默认Double
            number = bytesToDouble(bytes, byteOrder);
        } else {
            // 用户自定义类型不支持
            throw new IllegalArgumentException("Unsupported Number type: " + targetClass.getName());
        }

        return (T) number;
    }
}
