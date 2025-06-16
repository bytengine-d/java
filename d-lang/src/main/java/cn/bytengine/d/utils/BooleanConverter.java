package cn.bytengine.d.utils;


import cn.bytengine.d.lang.BooleanTools;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class BooleanConverter extends AbstractConverter<Boolean> {
    @Override
    protected Boolean convertInternal(Object value) {
        if (value instanceof Number) {
            // 0为false，其它数字为true
            return 0 != ((Number) value).doubleValue();
        }
        //Object不可能出现Primitive类型，故忽略
        return BooleanTools.toBoolean(convertToStr(value));
    }
}
