package org.codeg.intellij.util;

import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Constants;

import java.util.Objects;

/**
 *
 * @author liufei
 * @date 2018/12/28 18:10
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{
    public static final String EMPTY = "";
    /**
     * 获取路径包含src的package
     * @param path
     * @return
     */
    public static String getPackageBySrcPath(String path) {
        String packagePath;
        if (isNotBlank(path) && path.contains(Constants.SRC_DIR_NAME)) {
            String packagePrefix = RegexUtils.getPackagePrefix(path);
            if (Objects.nonNull(packagePrefix)) {
                packagePath = path.substring(path.indexOf(packagePrefix));
                return packagePath.replaceAll("/", "\\.").replaceAll("\\\\", "\\.");
            }
        }
        return Constants.DEFAULT_PACKAGE;
    }

    public static String separator() {
        if (Cache.getInstant().getEntityPath().contains("/")) {
            return "/";
        }
        return "\\";
    }

    public static String getEntityFilePath(String path, String className) {
        return path.concat(StringUtils.separator()).concat(className).concat(Constants.JAVA_SUFFIX);
    }

    public static String getServiceFilePath(String path, String className) {
        return path.concat(StringUtils.separator()).concat(className).concat(Constants.SERVICE_NAME).concat(Constants.JAVA_SUFFIX);
    }

    public static String getServiceImplFilePath(String path, String className) {
        final String implDir = path.concat(separator()).concat(Constants.IMPL_DIR_NAME);
        FileUtils.generateDir(implDir);
        return implDir.concat(separator())
                .concat(className).concat(Constants.SERVICE_IMPL_NAME).concat(Constants.JAVA_SUFFIX);
    }

    public static String getDaoFilePath(String path, String className) {
        return path.concat(StringUtils.separator()).concat(className).concat(Constants.DAO_NAME).concat(Constants.JAVA_SUFFIX);
    }

    public static String getMapperFilePath(String path, String className) {
        return path.concat(StringUtils.separator()).concat(className).concat(Constants.MAPPER_NAME).concat(Constants.MAPPER_SUFFIX);
    }

    public static String entityProperty(String property) {
        return property + ";";
    }

    public static String mapperColumn(String column) {
        return "column=\""+column+"\"";
    }
}
