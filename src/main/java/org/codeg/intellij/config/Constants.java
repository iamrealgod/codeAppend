package org.codeg.intellij.config;

import java.util.regex.Pattern;

/**
 *
 * @author liufei
 * @date 2018/12/28 10:44
 */
public class Constants {
    public final static Pattern TABLE_REGEX = Pattern.compile("CREATE TABLE `(.*?)` \\((.*?) PRIMARY");
    public final static Pattern FIELD_REGEX = Pattern.compile("`(.+?)` (.+?) .*?('(.*?)')?,");
    public final static Pattern APPEND_ENTITY_FIELD_REGEX = Pattern.compile("private .*? (.*?;.*)");
    public final static Pattern APPEND_MAPPER_RESULT_REGEX = Pattern.compile("<resultMap [\\s\\S]*?</resultMap>");
    public final static Pattern APPEND_MAPPER_RESULT_REGEX2 = Pattern.compile("<result .*? .*?(=\".*?\"/>)");
    public final static Pattern APPEND_MAPPER_FIELDS_REGEX = Pattern.compile("<sql .*?>([\\s\\S]+)</sql>");
    public final static Pattern PACKAGE_JAVA_REGEX = Pattern.compile("java[\\\\|/](.*?[\\\\|/])");
    public final static Pattern PACKAGE_SRC_REGEX = Pattern.compile("src[\\\\|/](.*?[\\\\|/])");
    public static final String UNDER_LINE = "_";
    public final  static int MAX_MATCHER = 1000;
    public static final String SRC_DIR_NAME = "src";
    public static final String ID = "id";
    public static final String IMPL_DIR_NAME = "impl";
    public static final String JAVA_SUFFIX = ".java";
    public static final String MAPPER_SUFFIX = ".xml";
    public static final String SERVICE_NAME = "Service";
    public static final String SERVICE_IMPL_NAME = "ServiceImpl";
    public static final String DAO_NAME = "Dao";
    public static final String MAPPER_NAME = "Mapper";
    public static final String DEFAULT_PACKAGE = "com.codeg";

    /**
     * 数据库字段类型
     */
    public static final CharSequence MYBATIS_TINYINT = "tinyint";
    public static final CharSequence MYBATIS_CHAR = "char";
    public static final CharSequence MYBATIS_BIGINT = "bigint";
    public static final CharSequence MYBATIS_INT = "int";
    public static final CharSequence MYBATIS_DATETIME = "datetime";
    public static final CharSequence MYBATIS_DECIMAL = "decimal";
    public static final CharSequence MYBATIS_TEXT = "text";
    public static final CharSequence MYBATIS_DOUBLE = "double";

    /**
     * entity
     */
    public final static String entityStr = "package {entityPackage};\n" + "\n"
            + "import com.baomidou.mybatisplus.annotations.TableField;\n"
            + "import com.baomidou.mybatisplus.annotations.TableId;\n"
            + "import com.baomidou.mybatisplus.annotations.TableName;\n"
            + "import com.baomidou.mybatisplus.enums.IdType;\n" + "import lombok.Data;\n" + "\n"
            + "import java.util.Date;\n" + "\n" + "@Data\n" + "@TableName(\"{tableName}\")\n"
            + "public class {className} {\n" + "{fields}\n" + "\n" + "}";

    public final static String entityFieldIdStr = "\t@TableId(value = \"id\", type = IdType.AUTO)\n"
            + "\tprivate Long id;\t// PRIMARY KEY\n";

    public final static String entityFieldStr = "\t@TableField(\"{column}\")\n"
            + "\tprivate {propertyType} {property};\t//{comment}\n";

    /**
     * service
     */
    public final static String serviceStr = "package {servicePackage};\n" + "\n"
            + "import com.baomidou.mybatisplus.service.IService;\n" + "import {entityPackage}.{className};\n" + "\n"
            + "/**\n" + " *  Auto created by codeg plugin\n" + " */\n"
            + "public interface {className}Service extends IService<{className}> {\n" + "\n" + "}";

    public final static String serviceImplStr = "package {servicePackage}.impl;\n" + "\n"
            + "import com.baomidou.mybatisplus.service.impl.ServiceImpl;\n" + "import {entityPackage}.{className};\n"
            + "import {daoPackage}.{className}Dao;\n" + "import {servicePackage}.{className}Service;\n"
            + "import lombok.extern.slf4j.Slf4j;\n" + "import org.springframework.stereotype.Service;\n" + "\n"
            + "/**\n" + " * Auto created by codeg plugin\n" + " */\n" + "@Slf4j\n" + "@Service\n"
            + "public class {className}ServiceImpl extends ServiceImpl<{className}Dao, {className}> implements "
            + "{className}Service {\n"
            + "\n" + "    // use \"baseMapper\" to call jdbc\n" + "    // example: baseMapper.insert(entity);\n"
            + "    // example: baseMapper.selectByPage(params);\n" + "   \n" + "}";

    /**
     * dao
     */
    public final static String daoStr = "package {daoPackage};\n" + "\n"
            + "import com.baomidou.mybatisplus.mapper.BaseMapper;\n" + "import {entityPackage}.{className};\n" + "\n"
            + "/**\n" + " * Auto created by codeg plugin\n" + " */\n"
            + "public interface {className}Dao extends BaseMapper<{className}> {\n" + "\n" + "}";

    /**
     * mapper
     */
    public final static String mapperStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + "<!DOCTYPE mapper\n"
            + "        PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\"\n"
            + "        \"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">\n" + "\n"
            + "<!-- Auto created by codeg plugin -->\n" + "<mapper namespace=\"{daoPackage}.{className}Dao\">\n" + "\n"
            + "    <resultMap id=\"{className}RM\" type=\"{entityPackage}.{className}\">\n"
            + "{resultFields}\n" + "    </resultMap>\n" + "\n" + "    <!-- all sql columns -->\n"
            + "    <sql id=\"columns\">\n" + "         {columns}\n" + "\t</sql>\n" + "\n" + "</mapper>";

    public final static String mapperResultStr = "\t\t<result property=\"{property}\" column=\"{column}\"/>\n";
    public final static String mapperColumnsStr = "`{column}`,";

}
