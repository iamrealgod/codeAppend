package org.codeg.intellij.builder;

import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Config;
import org.codeg.intellij.config.Constants;
import org.codeg.intellij.config.Enums.AppendType;
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
        String content = buildEntityStr(classEntity, fieldEntities, Config.getInstant().getColumnChk(), Config.getInstant().getAppendType(), Config.getInstant().isBuilderChk());
        final String javaFilePath = StringUtils.getEntityFilePath(Cache.getInstant().getEntityPath(), classEntity.getClassName());
        FileUtils.createFile(javaFilePath, content);
    }

    public static String buildEntityStr(ClassEntity classEntity, List<FieldEntity> fieldEntities, boolean columnChk, String appendType, boolean builderChk) {
        // 字段处理
        StringBuilder fields = new StringBuilder();
        StringBuilder methods = new StringBuilder(StringUtils.EMPTY);
        for (FieldEntity fieldEntity : fieldEntities) {
            if (fieldEntity.getProperty().equals(Constants.ID)) {
                // exclude or not exclude column
                fields.append(columnChk ? Constants.entityFieldIdStr : Constants.entityFieldIdStr_without_column);
            } else {
                // exclude or not exclude column
                String fieldStr = columnChk ? Constants.entityFieldStr : Constants.entityFieldStr_without_column;
                fields.append(fieldStr.replaceAll("\\{column}", fieldEntity.getColumn())
                        .replaceAll("\\{propertyType}",fieldEntity.getPropertyType())
                        .replaceAll("\\{property}",fieldEntity.getProperty())
                        .replaceAll("\\{comment}", Objects.isNull(fieldEntity.getComment()) ? StringUtils.EMPTY : fieldEntity.getComment()));
            }
            if (isGetset(appendType)) {
                methods.append(Constants.entityFieldStr_getset_method.replaceAll("\\{propertyType}", fieldEntity.getPropertyType())
                        .replaceAll("\\{property}", fieldEntity.getProperty())
                        .replaceAll("\\{getProperty}", getPropertyStr(fieldEntity.getProperty()))
                        .replaceAll("\\{setProperty}", setPropertyStr(fieldEntity.getProperty())));
            }
        }
        // 类处理
        String content;
        fields.append(isLombok(appendType) ? StringUtils.EMPTY : methods.toString());
        String lombokData = isLombok(appendType) ? "@Data\n" + getBuilder(builderChk) : StringUtils.EMPTY;
        String lombokImport = isLombok(appendType) ? "import lombok.Data;\n" + getBuilderImport(builderChk): StringUtils.EMPTY;
        content = columnChk ? Constants.entityStr : Constants.entityStr_without_column;
        content = content.replaceAll("\\{entityPackage}", Cache.getInstant().getEntityPackage())
                .replaceAll("\\{tableName}", classEntity.getTableName())
                .replaceAll("\\{tableName}", classEntity.getTableName())
                .replaceAll("\\{fields}", fields.toString())
                .replaceAll("\\{lombokData}", lombokData)
                .replaceAll("\\{lombokImport}", lombokImport)
                .replaceAll("\\{fieldImport}", getFieldImport(fieldEntities))
                .replaceAll("\\{className}", classEntity.getClassName());
        return content;
    }

    private static String getFieldImport(List<FieldEntity> fieldEntities) {
        String fieldImport = StringUtils.EMPTY;
        if (fieldEntities.stream().anyMatch(fieldEntity -> fieldEntity.getPropertyType().equals("Date"))) {
            fieldImport = fieldImport + "import java.util.Date;\n";
        }
        if (fieldEntities.stream().anyMatch(fieldEntity -> fieldEntity.getPropertyType().equals("LocalDateTime"))) {
            fieldImport = fieldImport + "import java.time.LocalDateTime;\n";
        }
        if (fieldEntities.stream().anyMatch(fieldEntity -> fieldEntity.getPropertyType().equals("BigDecimal"))) {
            fieldImport = fieldImport + "import java.math.BigDecimal;\n";
        }
        return fieldImport;
    }

    private static String getBuilderImport(boolean builderChk) {
        return builderChk ? "import lombok.Builder;\n" +
                "import lombok.AllArgsConstructor;\n" +
                "import lombok.NoArgsConstructor;\n" : StringUtils.EMPTY;
    }

    private static String getBuilder(boolean builderChk) {
        return builderChk ? "@Builder\n" +
                "@AllArgsConstructor\n" +
                "@NoArgsConstructor\n" : StringUtils.EMPTY;
    }

    private static boolean isLombok(String appendType) {
        return appendType.equals(AppendType.LOMBOK.name());
    }
    private static boolean isGetset(String appendType) {
        return appendType.equals(AppendType.GETSET.name());
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
        StringBuilder appendMethods = new StringBuilder();
        for (FieldEntity fieldEntity : fieldEntities) {
            final String property = fieldEntity.getProperty();
            if (!originContent.contains(StringUtils.entityProperty(property))) {
                // lombok or getset
                String entityFieldStr = Config.getInstant().getColumnChk() ? Constants.entityFieldStr : Constants.entityFieldStr_without_column;
                appendFields.append(entityFieldStr.replaceAll("\\{column}", fieldEntity.getColumn())
                        .replaceAll("\\{propertyType}", fieldEntity.getPropertyType())
                        .replaceAll("\\{property}", fieldEntity.getProperty())
                        .replaceAll("\\{comment}", Objects.isNull(fieldEntity.getComment()) ? StringUtils.EMPTY : fieldEntity.getComment()));
                if (isGetset(Config.getInstant().getAppendType())) {
                    appendMethods.append(Constants.entityFieldStr_getset_method.replaceAll("\\{propertyType}", fieldEntity.getPropertyType())
                            .replaceAll("\\{property}", fieldEntity.getProperty())
                            .replaceAll("\\{getProperty}", getPropertyStr(fieldEntity.getProperty()))
                            .replaceAll("\\{setProperty}", setPropertyStr(fieldEntity.getProperty())));
                }
            }
        }
        appendFields.append(isLombok(Config.getInstant().getAppendType()) ? StringUtils.EMPTY : appendMethods.toString());
        replaceContent = replaceContent.replace("{appendFields}", appendFields.toString());
        FileUtils.createFile(javaFilePath, replaceContent);
    }

    private static String getPropertyStr(String property) {
        String first = property.substring(0, 1);
        return "get" + first.toUpperCase() + property.substring(1);
    }

    public static String setPropertyStr(String property) {
        String first = property.substring(0, 1);
        return "set" + first.toUpperCase() + property.substring(1);
    }
}
