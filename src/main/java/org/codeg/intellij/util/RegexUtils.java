package org.codeg.intellij.util;

import org.codeg.intellij.config.Constants;
import org.codeg.intellij.entity.FieldEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 正则工具
 * @author liufei
 * @date 2018/12/28 11:30
 */
public class RegexUtils {

    /**
     * 解析实体map
     *  表名 + 字段sql
     * @param sql
     * @return
     */
    public static Map<String, String> parseOriginSql(String sql) {
        //去掉换行
        sql = sql.replaceAll("\r\n", "").replaceAll("\n", "");
        // 查找表名和字段语句
        Matcher matcher = Constants.TABLE_REGEX.matcher(sql);
        Map<String, String> entityMap = new HashMap<>(16);

        // 熔断处理
        int cutOff = 0;
        // 表名  字段
        while (matcher.find() && cutOff < Constants.MAX_MATCHER) {
            entityMap.put(matcher.group(1), matcher.group(2));
            cutOff++;
        }
        return entityMap;
    }

    public static List<FieldEntity> parseFieldSql(String sql, String fdPrefixType, String fdPrefix) {
        // 查找表名和字段语句
        Matcher matcher = Constants.FIELD_REGEX.matcher(sql);
        List<FieldEntity> fieldEntities = new ArrayList<>();
        // 熔断处理
        int cutOff = 0;
        while (matcher.find() && cutOff < Constants.MAX_MATCHER) {
            cutOff++;
            // 判断是否是索引
            final String fieldSql = matcher.group();
            if (fieldSql.contains(Constants.KEY_WORD)) {
                continue;
            }
            FieldEntity param = new FieldEntity();
            String column = matcher.group(1);
            param.setColumn(column);
            // 字段前缀处理
            column = StringUtils.handlePrefix(column, fdPrefixType, fdPrefix);
            param.setProperty(DBUtils.toProperty(column));
            param.setColumnType(matcher.group(2));
            param.setPropertyType(DBUtils.toPropertyType(param.getColumnType()));
            if (matcher.groupCount() >= 4) {
                param.setComment(matcher.group(4));
            }
            fieldEntities.add(param);
        }
        return fieldEntities;
    }

    /**
     * 获取entity字段追加标志
     * @param originContent
     * @return
     */
    public static String parseEntityReplaceStr(String originContent) {
        String replaceStr = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(originContent)) {
            Matcher matcher = Constants.APPEND_ENTITY_FIELD_REGEX.matcher(originContent);
            while (matcher.find()) {
                replaceStr = matcher.group(1);
            }
        }
        return replaceStr;
    }

    /**
     * 获取mapper中resultMap追加标志
     * @param originContent
     * @return
     */
    public static String parseResultMapReplaceStr(String originContent) {
        String replaceStr = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(originContent)) {
            String resultMap = StringUtils.EMPTY;
            // 寻找第一个resultMap
            Matcher matcher = Constants.APPEND_MAPPER_RESULT_REGEX.matcher(originContent);
            if (matcher.find()) {
                resultMap = matcher.group(0);
            }
            // 从第一个resultMap中找最后一个result
            Matcher matcher1 = Constants.APPEND_MAPPER_RESULT_REGEX2.matcher(resultMap);
            while (matcher1.find()) {
                replaceStr = matcher1.group(0);
            }
        }
        return replaceStr;
    }

    /**
     * 获取mapper中sql列的追加标志
     * @param originContent
     * @return
     */
    public static String parseResultFieldsReplaceStr(String originContent) {
        String replaceStr = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(originContent)) {
            Matcher matcher = Constants.APPEND_MAPPER_FIELDS_REGEX.matcher(originContent);
            if (matcher.find()) {
                replaceStr = matcher.group(1);
            }
        }
        return replaceStr;
    }

    public static String getPackagePrefix(String path) {
        String packagePrefix = null;
        Matcher matcher = Constants.PACKAGE_JAVA_REGEX.matcher(path);
        if (matcher.find()) {
            packagePrefix = matcher.group(1);
        } else {
            matcher = Constants.PACKAGE_SRC_REGEX.matcher(path);
            if (matcher.find()) {
                packagePrefix = matcher.group(1);
            }
        }
        return packagePrefix;
    }
}
