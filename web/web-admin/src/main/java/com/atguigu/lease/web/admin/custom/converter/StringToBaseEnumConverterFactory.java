package com.atguigu.lease.web.admin.custom.converter;

/*一个HTTP请求传来，比如 /items?itemType=1。
Spring发现需要将字符串 "1" 转换为 ItemType 枚举。
Spring找到了你注册的 StringToBaseEnumConverterFactory 工厂。
Spring调用工厂的 getConverter 方法，并且把目标“设计图纸” ItemType.class 作为参数传进去。
在 getConverter 方法内部：
targetType 现在就是 ItemType.class。
反射发生：targetType.getEnumConstants() 被执行，它动态地获取到 [ItemType.VIDEO, ItemType.AUDIO, ...] 这个数组。
工厂创建了一个专门用于 ItemType 的“一次性”转换器，并把这个数组交给了它。
工厂把这个新鲜出炉的、专门处理 ItemType 的转换器返回给Spring。
Spring使用这个新的转换器，调用它的 convert("1") 方法，最终找到了匹配的 ItemType 枚举并完成转换。*/

import com.atguigu.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) { //泛型方法

        return new Converter<String, T>() { //匿名内部类写法
            @Override
            public T convert(String source) {
                for (T enumConstant : targetType.getEnumConstants()) { //反射写法
                    if(enumConstant.getCode().equals(Integer.valueOf(source))){
                        return enumConstant;
                    }
                }
                throw new IllegalArgumentException("非法的枚举值:" + source);
            }
        };
    }
}
