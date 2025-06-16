package cn.bytengine.d.lang;

import cn.bytengine.d.collection.WeakConcurrentMap;

import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class PatternPool {
    private PatternPool() {
    }

    private static final WeakConcurrentMap<RegexWithFlag, Pattern> POOL = new WeakConcurrentMap<>();

    public static Pattern get(String regex) {
        return get(regex, 0);
    }

    public static Pattern get(String regex, int flags) {
        final RegexWithFlag regexWithFlag = new RegexWithFlag(regex, flags);
        return POOL.computeIfAbsent(regexWithFlag, (key) -> Pattern.compile(regex, flags));
    }

    public static Pattern remove(String regex, int flags) {
        return POOL.remove(new RegexWithFlag(regex, flags));
    }

    public static void clear() {
        POOL.clear();
    }

    private static class RegexWithFlag {
        private final String regex;
        private final int flag;

        public RegexWithFlag(String regex, int flag) {
            this.regex = regex;
            this.flag = flag;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + flag;
            result = prime * result + ((regex == null) ? 0 : regex.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            RegexWithFlag other = (RegexWithFlag) obj;
            if (flag != other.flag) {
                return false;
            }
            if (regex == null) {
                return other.regex == null;
            } else {
                return regex.equals(other.regex);
            }
        }
    }
}
