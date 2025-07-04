package cn.bytengine.d.lang;

/**
 * 字符查找器<br>
 * 查找指定字符在字符串中的位置信息
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CharFinder extends TextFinder {
    /**
     * TODO
     */
    private final char c;
    /**
     * TODO
     */
    private final boolean caseInsensitive;

    /**
     * 构造，不忽略字符大小写
     *
     * @param c 被查找的字符
     */
    public CharFinder(char c) {
        this(c, false);
    }

    /**
     * 构造
     *
     * @param c               被查找的字符
     * @param caseInsensitive 是否忽略大小写
     */
    public CharFinder(char c, boolean caseInsensitive) {
        this.c = c;
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public int start(int from) {
        AssertTools.notNull(this.text, "Text to find must be not null!");
        final int limit = getValidEndIndex();
        if (negative) {
            for (int i = from; i > limit; i--) {
                if (NumberTools.equals(c, text.charAt(i), caseInsensitive)) {
                    return i;
                }
            }
        } else {
            for (int i = from; i < limit; i++) {
                if (NumberTools.equals(c, text.charAt(i), caseInsensitive)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int end(int start) {
        if (start < 0) {
            return -1;
        }
        return start + 1;
    }
}
