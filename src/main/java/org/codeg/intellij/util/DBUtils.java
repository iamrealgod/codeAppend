package org.codeg.intellij.util;

import org.codeg.intellij.config.Config;
import org.codeg.intellij.config.Constants;
import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.entity.FieldEntity;

import java.util.List;

/**
 * 数据库数据处理
 * @author liufei
 * @date 2018/12/28 14:47
 */
public class DBUtils {

    /**
     * 数据库表名转属性
     * @param tableName
     * @return
     */
    public static String toClassName(String tableName) {
        if (tableName.startsWith(Constants.UNDER_LINE)) {
            tableName = tableName.substring(1, tableName.length());
        }
        final String[] split = tableName.split(Constants.UNDER_LINE);
        for (int i = 0; i < split.length; i++) {
            split[i] = String.valueOf(split[i].charAt(0)).toUpperCase() + split[i].substring(1);
        }
        return StringUtils.join(split);
    }
    /**
     * 数据库字段转属性
     * @param column
     * @return
     */
    public static String toProperty(String column) {
        if (column.startsWith(Constants.UNDER_LINE)) {
            column = column.replaceFirst(Constants.UNDER_LINE, StringUtils.EMPTY);
        }
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
            return Config.getInstant().isDateChk() ? "LocalDateTime" : "Date";
        }
        if (columnType.contains(Constants.MYBATIS_TIMESTAMP)) {
            return Config.getInstant().isDateChk() ? "LocalDateTime" : "Date";
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
    public static ClassEntity getClassEntity(String tableName, String tbPrefixType, String tbPrefix) {
        ClassEntity entity = new ClassEntity();
        entity.setTableName(tableName);
        tableName = StringUtils.handlePrefix(tableName, tbPrefixType, tbPrefix);
        entity.setClassName(DBUtils.toClassName(tableName));
        return entity;
    }

    public static List<FieldEntity> getFieldEntities(String fieldSql, String fdPrefixType, String fdPrefix) {
        return RegexUtils.parseFieldSql(fieldSql, fdPrefixType, fdPrefix);
    }
}
