package org.codeg.intellij.config;

import org.codeg.intellij.util.StringUtils;
import com.intellij.ide.util.PropertiesComponent;

import java.util.Objects;

/**
 *
 * @author liufei
 * @date 2018/12/26 17:10
 */
public class Cache {
    private static Cache cache;

    private String servicePath;
    private String servicePackage;
    private String daoPath;
    private String daoPackage;
    private String entityPath;
    private String entityPackage;
    private String mapperPath;

    public void save() {
        PropertiesComponent.getInstance().setValue("servicePath", "" + servicePath);
        PropertiesComponent.getInstance().setValue("servicePackage", "" + servicePackage);
        PropertiesComponent.getInstance().setValue("daoPath", "" + daoPath);
        PropertiesComponent.getInstance().setValue("daoPackage", "" + daoPackage);
        PropertiesComponent.getInstance().setValue("entityPath", "" + entityPath);
        PropertiesComponent.getInstance().setValue("entityPackage", "" + entityPackage);
        PropertiesComponent.getInstance().setValue("mapperPath", "" + mapperPath);
    }

    public static Cache getInstant() {
        if (Objects.isNull(cache)) {
            cache = new Cache();
            cache.setServicePath(PropertiesComponent.getInstance().getValue("servicePath", StringUtils.EMPTY));
            cache.setServicePackage(PropertiesComponent.getInstance().getValue("servicePackage"));
            cache.setDaoPath(PropertiesComponent.getInstance().getValue("daoPath", StringUtils.EMPTY));
            cache.setDaoPackage(PropertiesComponent.getInstance().getValue("daoPackage"));
            cache.setEntityPath(PropertiesComponent.getInstance().getValue("entityPath", StringUtils.EMPTY));
            cache.setEntityPackage(PropertiesComponent.getInstance().getValue("entityPackage"));
            cache.setMapperPath(PropertiesComponent.getInstance().getValue("mapperPath", StringUtils.EMPTY));
        }
        return cache;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
        // 处理路径为 src 下的 package
        setServicePackage(StringUtils.getPackageBySrcPath(servicePath));
    }

    public String getDaoPath() {
        return daoPath;
    }

    public void setDaoPath(String daoPath) {
        this.daoPath = daoPath;
        setDaoPackage(StringUtils.getPackageBySrcPath(daoPath));
    }

    public String getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(String entityPath) {
        this.entityPath = entityPath;
        setEntityPackage(StringUtils.getPackageBySrcPath(entityPath));
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }
}
