<#assign packageName = getString('packageName') >
<#assign className = getString('className') >
<#assign methodNames = get('methodNames') >
<#assign fieldNames = get('fieldNames') >

package ${packageName};

import cn.bytengine.d.assist.ClassAccessor;
import cn.bytengine.d.assist.ClassAccessors;
import cn.bytengine.d.assist.ClassAccessorRegister;
import cn.bytengine.d.fn.invoker.MetaInfoInvokerFactory;

import ${packageName}.${className};

public final class ${className}ClassAccessorRegister implements ClassAccessorRegister {
public void register() {
ClassAccessor classAccessor = ClassAccessors.register(${className}.class);
<#list methodNames as methodName>classAccessor.addMethodAccessor(MetaInfoInvokerFactory.createInvoker(${className}::${methodName}));
</#list>
<#list fieldNames as fieldName>classAccessor.addPropertyAccessor("${fieldName}");
</#list>
}
}