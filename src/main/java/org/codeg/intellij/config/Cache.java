package org.codeg.intellij.config;

import org.codeg.intellij.util.StringUtils;
import com.intellij.ide.util.PropertiesComponent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private Set<String> mapperPathItem;
    private Set<String> entityPathItem;
    private Set<String> daoPathItem;
    private Set<String> servicePathItem;

    public void save() {
        PropertiesComponent.getInstance().setValue("servicePath", "" + servicePath);
        PropertiesComponent.getInstance().setValue("servicePackage", "" + servicePackage);
        PropertiesComponent.getInstance().setValue("daoPath", "" + daoPath);
        PropertiesComponent.getInstance().setValue("daoPackage", "" + daoPackage);
        PropertiesComponent.getInstance().setValue("entityPath", "" + entityPath);
        PropertiesComponent.getInstance().setValue("entityPackage", "" + entityPackage);
        PropertiesComponent.getInstance().setValue("mapperPath", "" + mapperPath);

        PropertiesComponent.getInstance().setValue("mapperPathItem", StringUtils.join(mapperPathItem, ","));
        PropertiesComponent.getInstance().setValue("entityPathItem", StringUtils.join(entityPathItem, ","));
        PropertiesComponent.getInstance().setValue("daoPathItem", StringUtils.join(daoPathItem, ","));
        PropertiesComponent.getInstance().setValue("servicePathItem", StringUtils.join(servicePathItem, ","));
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


            cache.addEntityPathItem(PropertiesComponent.getInstance().getValue("entityPathItem", StringUtils.EMPTY).split(","));
            cache.addMapperPathItem(PropertiesComponent.getInstance().getValue("mapperPathItem", StringUtils.EMPTY).split(","));
            cache.addDaoPathItem(PropertiesComponent.getInstance().getValue("daoPathItem", StringUtils.EMPTY).split(","));
            cache.addServicePathItem(PropertiesComponent.getInstance().getValue("servicePathItem", StringUtils.EMPTY).split(","));
        }
        return cache;
    }

    public static void reset() {
        if (Objects.isNull(cache)) {
            cache = new Cache();
        }
        cache.setServicePath(StringUtils.EMPTY);
        cache.setServicePackage(StringUtils.EMPTY);
        cache.setDaoPath(StringUtils.EMPTY);
        cache.setDaoPackage(StringUtils.EMPTY);
        cache.setEntityPath(StringUtils.EMPTY);
        cache.setEntityPackage(StringUtils.EMPTY);
        cache.setMapperPath(StringUtils.EMPTY);
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

    public Set<String> getMapperPathItem() {
        return mapperPathItem;
    }

    public Set<String> getEntityPathItem() {
        return entityPathItem;
    }

    public Set<String> getDaoPathItem() {
        return daoPathItem;
    }

    public Set<String> getServicePathItem() {
        return servicePathItem;
    }

    public void addMapperPathItem(String... items) {
        if (Objects.isNull(mapperPathItem)) {
            mapperPathItem = new HashSet<>();
        }
        mapperPathItem.addAll(Arrays.stream(items).filter(StringUtils::isNotBlank).collect(Collectors.toSet()));

    }

    public void addEntityPathItem(String... items) {
        if (Objects.isNull(entityPathItem)) {
            entityPathItem = new HashSet<>();
        }
        entityPathItem.addAll(Arrays.stream(items).filter(StringUtils::isNotBlank).collect(Collectors.toSet()));
    }

    public void addDaoPathItem(String... items) {
        if (Objects.isNull(daoPathItem)) {
            daoPathItem = new HashSet<>();
        }
        daoPathItem.addAll(Arrays.stream(items).filter(StringUtils::isNotBlank).collect(Collectors.toSet()));
    }

    public void addServicePathItem(String... items) {
        if (Objects.isNull(servicePathItem)) {
            servicePathItem = new HashSet<>();
        }
        servicePathItem.addAll(Arrays.stream(items).filter(StringUtils::isNotBlank).collect(Collectors.toSet()));
    }

    public void remoteItem(String item, String comboBoxPosition) {
        try {
            if (comboBoxPosition.equals("mapperPathItem")) {
                mapperPathItem.remove(item);
            }
            if (comboBoxPosition.equals("entityPathItem")) {
                entityPathItem.remove(item);
            }
            if (comboBoxPosition.equals("daoPathItem")) {
                daoPathItem.remove(item);
            }
            if (comboBoxPosition.equals("servicePathItem")) {
                servicePathItem.remove(item);
            }
        } catch (Exception e) {

        }
    }
}
