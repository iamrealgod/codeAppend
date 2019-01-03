package org.codeg.intellij.builder;

import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Constants;
import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.util.FileUtils;
import org.codeg.intellij.util.StringUtils;

/**
 * jdbc构造器
 * @author liufei
 * @date 2018/12/28 11:17
 */
public class DaoBuilder {

    /**
     * 生成dao文件
     * @param classEntity
     */
    public static void buildDaoFile(ClassEntity classEntity) {
        // 查看目录是否为空
        String daoPath = Cache.getInstant().getDaoPath();
        if (StringUtils.isNotBlank(daoPath)) {
            // 接口类处理
            String content = Constants.daoStr.replaceAll("\\{daoPackage}", Cache.getInstant().getDaoPackage()).
                    replaceAll("\\{entityPackage}", Cache.getInstant().getEntityPackage()).
                    replaceAll("\\{className}", classEntity.getClassName());
            final String daoFilePath = StringUtils.getDaoFilePath(Cache.getInstant().getDaoPath(), classEntity.getClassName());
            FileUtils.createFile(daoFilePath, content);
        }
    }
}
