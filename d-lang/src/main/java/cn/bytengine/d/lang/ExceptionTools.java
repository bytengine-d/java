package cn.bytengine.d.lang;

/**
 * 异常工具类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ExceptionTools {
    private ExceptionTools() {
    }

    /**
     * 获得完整消息，包括异常名，消息格式为：{SimpleClassName}: {ThrowableMessage}
     *
     * @param e 异常
     * @return 完整消息
     */
    public static String getMessage(Throwable e) {
        if (null == e) {
            return CharSequenceTools.NULL;
        }
        return CharSequenceTools.format("{}: {}", e.getClass().getSimpleName(), e.getMessage());
    }
}
