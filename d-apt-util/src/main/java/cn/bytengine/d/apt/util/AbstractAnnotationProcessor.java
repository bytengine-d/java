package cn.bytengine.d.apt.util;

import cn.bytengine.d.lang.CharSequenceTools;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Annotation Processor Tool 辅助，提供日志输出和获取组件方法
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class AbstractAnnotationProcessor extends AbstractProcessor {
    private final Set<String> supportedAnnotationTypes = new LinkedHashSet<>();

    private Filer mFiler;
    private Messager mMessager;
    private Elements mElements;

    /**
     * 指定支持Annotation类型
     *
     * @param classes TODO
     */
    protected AbstractAnnotationProcessor(Class<? extends Annotation>... classes) {
        for (Class<? extends Annotation> aClass : classes) {
            supportedAnnotationTypes.add(aClass.getCanonicalName());
        }
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mElements = processingEnv.getElementUtils();
        internalInit(processingEnv);
    }

    abstract protected void internalInit(ProcessingEnvironment processingEnv);

    /**
     * 获取文件管理器
     *
     * @return 文件管理器
     */
    protected Filer filer() {
        return mFiler;
    }

    /**
     * 获取消息管理器
     *
     * @return 消息管理器
     */
    protected Messager messager() {
        return mMessager;
    }

    /**
     * 获取代码元素管理器
     *
     * @return 代码元素管理器
     */
    protected Elements elements() {
        return mElements;
    }

    /**
     * 输出消息，支持格式化消息，消息格式参考CharSequenceTools.format方法
     *
     * @param message 格式化消息
     * @param args    消息占位符参数
     * @see CharSequenceTools#format(CharSequence, Object...)
     */
    protected void note(String message, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, CharSequenceTools.format(message, args));
    }

    /**
     * 输出警告，支持格式化消息，消息格式参考CharSequenceTools.format方法
     *
     * @param message 格式化消息
     * @param args    消息占位符参数
     * @see CharSequenceTools#format(CharSequence, Object...)
     */
    protected void warn(String message, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.WARNING, CharSequenceTools.format(message, args));
    }

    /**
     * 输出强制性警告，支持格式化消息，消息格式参考CharSequenceTools.format方法
     *
     * @param message 格式化消息
     * @param args    消息占位符参数
     * @see CharSequenceTools#format(CharSequence, Object...)
     */
    protected void mandatory(String message, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, CharSequenceTools.format(message, args));
    }

    /**
     * 输出错误，支持格式化消息，消息格式参考CharSequenceTools.format方法
     *
     * @param message 格式化消息
     * @param args    消息占位符参数
     * @see CharSequenceTools#format(CharSequence, Object...)
     */
    protected void error(String message, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, CharSequenceTools.format(message, args));
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotationTypes;
    }
}
