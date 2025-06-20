package cn.bytengine.d.secure;

import cn.bytengine.d.lang.CharTools;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 安全Base64，支持加盐
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class SecureBase64 {
    /**
     * 字符集
     */
    public static final char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };
    /**
     * URL支持字符集
     */
    public static final char[] toBase64URL = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };

    /**
     * 根据盐和标准字符集，映射新的字符集
     *
     * @param base64  字符集
     * @param offsets 盐
     * @return 新的字符集
     */
    private static char[] mapping(char[] base64, byte[] offsets) {
        if (offsets.length != base64.length) {
            throw new IllegalArgumentException("Lengths don't match");
        }
        char[] result = new char[base64.length];
        for (int idx = 0; idx < base64.length; idx++) {
            result[idx] = (char) (base64[idx] - offsets[idx]);
        }
        return result;
    }

    /**
     * 生成盐
     *
     * @param supportedUrl 是否支持URL安全
     * @return 盐
     */
    public static byte[] generateSalt(boolean supportedUrl) {
        char[] base64 = supportedUrl ? toBase64URL : toBase64;
        char[] newTable = CharTools.generateTable(base64, base64.length, false);
        byte[] salt = new byte[newTable.length];
        for (int idx = 0; idx < newTable.length; idx++) {
            salt[idx] = (byte) (base64[idx] - newTable[idx]);
        }
        return salt;
    }

    // region instance struct
    /**
     * 盐
     */
    private final byte[] salt;
    /**
     * 支持URL盐
     */
    private final byte[] urlSalt;
    /**
     * 加盐后映射字符集
     */
    private final int[] fromBase64;
    /**
     * 支持URL加盐后映射字符集
     */
    private final int[] fromBase64URL;

    /**
     * 指定盐，创建Base64编解码器
     *
     * @param salt 盐
     */
    public SecureBase64(final byte[] salt) {
        this.salt = salt;
        {
            char[] base64 = mapping(toBase64, salt);
            fromBase64 = new int[256];
            Arrays.fill(fromBase64, -1);
            for (int i = 0; i < base64.length; i++)
                fromBase64[base64[i]] = i;
            fromBase64['='] = -2;
        }

        this.urlSalt = new byte[salt.length];
        for (int i = 0; i < salt.length; i++) {
            if (salt[i] == '+') {
                urlSalt[i] = '-';
            } else if (salt[i] == '/') {
                urlSalt[i] = '_';
            } else {
                urlSalt[i] = salt[i];
            }
        }
        {
            char[] base64 = mapping(toBase64URL, urlSalt);
            fromBase64URL = new int[256];
            Arrays.fill(fromBase64URL, -1);
            for (int i = 0; i < toBase64URL.length; i++)
                fromBase64URL[base64[i]] = i;
            fromBase64URL['='] = -2;
        }
    }

    // endregion

    // region Base64 Encode

    /**
     * 编码字符串
     *
     * @param input 字符串
     * @return 编码结果
     */
    public final String encodeString(final String input) {
        return new String(encode(input.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 编码二进制内容
     *
     * @param input 二进制内容
     * @return 编码结果
     */
    public final byte[] encode(final byte[] input) {
        int len = getEncodeOutLength(input.length, -1, null, true);
        byte[] dst = new byte[len];
        int ret = encode0(input, 0, input.length, dst, toBase64, -1, null, true, salt);
        if (ret != dst.length)
            return Arrays.copyOf(dst, ret);
        return dst;
    }

    /**
     * URL安全方式编码二进制内容
     *
     * @param input 二进制内容
     * @return 编码结果
     */
    public final byte[] encodeUrlSafe(final byte[] input) {
        int len = getEncodeOutLength(input.length, -1, null, true);
        byte[] dst = new byte[len];
        int ret = encode0(input, 0, input.length, dst, toBase64URL, -1, null, true, urlSalt);
        if (ret != dst.length)
            return Arrays.copyOf(dst, ret);
        return dst;
    }

    /**
     * 计算编码结果字节长度
     *
     * @param srcLen    编码内容长度
     * @param lineMax   最大行数
     * @param newLine   新行内容
     * @param doPadding 是否填充
     * @return 编码结果字节长度
     */
    private int getEncodeOutLength(int srcLen, int lineMax, byte[] newLine, boolean doPadding) {
        int len = 0;
        if (doPadding) {
            len = 4 * ((srcLen + 2) / 3);
        } else {
            int n = srcLen % 3;
            len = 4 * (srcLen / 3) + (n == 0 ? 0 : n + 1);
        }
        if (lineMax > 0)                                  // line separators
            len += (len - 1) / lineMax * newLine.length;
        return len;
    }

    /**
     * Base64编码
     *
     * @param src       编码内容
     * @param off       偏移量
     * @param end       结束下标
     * @param dst       结果目标容器
     * @param base64    编码表
     * @param lineMax   最大行数
     * @param newLine   新行内容
     * @param doPadding 是否填充
     * @param salt      盐
     * @return 编码结果
     */
    private static int encode0(byte[] src, int off, int end, byte[] dst, char[] base64, int lineMax, byte[] newLine, boolean doPadding, final byte[] salt) {
        int sp = off;
        int slen = (end - off) / 3 * 3;
        int sl = off + slen;
        if (lineMax > 0 && slen > lineMax / 4 * 3)
            slen = lineMax / 4 * 3;
        int dp = 0;
        while (sp < sl) {
            int sl0 = Math.min(sp + slen, sl);
            for (int sp0 = sp, dp0 = dp; sp0 < sl0; ) {
                int bits = (src[sp0++] & 0xff) << 16 |
                        (src[sp0++] & 0xff) << 8 |
                        (src[sp0++] & 0xff);
                int mapIdx = (bits >>> 18) & 0x3f;
                dst[dp0++] = (byte) (base64[mapIdx] - salt[mapIdx]);
                mapIdx = (bits >>> 12) & 0x3f;
                dst[dp0++] = (byte) (base64[mapIdx] - salt[mapIdx]);
                mapIdx = (bits >>> 6) & 0x3f;
                dst[dp0++] = (byte) (base64[mapIdx] - salt[mapIdx]);
                mapIdx = bits & 0x3f;
                dst[dp0++] = (byte) (base64[mapIdx] - salt[mapIdx]);
            }
            int dlen = (sl0 - sp) / 3 * 4;
            dp += dlen;
            sp = sl0;
            if (dlen == lineMax && sp < end) {
                for (byte b : newLine) {
                    dst[dp++] = b;
                }
            }
        }
        if (sp < end) {               // 1 or 2 leftover bytes
            int b0 = src[sp++] & 0xff;
            int mapIdx = b0 >> 2;
            dst[dp++] = (byte) (base64[mapIdx] - salt[mapIdx]);
            if (sp == end) {
                mapIdx = (b0 << 4) & 0x3f;
                dst[dp++] = (byte) (base64[mapIdx] - salt[mapIdx]);
                if (doPadding) {
                    dst[dp++] = '=';
                    dst[dp++] = '=';
                }
            } else {
                int b1 = src[sp++] & 0xff;
                mapIdx = (b0 << 4) & 0x3f | (b1 >> 4);
                dst[dp++] = (byte) (base64[mapIdx] - salt[mapIdx]);
                mapIdx = (b1 << 2) & 0x3f;
                dst[dp++] = (byte) (base64[mapIdx] - salt[mapIdx]);
                if (doPadding) {
                    dst[dp++] = '=';
                }
            }
        }
        return dp;
    }

    // endregion

    // region Base64 Decode

    /**
     * 解码Base64字符串
     *
     * @param src Base64字符串
     * @return 解码结果
     */
    public final String decodeString(String src) {
        return new String(decode(src.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 解码Base64内容
     *
     * @param src Base64内容
     * @return 解码结果
     */
    public final byte[] decode(byte[] src) {
        byte[] dst = new byte[getDecodeOutLength(src, 0, src.length, fromBase64, true)];
        int ret = decode0(src, 0, src.length, dst, fromBase64, true);
        if (ret != dst.length) {
            dst = Arrays.copyOf(dst, ret);
        }
        return dst;
    }

    /**
     * URL安全方式解码Base64内容
     *
     * @param src Base64内容
     * @return 解码结果
     */
    public final byte[] decodeUrlSafe(byte[] src) {
        byte[] dst = new byte[getDecodeOutLength(src, 0, src.length, fromBase64URL, false)];
        int ret = decode0(src, 0, src.length, dst, fromBase64URL, false);
        if (ret != dst.length) {
            dst = Arrays.copyOf(dst, ret);
        }
        return dst;
    }

    /**
     * 计算解码结果字节长度
     *
     * @param src    Base64内容
     * @param sp     偏移量
     * @param sl     内容长度
     * @param base64 编码表
     * @param isMime 支持Mime
     * @return 结果长度
     */
    private static int getDecodeOutLength(byte[] src, int sp, int sl, int[] base64, boolean isMime) {
        int paddings = 0;
        int len = sl - sp;
        if (len == 0)
            return 0;
        if (len < 2) {
            if (isMime && base64[0] == -1)
                return 0;
            throw new IllegalArgumentException(
                    "Input byte[] should at least have 2 bytes for base64 bytes");
        }
        if (isMime) {
            int n = 0;
            while (sp < sl) {
                int b = src[sp++] & 0xff;
                if (b == '=') {
                    len -= (sl - sp + 1);
                    break;
                }
                if ((b = base64[b]) == -1)
                    n++;
            }
            len -= n;
        } else {
            if (src[sl - 1] == '=') {
                paddings++;
                if (src[sl - 2] == '=')
                    paddings++;
            }
        }
        if (paddings == 0 && (len & 0x3) != 0)
            paddings = 4 - (len & 0x3);
        return 3 * ((len + 3) / 4) - paddings;
    }

    /**
     * Base64解码
     *
     * @param src    Base64内容
     * @param sp     偏移量
     * @param sl     内容长度
     * @param dst    结果容器
     * @param base64 编码表
     * @param isMime 支持Mime
     * @return 处理内容长度
     */
    private static int decode0(byte[] src, int sp, int sl, byte[] dst, int[] base64, boolean isMime) {
        int dp = 0;
        int bits = 0;
        int shiftto = 18;       // pos of first byte of 4-byte atom
        while (sp < sl) {
            int b = src[sp++] & 0xff;
            if ((b = base64[b]) < 0) {
                if (b == -2) {
                    if (shiftto == 6 && (sp == sl || src[sp++] != '=') ||
                            shiftto == 18) {
                        throw new IllegalArgumentException(
                                "Input byte array has wrong 4-byte ending unit");
                    }
                    break;
                }
                if (isMime)    // skip if for rfc2045
                    continue;
                else
                    throw new IllegalArgumentException(
                            "Illegal base64 character " +
                                    Integer.toString(src[sp - 1], 16));
            }
            bits |= (b << shiftto);
            shiftto -= 6;
            if (shiftto < 0) {
                dst[dp++] = (byte) (bits >> 16);
                dst[dp++] = (byte) (bits >> 8);
                dst[dp++] = (byte) (bits);
                shiftto = 18;
                bits = 0;
            }
        }
        // reached end of byte array or hit padding '=' characters.
        if (shiftto == 6) {
            dst[dp++] = (byte) (bits >> 16);
        } else if (shiftto == 0) {
            dst[dp++] = (byte) (bits >> 16);
            dst[dp++] = (byte) (bits >> 8);
        } else if (shiftto == 12) {
            // dangling single "x", incorrectly encoded.
            throw new IllegalArgumentException(
                    "Last unit does not have enough valid bits");
        }
        // anything left is invalid, if is not MIME.
        // if MIME, ignore all non-base64 character
        while (sp < sl) {
            if (isMime && base64[src[sp++]] < 0)
                continue;
            throw new IllegalArgumentException(
                    "Input byte array has incorrect ending byte at " + sp);
        }
        return dp;
    }
    // endregion
}
