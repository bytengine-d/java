package cn.bytengine.d.assist;

import java.util.ServiceLoader;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface ClassAccessorRegister {
    static void load() {
        ServiceLoader<ClassAccessorRegister> serviceLoader = ServiceLoader.load(ClassAccessorRegister.class);
        serviceLoader.iterator().forEachRemaining(ClassAccessorRegister::register);
    }

    void register();
}
