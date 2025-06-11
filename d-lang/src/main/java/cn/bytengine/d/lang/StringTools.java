package cn.bytengine.d.lang;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class StringTools {
    private StringTools() {
    }

    public static String utf8Str(Object obj) {
        return str(obj, CharsetTools.CHARSET_UTF_8);
    }

    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        } else if (ArrayTools.isArray(obj)) {
            return ArrayTools.toString(obj);
        }

        return obj.toString();
    }

    public static String str(byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        if (null == charset) {
            return new String(data);
        }
        return new String(data, charset);
    }

    public static String str(byte[] bytes, String charset) {
        return str(bytes, CharsetTools.charset(charset));
    }

    public static String str(Byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length];
        Byte dataByte;
        for (int i = 0; i < data.length; i++) {
            dataByte = data[i];
            bytes[i] = (null == dataByte) ? -1 : dataByte;
        }

        return str(bytes, charset);
    }

    public static String str(Byte[] bytes, String charset) {
        return str(bytes, CharsetTools.charset(charset));
    }

    public static String str(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    public static String str(ByteBuffer data, String charset) {
        if (data == null) {
            return null;
        }

        return str(data, CharsetTools.charset(charset));
    }
}
