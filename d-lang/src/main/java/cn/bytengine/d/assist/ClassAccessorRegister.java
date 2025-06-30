package cn.bytengine.d.assist;

import java.util.ServiceLoader;

/**
 * 类访问器注册器
 *
 * @author Ban Tenio
 * @version 1.0
 * @see cn.bytengine.d.assist.annotations.ClassAccess
 */
public interface ClassAccessorRegister {
    /**
     * 基于SPI加载所有注册类访问器注册器
     */
    static void load() {
        ServiceLoader<ClassAccessorRegister> serviceLoader = ServiceLoader.load(ClassAccessorRegister.class);
        serviceLoader.iterator().forEachRemaining(ClassAccessorRegister::register);
    }

    /**
     * 执行类访问器注册
     */
    void register();
}
