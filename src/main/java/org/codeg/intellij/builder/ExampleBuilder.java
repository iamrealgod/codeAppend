package org.codeg.intellij.builder;

import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.entity.FieldEntity;
import org.codeg.intellij.util.DBUtils;
import org.codeg.intellij.util.RegexUtils;

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

    public static String buildExampleEntity(boolean columnChk, String appendType, String tbPrefixType, String tbPrefix, String fdPrefixType, String fdPrefix) {
        Map<String, String> entityMap = RegexUtils.parseOriginSql(SQL);
        for (String key : entityMap.keySet()) {
            final ClassEntity classEntity = DBUtils.getClassEntity(key, tbPrefixType, tbPrefix);
            final List<FieldEntity> fieldEntities = DBUtils.getFieldEntities(entityMap.get(key), fdPrefixType,
                    fdPrefix);
            return EntityBuilder.buildEntityStr(classEntity, fieldEntities, columnChk, appendType);
        }
        return "";
    }

    public static String buildExampleMapper(String tbPrefixType, String tbPrefix, String fdPrefixType, String fdPrefix) {
        Map<String, String> entityMap = RegexUtils.parseOriginSql(SQL);
        for (String key : entityMap.keySet()) {
            final ClassEntity classEntity = DBUtils.getClassEntity(key, tbPrefixType, tbPrefix);
            final List<FieldEntity> fieldEntities = DBUtils.getFieldEntities(entityMap.get(key), fdPrefixType,
                    fdPrefix);
            return MapperBuilder.buildMapperStr(classEntity, fieldEntities);
        }
        return "";
    }
}
