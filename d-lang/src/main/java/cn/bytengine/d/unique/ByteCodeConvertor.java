package cn.bytengine.d.unique;

import cn.bytengine.d.lang.ArrayTools;

/**
 * 根据指定Char表格，将数字进行编解码转换
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ByteCodeConvertor implements CodeConvertor {
    private final char[] table;

    /**
     * 指定编码表格
     *
     * @param table TODO
     */
    public ByteCodeConvertor(String table) {
        this.table = table.toCharArray();
    }

    @Override
    public String encode(long val) {
        int len = 4, left = 0, right = 64 - len;
        char[] buf = new char[16];
        while (right >= left) {
            long temp = val;
            if (left > 0) {
                temp = temp << left;
            }
            if (right > 0) {
                temp = temp >>> right;
            }
            buf[left / 4] = table[(short) temp];
            left += len;
        }
        return new String(buf);
    }

    @Override
    public long decode(String code) {
        char[] buf = code.toCharArray();
        long result = 0;
        for (int idx = 0; idx < buf.length; idx++) {
            char c = buf[idx];
            long tableIdx = ArrayTools.indexOf(table, buf[idx]);
            if (tableIdx == -1) {
                throw new IllegalArgumentException("The code contain unable character '" + c + "'.");
            }
            result = result + (tableIdx << ((15 - idx) * 4));
        }
        return result;
    }
}
