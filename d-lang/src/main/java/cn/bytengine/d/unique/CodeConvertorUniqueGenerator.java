package cn.bytengine.d.unique;

/**
 * 使用唯一数字生成器和编码器，实现唯一值生成器
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CodeConvertorUniqueGenerator implements UniqueGenerator {
    /**
     * 唯一数字生成器
     */
    private final SequenceGenerator sequenceGenerator;
    /**
     * 编解码器
     */
    private final CodeConvertor codeConvertor;

    /**
     * 构造器
     *
     * @param sequenceGenerator 数字生成器
     * @param codeConvertor     编解码器
     */
    public CodeConvertorUniqueGenerator(SequenceGenerator sequenceGenerator, CodeConvertor codeConvertor) {
        this.sequenceGenerator = sequenceGenerator;
        this.codeConvertor = codeConvertor;
    }

    /**
     * 获取数字生成器
     *
     * @return 数字生成器
     */
    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    /**
     * 获取编解码器
     *
     * @return 编解码器
     */
    public CodeConvertor getCodeConvertor() {
        return codeConvertor;
    }

    @Override
    public String nextUnique() {
        return codeConvertor.encode(sequenceGenerator.nextNumber());
    }
}
