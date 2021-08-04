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


    public static final String DEMO_SQL = "/*\n" +
            " Sql Example:\n" +
            "*/\n" +
            "\n" +
            "SET FOREIGN_KEY_CHECKS=0;\n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Table structure for pre_test\n" +
            "-- ----------------------------\n" +
            "DROP TABLE IF EXISTS `pre_test`;\n" +
            "CREATE TABLE `pre_test` (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
            "  `num_no` varchar(64) NOT NULL COMMENT '订单号',\n" +
            "  `fu_address` varchar(32) DEFAULT NULL COMMENT '详细地址',\n" +
            "  `longitude` varchar(16) DEFAULT NULL COMMENT '地址经度',\n" +
            "  `latitude` varchar(16) DEFAULT NULL COMMENT '地址纬度',\n" +
            "  `create_user_id` bigint(20) NOT NULL COMMENT '创建人id',\n" +
            "  `create_time` datetime NOT NULL COMMENT '创建时间',\n" +
            "  `update_user_id` bigint(20) NOT NULL COMMENT '更新人id',\n" +
            "  `update_time` datetime NOT NULL COMMENT '更新时间',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  KEY `idx_create_time` (`create_time`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地址表';\n" +
            "\n" +
            "-- ----------------------------\n" +
            "-- Table structure for pre_test1\n" +
            "-- ----------------------------\n" +
            "DROP TABLE IF EXISTS `pre_test1`;\n" +
            "CREATE TABLE `pre_test2` (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
            "  `bill_no` varchar(64) NOT NULL,\n" +
            "  `attach_type` varchar(32) NOT NULL COMMENT '附件类型',\n" +
            "  `store_id` varchar(128) NOT NULL COMMENT '存储id',\n" +
            "  `create_time` datetime NOT NULL COMMENT '创建时间',\n" +
            "  `deleted` tinyint(4) NOT NULL COMMENT '是否删除(0-否、1-是）',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  KEY `idx_store_id` (`store_id`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';";

    public static String buildExampleEntityContent(boolean columnChk, String appendType, String tbPrefixType, String tbPrefix, String fdPrefixType, String fdPrefix, boolean builderChk, boolean localDateChk) {
        Map<String, String> entityMap = RegexUtils.parseOriginSql(SQL);
        for (String key : entityMap.keySet()) {
            final ClassEntity classEntity = DBUtils.getClassEntity(key, tbPrefixType, tbPrefix);
            final List<FieldEntity> fieldEntities = DBUtils.getFieldEntities(entityMap.get(key), fdPrefixType, fdPrefix, localDateChk);
            return EntityBuilder.buildEntityStr(classEntity, fieldEntities, columnChk, appendType, builderChk);
        }
        return "";
    }

    public static String buildExampleMapperContent(String tbPrefixType, String tbPrefix, String fdPrefixType, String fdPrefix) {
        Map<String, String> entityMap = RegexUtils.parseOriginSql(SQL);
        for (String key : entityMap.keySet()) {
            final ClassEntity classEntity = DBUtils.getClassEntity(key, tbPrefixType, tbPrefix);
            final List<FieldEntity> fieldEntities = DBUtils.getFieldEntities(entityMap.get(key), fdPrefixType, fdPrefix);
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
