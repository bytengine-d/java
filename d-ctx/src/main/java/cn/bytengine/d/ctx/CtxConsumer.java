package cn.bytengine.d.ctx;

import java.io.Serializable;
import java.util.Objects;

/**
 * Ctx消费者
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface CtxConsumer extends Serializable {
    /**
     * Ctx处理
     *
     * @param ctx TODO
     */
    void accept(Ctx ctx);

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default CtxConsumer andThen(CtxConsumer after) {
        Objects.requireNonNull(after);
        return (Ctx t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
