package cn.bytengine.d.utils;

import cn.bytengine.d.lang.BooleanTools;
import cn.bytengine.d.lang.CharSequenceTools;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CharacterConverter extends AbstractConverter<Character> {
    @Override
    protected Character convertInternal(Object value) {
        if (value instanceof Boolean) {
            return BooleanTools.toCharacter((Boolean) value);
        } else {
            final String valueStr = convertToStr(value);
            if (CharSequenceTools.isNotBlank(valueStr)) {
                return valueStr.charAt(0);
            }
        }
        return null;
    }
}
