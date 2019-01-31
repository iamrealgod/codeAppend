package org.codeg.intellij.builder;

import org.codeg.intellij.config.Constants;
import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.entity.FieldEntity;
import org.codeg.intellij.util.DBUtils;
import org.codeg.intellij.util.RegexUtils;
import org.codeg.intellij.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 *
 * @author liufei
 * @date 2019/1/30 17:33
 */
public class ExampleBuilder {
    public static final String SQL = "CREATE TABLE `pre_example` (\n"
            + "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'primary key',\n"
            + "  `num_no` varchar(64) NOT NULL COMMENT 'number',\n"
            + "  `longitude` decimal(10,2) DEFAULT NULL COMMENT 'address longitude',\n"
            + "  `update_time` datetime NOT NULL COMMENT 'up date',\n" + "  PRIMARY KEY (`id`),\n"
            + "  KEY `idx_create_time` (`create_time`) USING BTREE\n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='example table';";

    public static String buildExampleEntityContent(boolean columnChk, String appendType, String tbPrefixType, String tbPrefix, String fdPrefixType, String fdPrefix) {
        Map<String, String> entityMap = RegexUtils.parseOriginSql(SQL);
        for (String key : entityMap.keySet()) {
            final ClassEntity classEntity = DBUtils.getClassEntity(key, tbPrefixType, tbPrefix);
            final List<FieldEntity> fieldEntities = DBUtils.getFieldEntities(entityMap.get(key), fdPrefixType,
                    fdPrefix);
            return EntityBuilder.buildEntityStr(classEntity, fieldEntities, columnChk, appendType);
        }
        return "";
    }

    public static String buildExampleMapperContent(String tbPrefixType, String tbPrefix, String fdPrefixType, String fdPrefix) {
        Map<String, String> entityMap = RegexUtils.parseOriginSql(SQL);
        for (String key : entityMap.keySet()) {
            final ClassEntity classEntity = DBUtils.getClassEntity(key, tbPrefixType, tbPrefix);
            final List<FieldEntity> fieldEntities = DBUtils.getFieldEntities(entityMap.get(key), fdPrefixType,
                    fdPrefix);
            return MapperBuilder.buildMapperStr(classEntity, fieldEntities);
        }
        return "";
    }

    public static String buildExampleEntityName(String tbPrefixType, String tbPrefix){
        Map<String, String> entityMap = RegexUtils.parseOriginSql(SQL);
        for (String key : entityMap.keySet()) {
            final ClassEntity classEntity = DBUtils.getClassEntity(key, tbPrefixType, tbPrefix);
            return classEntity.getClassName().concat(Constants.JAVA_SUFFIX);
        }
        return "";
    }
    public static String buildExampleMapperName(String tbPrefixType, String tbPrefix, String mapperSuffix){
        Map<String, String> entityMap = RegexUtils.parseOriginSql(SQL);
        for (String key : entityMap.keySet()) {
            final ClassEntity classEntity = DBUtils.getClassEntity(key, tbPrefixType, tbPrefix);
            return StringUtils.getMapperName(classEntity.getClassName(), mapperSuffix);
        }
        return "";
    }

}
