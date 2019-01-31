package org.codeg.intellij.builder;

import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Config;
import org.codeg.intellij.config.Constants;
import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.util.FileUtils;
import org.codeg.intellij.util.StringUtils;

/**
 * 服务构造器
 * @author liufei
 * @date 2018/12/28 11:17
 */
public class ServiceBuilder {

    /**
     * 生成service文件
     * @param classEntity
     */
    public static void buildServiceFile(ClassEntity classEntity) {
        // 查看目录是否为空
        String servicePath = Cache.getInstant().getServicePath();
        if (StringUtils.isNotBlank(servicePath)) {

            // 接口类处理
            final String serviceStr = Config.getInstant().getMybatisPlusChk() ? Constants.serviceStr : Constants.serviceStr_without_mybatis_plus;
            String content = serviceStr.replaceAll("\\{servicePackage}", Cache.getInstant().getServicePackage()).
                    replaceAll("\\{entityPackage}", Cache.getInstant().getEntityPackage()).
                    replaceAll("\\{className}", classEntity.getClassName());
            final String serviceFilePath = StringUtils.getServiceFilePath(Cache.getInstant().getServicePath(), classEntity.getClassName());
            FileUtils.createFile(serviceFilePath, content);

            // 实现类处理
            String serviceImplStr = Config.getInstant().getMybatisPlusChk() ? Constants.serviceImplStr : Constants.serviceImplStr_without_mybatis_plus;
            String implContent = serviceImplStr.replaceAll("\\{servicePackage}", Cache.getInstant().getServicePackage()).
                    replaceAll("\\{entityPackage}", Cache.getInstant().getEntityPackage()).
                    replaceAll("\\{daoPackage}", Cache.getInstant().getDaoPackage()).
                    replaceAll("\\{className}", classEntity.getClassName());
            final String serviceImplFilePath = StringUtils.getServiceImplFilePath(Cache.getInstant().getServicePath(), classEntity.getClassName());
            FileUtils.createFile(serviceImplFilePath, implContent);
        }
    }

}
