package org.codeg.intellij.builder;

import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Constants;
import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.entity.FieldEntity;
import org.codeg.intellij.util.FileUtils;
import org.codeg.intellij.util.RegexUtils;
import org.codeg.intellij.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 实体构造器
 * @author liufei
 * @date 2018/12/28 11:17
 */
public class EntityBuilder {

    /**
     * 构造实体类
     * @return
     */
    public static void buildEntityFile(ClassEntity classEntity, List<FieldEntity> fieldEntities) {
        // 字段处理
        StringBuilder fields = new StringBuilder();
        for (FieldEntity fieldEntity : fieldEntities) {
            if (fieldEntity.getProperty().equals(Constants.ID)) {
                fields.append(Constants.entityFieldIdStr);
            } else {
                fields.append(Constants.entityFieldStr.replaceAll("\\{column}", fieldEntity.getColumn())
                        .replaceAll("\\{propertyType}",fieldEntity.getPropertyType())
                        .replaceAll("\\{property}",fieldEntity.getProperty())
                        .replaceAll("\\{comment}", Objects.isNull(fieldEntity.getComment()) ? StringUtils.EMPTY : fieldEntity.getComment()));
            }
        }
        // 类处理
        String content = Constants.entityStr.replaceAll("\\{entityPackage}", Cache.getInstant().getEntityPackage()).
                replaceAll("\\{tableName}", classEntity.getTableName()).
                replaceAll("\\{fields}", fields.toString()).
                replaceAll("\\{className}", classEntity.getClassName());
        final String javaFilePath = StringUtils.getEntityFilePath(Cache.getInstant().getEntityPath(), classEntity.getClassName());
        FileUtils.createFile(javaFilePath, content);
    }

    public static void appendEntityFile(ClassEntity classEntity, List<FieldEntity> fieldEntities) {
        // 获取实体文件
        final String javaFilePath = StringUtils.getEntityFilePath(Cache.getInstant().getEntityPath(), classEntity.getClassName());
        // 获取内容
        final String originContent = FileUtils.readContent(javaFilePath);
        // 获取追加标志
        String replaceStr = RegexUtils.parseEntityReplaceStr(originContent);
        if (Objects.isNull(originContent) || StringUtils.isBlank(replaceStr)) {
            return;
        }
        // 设置替换标志
        String replaceContent = originContent.replace(replaceStr, replaceStr + "\n" + "{appendFields}");
        // 处理追加内容
        StringBuilder appendFields = new StringBuilder();
        for (FieldEntity fieldEntity : fieldEntities) {
            final String property = fieldEntity.getProperty();
            if (!originContent.contains(StringUtils.entityProperty(property))) {
                appendFields.append(Constants.entityFieldStr.replaceAll("\\{column}", fieldEntity.getColumn())
                        .replaceAll("\\{propertyType}",fieldEntity.getPropertyType())
                        .replaceAll("\\{property}",fieldEntity.getProperty())
                        .replaceAll("\\{comment}", Objects.isNull(fieldEntity.getComment()) ? StringUtils.EMPTY : fieldEntity.getComment()));
            }
        }
        replaceContent = replaceContent.replace("{appendFields}", appendFields.toString());
        FileUtils.createFile(javaFilePath, replaceContent);
    }
}
