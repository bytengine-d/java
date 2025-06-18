package cn.bytengine.d.ctx.annotations;

import cn.bytengine.d.apt.util.AbstractAnnotationProcessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CtxWrapperProcessor extends AbstractAnnotationProcessor {

    public CtxWrapperProcessor() {
        super(CtxWrapper.class);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean isClaimed = false;
        Set<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(CtxWrapper.class);
        if (!annotatedClasses.isEmpty()) {
            note("processing CtxWrapper count: {}", annotatedClasses.size());
            for (Element annotatedClass : annotatedClasses) {
                CtxWrapper ctxWrapper = annotatedClass.getAnnotation(CtxWrapper.class);
            }
        } else {
            // TODO 生成映射关系类信息
            try {
                FileObject file = filer().getResource(StandardLocation.SOURCE_OUTPUT, "", "META-INF/test.json");
                long fileLastModified = file.getLastModified();
                if (fileLastModified == 0) {
                    file = filer().createResource(StandardLocation.SOURCE_OUTPUT, "", "META-INF/test.json");
                    try (Writer out = file.openWriter()) {
                        out.write("{\"test\": 1}");
                    }
                }
            } catch (IOException e) {
                error("create resource occurred error. \n {}", e);
            }
        }
        return isClaimed;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    protected void internalInit(ProcessingEnvironment processingEnv) {

    }
}
