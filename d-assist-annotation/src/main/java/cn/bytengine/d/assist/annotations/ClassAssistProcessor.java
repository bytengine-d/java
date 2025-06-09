package cn.bytengine.d.assist.annotations;

import cn.bytengine.d.apt.util.AbstractAnnotationProcessor;

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
 * 类辅助处理器，用于生成类信息和属性、方法访问器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ClassAssistProcessor extends AbstractAnnotationProcessor {

    public ClassAssistProcessor() {
        super(ClassAssist.class);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean isClaimed = false;
        Set<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(ClassAssist.class);
        if (!annotatedClasses.isEmpty()) {
            note("processing ClassAssist count: {}", annotatedClasses.size());
            for (Element annotatedClass : annotatedClasses) {
                ClassAssist ctxWrapper = annotatedClass.getAnnotation(ClassAssist.class);
            }
        } else {
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
                error("create resource occurred error. \n{}", e);
            }
        }
        return isClaimed;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}
