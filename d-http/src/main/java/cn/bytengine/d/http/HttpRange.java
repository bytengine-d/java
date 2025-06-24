package cn.bytengine.d.http;

import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.CollectionTools;
import cn.bytengine.d.lang.ObjectTools;
import cn.bytengine.d.utils.InputStreamResource;
import cn.bytengine.d.utils.Resource;
import cn.bytengine.d.utils.ResourceRegion;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Represents an HTTP (byte) range for use with the HTTP {@code "Range"} header.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class HttpRange {

    /**
     * Maximum ranges per request.
     */
    private static final int MAX_RANGES = 100;

    private static final String BYTE_RANGE_PREFIX = "bytes=";


    /**
     * Turn a {@code Resource} into a {@link ResourceRegion} using the range
     * information contained in the current {@code HttpRange}.
     *
     * @param resource the {@code Resource} to select the region from
     * @return the selected region of the given {@code Resource}
     */
    public ResourceRegion toResourceRegion(Resource resource) {
        // Don't try to determine contentLength on InputStreamResource - cannot be read afterwards...
        // Note: custom InputStreamResource subclasses could provide a pre-calculated content length!
        AssertTools.isTrue(resource.getClass() != InputStreamResource.class,
                "Cannot convert an InputStreamResource to a ResourceRegion");
        long contentLength = getLengthFor(resource);
        long start = getRangeStart(contentLength);
        long end = getRangeEnd(contentLength);
        AssertTools.isTrue(start < contentLength, "'position' exceeds the resource length " + contentLength);
        return new ResourceRegion(resource, start, end - start + 1);
    }

    /**
     * Return the start of the range given the total length of a representation.
     *
     * @param length the length of the representation
     * @return the start of this range for the representation
     */
    public abstract long getRangeStart(long length);

    /**
     * Return the end of the range (inclusive) given the total length of a representation.
     *
     * @param length the length of the representation
     * @return the end of the range for the representation
     */
    public abstract long getRangeEnd(long length);


    /**
     * Create an {@code HttpRange} from the given position to the end.
     *
     * @param firstBytePos the first byte position
     * @return a byte range that ranges from {@code firstPos} till the end
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-2.1">Byte Ranges</a>
     */
    public static HttpRange createByteRange(long firstBytePos) {
        return new ByteRange(firstBytePos, null);
    }

    /**
     * Create a {@code HttpRange} from the given fist to last position.
     *
     * @param firstBytePos the first byte position
     * @param lastBytePos  the last byte position
     * @return a byte range that ranges from {@code firstPos} till {@code lastPos}
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-2.1">Byte Ranges</a>
     */
    public static HttpRange createByteRange(long firstBytePos, long lastBytePos) {
        return new ByteRange(firstBytePos, lastBytePos);
    }

    /**
     * Create an {@code HttpRange} that ranges over the last given number of bytes.
     *
     * @param suffixLength the number of bytes for the range
     * @return a byte range that ranges over the last {@code suffixLength} number of bytes
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-2.1">Byte Ranges</a>
     */
    public static HttpRange createSuffixRange(long suffixLength) {
        return new SuffixByteRange(suffixLength);
    }

    /**
     * Parse the given, comma-separated string into a list of {@code HttpRange} objects.
     * <p>This method can be used to parse an {@code Range} header.
     *
     * @param ranges the string to parse
     * @return the list of ranges
     * @throws IllegalArgumentException if the string cannot be parsed
     *                                  or if the number of ranges is greater than 100
     */
    public static List<HttpRange> parseRanges(String ranges) {
        if (CharSequenceTools.isEmpty(ranges)) {
            return Collections.emptyList();
        }
        if (!ranges.startsWith(BYTE_RANGE_PREFIX)) {
            throw new IllegalArgumentException("Range '" + ranges + "' does not start with 'bytes='");
        }
        ranges = ranges.substring(BYTE_RANGE_PREFIX.length());

        String[] tokens = CharSequenceTools.tokenizeToStringArray(ranges, ",");
        if (tokens.length > MAX_RANGES) {
            throw new IllegalArgumentException("Too many ranges: " + tokens.length);
        }
        List<HttpRange> result = new ArrayList<>(tokens.length);
        for (String token : tokens) {
            result.add(parseRange(token));
        }
        return result;
    }

    private static HttpRange parseRange(String range) {
        AssertTools.notEmpty(range, "Range String must not be empty");
        int dashIdx = range.indexOf('-');
        if (dashIdx > 0) {
            long firstPos = parseLong(range, 0, dashIdx, 10);
            if (dashIdx < range.length() - 1) {
                Long lastPos = parseLong(range, dashIdx + 1, range.length(), 10);
                return new ByteRange(firstPos, lastPos);
            } else {
                return new ByteRange(firstPos, null);
            }
        } else if (dashIdx == 0) {
            long suffixLength = parseLong(range, 1, range.length(), 10);
            return new SuffixByteRange(suffixLength);
        } else {
            throw new IllegalArgumentException("Range '" + range + "' does not contain \"-\"");
        }
    }

    /**
     * Convert each {@code HttpRange} into a {@code ResourceRegion}, selecting the
     * appropriate segment of the given {@code Resource} using HTTP Range information.
     *
     * @param ranges   the list of ranges
     * @param resource the resource to select the regions from
     * @return the list of regions for the given resource
     */
    public static List<ResourceRegion> toResourceRegions(List<HttpRange> ranges, Resource resource) {
        if (CollectionTools.isEmpty(ranges)) {
            return Collections.emptyList();
        }
        List<ResourceRegion> regions = new ArrayList<>(ranges.size());
        for (HttpRange range : ranges) {
            regions.add(range.toResourceRegion(resource));
        }
        if (ranges.size() > 1) {
            long length = getLengthFor(resource);
            long total = 0;
            for (ResourceRegion region : regions) {
                total += region.getCount();
            }
            if (total >= length) {
                throw new IllegalArgumentException("The sum of all ranges (" + total +
                        ") should be less than the resource length (" + length + ")");
            }
        }
        return regions;
    }

    private static long getLengthFor(Resource resource) {
        try {
            long contentLength = resource.contentLength();
            AssertTools.isTrue(contentLength > 0, "Resource content length should be > 0");
            return contentLength;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to obtain Resource content length", ex);
        }
    }

    /**
     * Return a string representation of the given list of {@code HttpRange} objects.
     * <p>This method can be used to for an {@code Range} header.
     *
     * @param ranges the ranges to create a string of
     * @return the string representation
     */
    public static String toString(Collection<HttpRange> ranges) {
        AssertTools.isTrue(CollectionTools.isEmpty(ranges), "Ranges Collection must not be empty");
        StringJoiner builder = new StringJoiner(", ", BYTE_RANGE_PREFIX, "");
        for (HttpRange range : ranges) {
            builder.add(range.toString());
        }
        return builder.toString();
    }


    /**
     * Represents an HTTP/1.1 byte range, with a first and optional last position.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-2.1">Byte Ranges</a>
     * @see HttpRange#createByteRange(long)
     * @see HttpRange#createByteRange(long, long)
     */
    private static class ByteRange extends HttpRange {

        private final long firstPos;

        private final Long lastPos;

        public ByteRange(long firstPos, Long lastPos) {
            assertPositions(firstPos, lastPos);
            this.firstPos = firstPos;
            this.lastPos = lastPos;
        }

        private void assertPositions(long firstBytePos, Long lastBytePos) {
            if (firstBytePos < 0) {
                throw new IllegalArgumentException("Invalid first byte position: " + firstBytePos);
            }
            if (lastBytePos != null && lastBytePos < firstBytePos) {
                throw new IllegalArgumentException("firstBytePosition=" + firstBytePos +
                        " should be less then or equal to lastBytePosition=" + lastBytePos);
            }
        }

        @Override
        public long getRangeStart(long length) {
            return this.firstPos;
        }

        @Override
        public long getRangeEnd(long length) {
            if (this.lastPos != null && this.lastPos < length) {
                return this.lastPos;
            } else {
                return length - 1;
            }
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof ByteRange) {
                ByteRange that = (ByteRange) other;
                return this.firstPos == that.firstPos &&
                        ObjectTools.nullSafeEquals(this.lastPos, that.lastPos);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.firstPos, this.lastPos);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.firstPos);
            builder.append('-');
            if (this.lastPos != null) {
                builder.append(this.lastPos);
            }
            return builder.toString();
        }
    }


    /**
     * Represents an HTTP/1.1 suffix byte range, with a number of suffix bytes.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-2.1">Byte Ranges</a>
     * @see HttpRange#createSuffixRange(long)
     */
    private static class SuffixByteRange extends HttpRange {

        private final long suffixLength;

        public SuffixByteRange(long suffixLength) {
            if (suffixLength < 0) {
                throw new IllegalArgumentException("Invalid suffix length: " + suffixLength);
            }
            this.suffixLength = suffixLength;
        }

        @Override
        public long getRangeStart(long length) {
            if (this.suffixLength < length) {
                return length - this.suffixLength;
            } else {
                return 0;
            }
        }

        @Override
        public long getRangeEnd(long length) {
            return length - 1;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof SuffixByteRange) {
                SuffixByteRange that = (SuffixByteRange) other;
                return this.suffixLength == that.suffixLength;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(this.suffixLength);
        }

        @Override
        public String toString() {
            return "-" + this.suffixLength;
        }
    }

    /**
     * Parses the {@link CharSequence} argument as a signed {@code long} in
     * the specified {@code radix}, beginning at the specified
     * {@code beginIndex} and extending to {@code endIndex - 1}.
     *
     * <p>The method does not take steps to guard against the
     * {@code CharSequence} being mutated while parsing.
     *
     * @param s          the {@code CharSequence} containing the {@code long}
     *                   representation to be parsed
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex   the ending index, exclusive.
     * @param radix      the radix to be used while parsing {@code s}.
     * @return the signed {@code long} represented by the subsequence in
     * the specified radix.
     * @throws NullPointerException      if {@code s} is null.
     * @throws IndexOutOfBoundsException if {@code beginIndex} is
     *                                   negative, or if {@code beginIndex} is greater than
     *                                   {@code endIndex} or if {@code endIndex} is greater than
     *                                   {@code s.length()}.
     * @throws NumberFormatException     if the {@code CharSequence} does not
     *                                   contain a parsable {@code long} in the specified
     *                                   {@code radix}, or if {@code radix} is either smaller than
     *                                   {@link java.lang.Character#MIN_RADIX} or larger than
     *                                   {@link java.lang.Character#MAX_RADIX}.
     */
    private static long parseLong(CharSequence s, int beginIndex, int endIndex, int radix)
            throws NumberFormatException {
        Objects.requireNonNull(s);
        checkFromToIndex(beginIndex, endIndex, s.length(), null);

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " less than Character.MIN_RADIX");
        }
        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " greater than Character.MAX_RADIX");
        }

        boolean negative = false;
        int i = beginIndex;
        long limit = -Long.MAX_VALUE;

        if (i < endIndex) {
            char firstChar = s.charAt(i);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+') {
                    throw new NumberFormatException("Error at index "
                            + (endIndex - beginIndex) + " in: \""
                            + s.subSequence(beginIndex, endIndex) + "\"");
                }
                i++;
            }
            if (i >= endIndex) { // Cannot have lone "+", "-" or ""
                throw new NumberFormatException("Error at index "
                        + (endIndex - beginIndex) + " in: \""
                        + s.subSequence(beginIndex, endIndex) + "\"");
            }
            long multmin = limit / radix;
            long result = 0;
            while (i < endIndex) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                int digit = Character.digit(s.charAt(i), radix);
                if (digit < 0 || result < multmin) {
                    throw new NumberFormatException("Error at index "
                            + (endIndex - beginIndex) + " in: \""
                            + s.subSequence(beginIndex, endIndex) + "\"");
                }
                result *= radix;
                if (result < limit + digit) {
                    throw new NumberFormatException("Error at index "
                            + (endIndex - beginIndex) + " in: \""
                            + s.subSequence(beginIndex, endIndex) + "\"");
                }
                i++;
                result -= digit;
            }
            return negative ? result : -result;
        } else {
            throw new NumberFormatException("");
        }
    }

    /**
     * Checks if the sub-range from {@code fromIndex} (inclusive) to
     * {@code toIndex} (exclusive) is within the bounds of range from {@code 0}
     * (inclusive) to {@code length} (exclusive).
     *
     * <p>The sub-range is defined to be out of bounds if any of the following
     * inequalities is true:
     * <ul>
     *  <li>{@code fromIndex < 0}</li>
     *  <li>{@code fromIndex > toIndex}</li>
     *  <li>{@code toIndex > length}</li>
     *  <li>{@code length < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * <p>If the sub-range is out of bounds, then a runtime exception is
     * thrown that is the result of applying the following arguments to the
     * exception formatter: the name of this method, {@code checkFromToIndex};
     * and an unmodifiable list of integers whose values are, in order, the
     * out-of-bounds arguments {@code fromIndex}, {@code toIndex}, and {@code length}.
     *
     * @param <X>       the type of runtime exception to throw if the arguments are
     *                  out of bounds
     * @param fromIndex the lower-bound (inclusive) of the sub-range
     * @param toIndex   the upper-bound (exclusive) of the sub-range
     * @param length    the upper-bound (exclusive) the range
     * @param oobef     the exception formatter that when applied with this
     *                  method name and out-of-bounds arguments returns a runtime
     *                  exception.  If {@code null} or returns {@code null} then, it is as
     *                  if an exception formatter produced from an invocation of
     *                  {@code outOfBoundsExceptionFormatter(IndexOutOfBounds::new)} is used
     *                  instead (though it may be more efficient).
     *                  Exceptions thrown by the formatter are relayed to the caller.
     * @return {@code fromIndex} if the sub-range within bounds of the range
     * @throws X                         if the sub-range is out of bounds and the exception factory
     *                                   function is non-{@code null}
     * @throws IndexOutOfBoundsException if the sub-range is out of bounds and
     *                                   the exception factory function is {@code null}
     */
    public static <X extends RuntimeException>
    int checkFromToIndex(int fromIndex, int toIndex, int length,
                         BiFunction<String, List<Number>, X> oobef) {
        if (fromIndex < 0 || fromIndex > toIndex || toIndex > length)
            throw new IndexOutOfBoundsException();
        return fromIndex;
    }
}
