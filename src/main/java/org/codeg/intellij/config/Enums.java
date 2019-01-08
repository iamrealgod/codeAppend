package org.codeg.intellij.config;

/**
 *
 * @author liufei
 * @date 2019/1/7 15:24
 */
public interface Enums {
    /**
     * 追加类型
     */
    enum AppendType{
        LOMBOK,
        GETSET
    }
    /**
     * 前缀处理方式
     */
    enum PrefixType {
        DEL, // 删除
        STET, // 保留
        OTHER // 删除其他前缀(如果没有则不处理)
    }
}
