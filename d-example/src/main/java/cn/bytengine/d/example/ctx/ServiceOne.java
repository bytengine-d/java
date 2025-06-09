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
    public void handleEvent(String name, int age) {
        System.out.println(name + ":" + age);
    }
}
