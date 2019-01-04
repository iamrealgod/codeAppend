package org.codeg.intellij.util;

import org.codeg.intellij.config.Constants;
import org.codeg.intellij.entity.FieldEntity;
import org.codeg.intellij.entity.ClassEntity;

import java.util.List;

/**
 * 数据库数据处理
 * @author liufei
 * @date 2018/12/28 14:47
 */
public class DBUtils {

    /**
     * 数据库字段转属性
     * @param column
     * @return
     */
    public static String toProperty(String column) {
        final String[] split = column.split(Constants.UNDER_LINE);
        for (int i = 1; i < split.length; i++) {
            split[i] = String.valueOf(split[i].charAt(0)).toUpperCase() + split[i].substring(1);
        }
        return StringUtils.join(split);
    }

    /**
     * 数据库字段类型转java属性类型
     * @param columnType
     * @return
     */
    public static String toPropertyType(String columnType) {
        if (columnType.contains(Constants.MYBATIS_TINYINT)) {
            return "Integer";
        }
        if (columnType.contains(Constants.MYBATIS_BIGINT)) {
            return "Long";
        }
        if (columnType.contains(Constants.MYBATIS_INT)) {
            return "Integer";
        }
        if (columnType.contains(Constants.MYBATIS_DOUBLE)) {
            return "Double";
        }
        if (columnType.contains(Constants.MYBATIS_CHAR)) {
            return "String";
        }
        if (columnType.contains(Constants.MYBATIS_TEXT)) {
            return "String";
        }
        if (columnType.contains(Constants.MYBATIS_DATETIME)) {
            return "Date";
        }
        if (columnType.contains(Constants.MYBATIS_DECIMAL)) {
            return "BigDecimal";
        }
        return "String";
    }

    /**
     * 获取class信息
     * @param tableName
     * @return
     */
    public static ClassEntity getClassEntity(String tableName) {
        ClassEntity entity = new ClassEntity();
        entity.setTableName(tableName);
        entity.setClassName(tableName);
        if (tableName.contains(Constants.UNDER_LINE)) {
            tableName = tableName.substring(tableName.indexOf(Constants.UNDER_LINE));
            entity.setClassName(DBUtils.toProperty(tableName));
        }
        return entity;
    }

    public static List<FieldEntity> getFieldEntities(String fieldSql) {
        return RegexUtils.parseFieldSql(fieldSql);
    }
}
