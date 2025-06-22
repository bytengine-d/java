package cn.bytengine.d.utils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * Defines the contract for path (segments).
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface PathComponent {
    /**
     * 获取路径
     *
     * @return 路径
     */
    String getPath();

    /**
     * 获取路径片段集合
     *
     * @return 路径片段集合
     */
    List<String> getPathSegments();

    /**
     * 根据指定编码器，创建PathComponent
     *
     * @param encoder 编码器
     * @return 路径组件对象
     */
    PathComponent encode(BiFunction<String, HierarchicalUriComponents.Type, String> encoder);

    /**
     * 验证路径组件是否符合规则
     */
    void verify();

    /**
     * 根据指定编码器和URI模版变量表构建路径组件
     *
     * @param uriVariables URI模版变量表
     * @param encoder      编码器
     * @return 路径组件
     */
    PathComponent expand(UriComponents.UriTemplateVariables uriVariables, UnaryOperator<String> encoder);

    /**
     * 将路径内容赋值到指定UriComponentBuilder
     *
     * @param builder UriComponentBuilder
     */
    void copyToUriComponentsBuilder(UriComponentsBuilder builder);
}
