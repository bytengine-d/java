package cn.bytengine.d.ctx;

import java.io.Serializable;
import java.util.Objects;

/**
 * TODO
 * <p>
 * <ul>
 * <li>ProjectName:    BambooService
 * <li>Package:        shiren.bamboo.ctx
 * <li>ClassName:      CtxConsumer
 * </ul>
 * </p>
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface CtxConsumer extends Serializable {
    void accept(Ctx ctx);

    default CtxConsumer andThen(CtxConsumer after) {
        Objects.requireNonNull(after);
        return (Ctx t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
