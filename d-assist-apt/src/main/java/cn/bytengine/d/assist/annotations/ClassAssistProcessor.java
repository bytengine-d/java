package cn.bytengine.d.assist.annotations;

import cn.bytengine.d.apt.util.AbstractAnnotationProcessor;
import cn.bytengine.d.ctx.Ctx;
import cn.bytengine.d.ctx.Ctxs;
import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.CollectionTools;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 类辅助处理器，用于生成类信息和属性、方法访问器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ClassAssistProcessor extends AbstractAnnotationProcessor {
    private static final List<Modifier> METHOD_MODIFIER_FILTER_LIST = Arrays.asList(Modifier.STATIC, Modifier.ABSTRACT, Modifier.PROTECTED, Modifier.PRIVATE, Modifier.DEFAULT);

    private Configuration configuration;
    private Ctx ctx;
    private Template classAccessorRegisterTemplate;
    private Template classAccessorRegisterServicesTemplate;

    public ClassAssistProcessor() {
        super(ClassAccess.class);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean isClaimed = false;
        Set<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(ClassAccess.class);
        if (!annotatedClasses.isEmpty()) {
            if (classAccessorRegisterTemplate == null) {
                try {
                    classAccessorRegisterTemplate =
                            configuration.getTemplate("cn.bytengine.d.assist.ClassAccessorRegister.ftl");
                } catch (IOException e) {
                    error("process ClassAccessorRegister template occurred error. {}", e);
                    throw new RuntimeException(e);
                }
            }
            if (classAccessorRegisterServicesTemplate == null) {
                try {
                    classAccessorRegisterServicesTemplate =
                            configuration.getTemplate("SubClassAccessorRegister.java.ftl");
                } catch (IOException e) {
                    error("process ClassAccessorRegister template occurred error. {}", e);
                    throw new RuntimeException(e);
                }
            }
            List<String> generatedClassAccessorRegisterList = new ArrayList<>(16);
            String generatedClassName;
            for (Element annotatedClass : annotatedClasses) {
                generatedClassName = processElementClass(ctx, annotatedClass, roundEnv);
                if (CharSequenceTools.isNotBlank(generatedClassName)) {
                    generatedClassAccessorRegisterList.add(generatedClassName);
                }
            }
            if (!generatedClassAccessorRegisterList.isEmpty()) {
                processServiceMetaInf(ctx, generatedClassAccessorRegisterList, roundEnv);
            }
        }
        return isClaimed;
    }

    protected void processServiceMetaInf(Ctx ctx, List<String> generatedClassAccessorRegisterList, RoundEnvironment roundEnv) {
        ctx.set("serviceClassList", generatedClassAccessorRegisterList);
        try {
            FileObject file = filer().getResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/services/cn.bytengine.d.assist.ClassAccessorRegister");
            long fileLastModified = file.getLastModified();
            if (fileLastModified == 0) {
                file = filer().createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/services/cn.bytengine.d.assist.ClassAccessorRegister");
                try (Writer out = file.openWriter()) {
                    classAccessorRegisterServicesTemplate.process(ctx, out);
                }
            }
        } catch (TemplateException | IOException e) {
            error("create cn.bytengine.d.assist.ClassAccessorRegister service file occurred error. \n{}", e);
        }
    }

    protected String processElementClass(Ctx parent, Element annotatedClass, RoundEnvironment roundEnv) {
        String className = annotatedClass.getSimpleName().toString();
        ElementKind elementKind = annotatedClass.getKind();
        if (elementKind != ElementKind.CLASS) {
            warn("not supported for {}, it is not class.", className);
            return null;
        }
        Set<Modifier> modifiers = annotatedClass.getModifiers();
        if (modifiers.contains(Modifier.ABSTRACT) || !modifiers.contains(Modifier.PUBLIC)) {
            warn("not supported for abstract or no public class {}.", className);
            return null;
        }
        String packageName = elements().getPackageOf(annotatedClass).getQualifiedName().toString();
        String targetClassName = className + "ClassAccessorRegister";
        String targetFullClassName = packageName + "." + targetClassName;

        Ctx ctx = Ctxs.space(parent);
        ctx.set("className", className);
        ctx.set("packageName", packageName);
        ctx.set("targetClassName", targetClassName);

        List<? extends Element> subElements = annotatedClass.getEnclosedElements();

        List<String> methodNames = subElements.stream()
                .filter(subElement -> subElement.getKind() == ElementKind.METHOD && !CollectionTools.hasAny(subElement.getModifiers(), METHOD_MODIFIER_FILTER_LIST))
                .map(subElement -> subElement.getSimpleName().toString())
                .collect(Collectors.toList());
        ctx.set("methodNames", methodNames);

        List<String> fieldNames = subElements.stream()
                .filter(subElement -> subElement.getKind() == ElementKind.FIELD && !subElement.getModifiers().contains(Modifier.STATIC))
                .map(subElement -> subElement.getSimpleName().toString())
                .collect(Collectors.toList());
        ctx.set("fieldNames", fieldNames);

        try {
            JavaFileObject javaFileObject = filer().createSourceFile(targetClassName);
            try (Writer out = javaFileObject.openWriter()) {
                classAccessorRegisterTemplate.process(ctx, out);
            }
        } catch (TemplateException | IOException e) {
            error("process ClassAccessorRegister template occurred error. {}", e);
            return null;
        }
        return targetFullClassName;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    protected void internalInit(ProcessingEnvironment processingEnv) {
        ctx = Ctxs.space();
        configuration = new Configuration(Configuration.getVersion());
        configuration.setClassForTemplateLoading(ClassAssistProcessor.class, "/ftl/");
    }
}
