package cn.bytengine.d.unique;

import cn.bytengine.d.lang.ArrayTools;
import cn.bytengine.d.lang.CharTools;

/**
 * 按照进制算法进行编解码
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ScaleCodeConvertor implements CodeConvertor {
    /**
     * 编码表
     */
    private char[] table;
    /**
     * 编码表
     */
    private String tableStr;
    /**
     * 进制
     */
    private int scale;

    /**
     * 指定编码表，创建进制转换器；
     *
     * @param tableString 编码表
     */
    public ScaleCodeConvertor(String tableString) {
        tableStr = tableString;
        table = tableString.toCharArray();
        scale = table.length;
    }

    @Override
    public String encode(long number) {
        StringBuilder builder = new StringBuilder();
        int remainder;
        int condition = scale - 1;
        while (number > condition) {
            remainder = Long.valueOf(number % scale).intValue();
            builder.append(table[remainder]);
            number = number / scale;
        }
        builder.append(table[Long.valueOf(number).intValue()]);
        return builder.reverse().toString();
    }

    @Override
    public long decode(String code) {
        long value = 0;
        char tempChar;
        char[] codeChars = code.toCharArray();
        int tempCharValue, codeLen = codeChars.length;
        for (int i = 0; i < codeLen; i++) {
            tempChar = codeChars[i];
            tempCharValue = tableStr.indexOf(tempChar);
            value += (long) (tempCharValue * Math.pow(scale, codeLen - i - 1));
        }
        return value;
    }

    /**
     * 根据指定字符集合，创建新的编码表
     *
     * @param tables 字符集合
     * @return 编码表
     * @see cn.bytengine.d.lang.CharTools#NUMBER_CHARS
     * @see cn.bytengine.d.lang.CharTools#LOWERCASE_ALPHA_CHARS
     * @see cn.bytengine.d.lang.CharTools#UPPERCASE_ALPHA_CHARS
     */
    public static String generateCodeTable(char[]... tables) {
        char[] table = ArrayTools.addAll(tables);
        table = CharTools.generateTable(table, table.length, false);
        return new String(table);
    }
}
