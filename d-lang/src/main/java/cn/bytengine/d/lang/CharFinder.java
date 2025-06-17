package cn.bytengine.d.lang;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CharFinder extends TextFinder {
    private final char c;
    private final boolean caseInsensitive;

    public CharFinder(char c) {
        this(c, false);
    }

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
