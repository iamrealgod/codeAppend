package org.codeg.intellij.config;

import java.util.regex.Pattern;

/**
 *
 * @author liufei
 * @date 2018/12/28 10:44
 */
public class Constants {
    public static final  Pattern TABLE_REGEX = Pattern.compile("CREATE TABLE `(.*?)` \\((.*?) PRIMARY");
    public static final Pattern FIELD_REGEX = Pattern.compile("`(.+?)` (.+?) .*?('(.*?)')?,");
    public static final Pattern APPEND_ENTITY_FIELD_REGEX = Pattern.compile("private .*? (.*?;.*)");
    public static final Pattern APPEND_MAPPER_RESULT_REGEX = Pattern.compile("<resultMap [\\s\\S]*?</resultMap>");
    public static final Pattern APPEND_MAPPER_RESULT_REGEX2 = Pattern.compile("<result .*? .*?(=\".*?\"/>)");
    public static final Pattern APPEND_MAPPER_FIELDS_REGEX = Pattern.compile("<sql .*?>([\\s\\S]+)</sql>");
    public static final Pattern PACKAGE_JAVA_REGEX = Pattern.compile("java[\\\\|/](.*?[\\\\|/])");
    public static final Pattern PACKAGE_SRC_REGEX = Pattern.compile("src[\\\\|/](.*?[\\\\|/])");
    public static final String UNDER_LINE = "_";
    public static final int MAX_MATCHER = 1000;
    public static final String SRC_DIR_NAME = "src";
    public static final String ID = "id";
    public static final String IMPL_DIR_NAME = "impl";
    public static final String JAVA_SUFFIX = ".java";
    public static final String XML = ".xml";
    public static final String SERVICE_NAME = "Service";
    public static final String SERVICE_IMPL_NAME = "ServiceImpl";
    public static final String DAO_NAME = "Dao";
    public static final String MAPPER_NAME = "Mapper";
    public static final String DEFAULT_PACKAGE = "com.codeg";
    public static final int LINE_BREAK_NUM = 128;

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
    public static final String entityStr = "package {entityPackage};\n" + "\n"
            + "import com.baomidou.mybatisplus.annotation.TableField;\n"
            + "import com.baomidou.mybatisplus.annotation.TableId;\n"
            + "import com.baomidou.mybatisplus.annotation.TableName;\n"
            + "import com.baomidou.mybatisplus.annotation.IdType;\n" + "{lombokImport}" + "\n"
            + "import java.util.Date;\n" + "\n" + "{lombokData}" + "@TableName(\"{tableName}\")\n"
            + "public class {className} {\n" + "{fields}\n}";
    public static final String entityStr_without_column = "package {entityPackage};\n" + "\n"
            + "{lombokImport}" + "\n"
            + "import java.util.Date;\n" + "\n" + "{lombokData}"
            + "public class {className} {\n" + "{fields}\n}";

    public static final String entityFieldIdStr = "\t@TableId(value = \"id\", type = IdType.AUTO)\n"
            + "\tprivate Long id;\t// PRIMARY KEY\n";
    public static final String entityFieldIdStr_without_column = "\tprivate Long id;\t// PRIMARY KEY\n";

    public static final String entityFieldStr = "\t@TableField(\"{column}\")\n"
            + "\tprivate {propertyType} {property};\t//{comment}\n";
    public static final String entityFieldStr_without_column = "\tprivate {propertyType} {property};    //{comment}\n";
    public static final String entityFieldStr_getset_method = "\tpublic {propertyType} {getProperty}() {\n" + "\t\treturn {property};\n" + "\t}\n" + "\t\n"
            + "\tpublic void {setProperty}({propertyType} {property}) {\n" + "\t\tthis.{property} = {property};\n" + "\t}\n\n";

    /**
     * service
     */
    public static final String serviceStr = "package {servicePackage};\n" + "\n"
            + "import com.baomidou.mybatisplus.extension.service.IService;\n" + "import {entityPackage}.{className};\n" + "\n"
            + "/**\n" + " *  Auto created by codeAppend plugin\n" + " */\n"
            + "public interface {className}Service extends IService<{className}> {\n" + "\n" + "}";
    public static final String serviceStr_without_mybatis_plus = "package {servicePackage};\n" + "\n"
            + "\n"
            + "/**\n" + " *  Auto created by codeAppend plugin\n" + " */\n"
            + "public interface {className}Service {\n" + "\n" + "}";

    public static final String serviceImplStr = "package {servicePackage}.impl;\n" + "\n"
            + "import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\n" + "import {entityPackage}.{className};\n"
            + "import {daoPackage}.{className}Dao;\n" + "import {servicePackage}.{className}Service;\n"
            + "import org.springframework.stereotype.Service;\n" + "\n"
            + "/**\n" + " * Auto created by codeAppend plugin\n" + " */\n" + "@Service\n"
            + "public class {className}ServiceImpl extends ServiceImpl<{className}Dao, {className}> implements "
            + "{className}Service {\n"
            + "\n" + "    // use \"baseMapper\" to call jdbc\n" + "    // example: baseMapper.insert(entity);\n"
            + "    // example: baseMapper.selectByPage(params);\n" + "   \n" + "}";
    public static final String serviceImplStr_without_mybatis_plus = "package {servicePackage}.impl;\n" + "\n"
            + "import {servicePackage}.{className}Service;\n"
            + "import org.springframework.stereotype.Service;\n" + "\n"
            + "/**\n" + " * Auto created by codeAppend plugin\n" + " */\n" + "@Service\n"
            + "public class {className}ServiceImpl implements "
            + "{className}Service {\n"
            + "   \n" + "}";

    /**
     * dao
     */
    public static final String daoStr = "package {daoPackage};\n" + "\n"
            + "import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n" + "import {entityPackage}.{className};\n" + "\n"
            + "/**\n" + " * Auto created by codeAppend plugin\n" + " */\n"
            + "public interface {className}Dao extends BaseMapper<{className}> {\n" + "\n" + "}";
    public static final String daoStr_without_mybatis_plus = "package {daoPackage};\n" + "\n"
            + "/**\n" + " * Auto created by codeAppend plugin\n" + " */\n"
            + "public interface {className}Dao {\n" + "\n" + "}";

    /**
     * mapper
     */
    public static final String mapperStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + "<!DOCTYPE mapper PUBLIC"
            + " \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" + "\n"
            + "<!-- Auto created by codeAppend plugin -->\n" + "<mapper namespace=\"{daoPackage}.{className}Dao\">\n" + "\n"
            + "    <resultMap id=\"{className}RM\" type=\"{entityPackage}.{className}\">"
            + "{resultFields}\n" + "    </resultMap>\n" + "\n" + "    <!-- all sql columns -->\n"
            + "    <sql id=\"columns\">\n" + "         {columns}\n" + "\t</sql>\n" + "\n" + "</mapper>";

    public static final String mapperResultStr = "\n\t\t<result property=\"{property}\" column=\"{column}\"/>";
    public static final String mapperColumnsStr = "`{column}`,";

}
