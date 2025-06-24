package cn.bytengine.d.utils;

import cn.bytengine.d.lang.AssertTools;

/**
 * Region of a {@link Resource} implementation, materialized by a {@code position}
 * within the {@link Resource} and a byte {@code count} for the length of that region.
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ResourceRegion {
    private final Resource resource;

    private final long position;

    private final long count;


    /**
     * Create a new {@code ResourceRegion} from a given {@link Resource}.
     * This region of a resource is represented by a start {@code position}
     * and a byte {@code count} within the given {@code Resource}.
     *
     * @param resource a Resource
     * @param position the start position of the region in that resource
     * @param count    the byte count of the region in that resource
     */
    public ResourceRegion(Resource resource, long position, long count) {
        AssertTools.notNull(resource, "Resource must not be null");
        AssertTools.isTrue(position >= 0, "'position' must be greater than or equal to 0");
        AssertTools.isTrue(count >= 0, "'count' must be greater than or equal to 0");
        this.resource = resource;
        this.position = position;
        this.count = count;
    }


    /**
     * Return the underlying {@link Resource} for this {@code ResourceRegion}.
     *
     * @return TODO
     */
    public Resource getResource() {
        return this.resource;
    }

    /**
     * Return the start position of this region in the underlying {@link Resource}.
     *
     * @return TODO
     */
    public long getPosition() {
        return this.position;
    }

    /**
     * Return the byte count of this region in the underlying {@link Resource}.
     *
     * @return TODO
     */
    public long getCount() {
        return this.count;
    }
}
