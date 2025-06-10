package cn.bytengine.d.example.ctx;

import cn.bytengine.d.ctx.annotations.CtxWrapper;

/**
 * TODO
 * <p>
 * <ul>
 * <li>ProjectName:    d
 * <li>Package:        cn.bytengine.d.example.ctx
 * <li>ClassName:      ServiceOne
 * <li>Date:    2024/5/7 17:28
 * </ul>
 * </p>
 *
 * @author Ban Tenio
 * @version 1.0
 */
@CtxWrapper
public class ServiceOne {
    private String username;
    private int age;

    public String getUsername() {
        return username;
    }

    public ServiceOne setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getAge() {
        return age;
    }

    public ServiceOne setAge(int age) {
        this.age = age;
        return this;
    }

    public void handleEvent(String name, int age) {
        StringBuilder buf = new StringBuilder(name + ":" + age);
        buf.delete(0, buf.length());
    }
}
