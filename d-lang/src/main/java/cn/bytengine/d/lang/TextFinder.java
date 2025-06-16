package cn.bytengine.d.lang;

import java.io.Serializable;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class TextFinder implements Finder, Serializable {
    protected CharSequence text;
    protected int endIndex = -1;
    protected boolean negative;

    public TextFinder setText(CharSequence text) {
        this.text = AssertTools.notNull(text, "Text must be not null!");
        return this;
    }

    public TextFinder setEndIndex(int endIndex) {
        this.endIndex = endIndex;
        return this;
    }

    public TextFinder setNegative(boolean negative) {
        this.negative = negative;
        return this;
    }

    protected int getValidEndIndex() {
        if (negative && -1 == endIndex) {
            // 反向查找模式下，-1表示0前面的位置，即字符串反向末尾的位置
            return -1;
        }
        final int limit;
        if (endIndex < 0) {
            limit = endIndex + text.length() + 1;
        } else {
            limit = Math.min(endIndex, text.length());
        }
        return limit;
    }
}
