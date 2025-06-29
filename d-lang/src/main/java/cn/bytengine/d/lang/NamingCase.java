package cn.bytengine.d.lang;

/**
 * 命名规则封装，主要是针对驼峰风格命名、连接符命名等的封装
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class NamingCase {
    /**
     * 将驼峰式命名的字符串转换为下划线方式，又称SnakeCase、underScoreCase。<br>
     * 如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * 规则为：
     * <ul>
     *     <li>单字之间以下划线隔开</li>
     *     <li>每个单字的首字母亦用小写字母</li>
     * </ul>
     * 例如：
     *
     * <pre>
     * HelloWorld=》hello_world
     * Hello_World=》hello_world
     * HelloWorld_test=》hello_world_test
     * </pre>
     *
     * @param str 转换前的驼峰式命名的字符串，也可以为下划线形式
     * @return 转换后下划线方式命名的字符串
     */
    public static String toUnderlineCase(CharSequence str) {
        return toSymbolCase(str, CharTools.UNDERLINE);
    }

    /**
     * 将驼峰式命名的字符串转换为短横连接方式。<br>
     * 如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * 规则为：
     * <ul>
     *     <li>单字之间横线线隔开</li>
     *     <li>每个单字的首字母亦用小写字母</li>
     * </ul>
     * 例如：
     *
     * <pre>
     * HelloWorld=》hello-world
     * Hello_World=》hello-world
     * HelloWorld_test=》hello-world-test
     * </pre>
     *
     * @param str 转换前的驼峰式命名的字符串，也可以为下划线形式
     * @return 转换后下划线方式命名的字符串
     */
    public static String toKebabCase(CharSequence str) {
        return toSymbolCase(str, CharTools.DASHED);
    }

    /**
     * 将驼峰式命名的字符串转换为使用符号连接方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。
     *
     * @param str    转换前的驼峰式命名的字符串，也可以为符号连接形式
     * @param symbol 连接符
     * @return 转换后符号连接方式命名的字符串
     */
    public static String toSymbolCase(CharSequence str, char symbol) {
        if (str == null) {
            return null;
        }

        final int length = str.length();
        final StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < length; i++) {
            c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                final Character preChar = (i > 0) ? str.charAt(i - 1) : null;
                final Character nextChar = (i < str.length() - 1) ? str.charAt(i + 1) : null;

                if (null != preChar) {
                    if (symbol == preChar) {
                        // 前一个为分隔符
                        if (null == nextChar || Character.isLowerCase(nextChar)) {
                            //普通首字母大写，如_Abb -> _abb
                            c = Character.toLowerCase(c);
                        }
                        //后一个为大写，按照专有名词对待，如_AB -> _AB
                    } else if (Character.isLowerCase(preChar)) {
                        // 前一个为小写
                        sb.append(symbol);
                        if (null == nextChar || Character.isLowerCase(nextChar) || CharTools.isNumber(nextChar)) {
                            //普通首字母大写，如aBcc -> a_bcc
                            c = Character.toLowerCase(c);
                        }
                        // 后一个为大写，按照专有名词对待，如aBC -> a_BC
                    } else {
                        //前一个为大写
                        if (null != nextChar && Character.isLowerCase(nextChar)) {
                            // 普通首字母大写，如ABcc -> A_bcc
                            sb.append(symbol);
                            c = Character.toLowerCase(c);
                        }
                        // 后一个为大写，按照专有名词对待，如ABC -> ABC
                    }
                } else {
                    // 首字母，需要根据后一个判断是否转为小写
                    if (null == nextChar || Character.isLowerCase(nextChar)) {
                        // 普通首字母大写，如Abc -> abc
                        c = Character.toLowerCase(c);
                    }
                    // 后一个为大写，按照专有名词对待，如ABC -> ABC
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
