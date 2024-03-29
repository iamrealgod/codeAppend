package org.codeg.intellij.config;

import com.intellij.ide.util.PropertiesComponent;
import org.codeg.intellij.config.Enums.AppendType;
import org.codeg.intellij.config.Enums.PrefixType;
import org.codeg.intellij.util.StringUtils;

import java.util.Objects;

/**
 *
 * @author liufei
 * @date 2018/12/26 17:08
 */
public class Config {
    private static Config config;

    private String appendType;
    private String tbPrefixType;
    private String tbPrefix;
    private String fdPrefixType;
    private String fdPrefix;
    private boolean columnChk;
    private boolean mybatisPlusChk;
    private boolean localDateChk;
    private boolean builderChk;
    private String mapperSuffix;

    public void save() {
        PropertiesComponent.getInstance().setValue("appendType", appendType);
        PropertiesComponent.getInstance().setValue("tbPrefixType", tbPrefixType);
        PropertiesComponent.getInstance().setValue("tbPrefix", tbPrefix);
        PropertiesComponent.getInstance().setValue("fdPrefixType", fdPrefixType);
        PropertiesComponent.getInstance().setValue("fdPrefix", fdPrefix);
        PropertiesComponent.getInstance().setValue("columnChk", columnChk);
        PropertiesComponent.getInstance().setValue("mybatisPlusChk", mybatisPlusChk);
        PropertiesComponent.getInstance().setValue("mapperSuffix", mapperSuffix);
        PropertiesComponent.getInstance().setValue("localDateChk", localDateChk);
        PropertiesComponent.getInstance().setValue("builderChk", builderChk);
    }

    public static Config getInstant() {
        if (Objects.isNull(config)) {
            config = new Config();
            config.setAppendType(PropertiesComponent.getInstance().getValue("appendType", AppendType.LOMBOK.name()));
            config.setTbPrefixType(PropertiesComponent.getInstance().getValue("tbPrefixType", PrefixType.DEL.name()));
            config.setTbPrefix(PropertiesComponent.getInstance().getValue("tbPrefix", StringUtils.EMPTY));
            config.setFdPrefixType(PropertiesComponent.getInstance().getValue("fdPrefixType", PrefixType.STET.name()));
            config.setFdPrefix(PropertiesComponent.getInstance().getValue("fdPrefix", StringUtils.EMPTY));
            config.setColumnChk(PropertiesComponent.getInstance().getBoolean("columnChk", true));
            config.setMybatisPlusChk(PropertiesComponent.getInstance().getBoolean("mybatisPlusChk", true));
            config.setMybatisPlusChk(PropertiesComponent.getInstance().getBoolean("localDateChk", false));
            config.setMybatisPlusChk(PropertiesComponent.getInstance().getBoolean("builderChk", false));
            config.setMapperSuffix(PropertiesComponent.getInstance().getValue("mapperSuffix", Constants.MAPPER_NAME));
        }
        return config;
    }

    public static void reset() {
        if (Objects.isNull(config)) {
            config = new Config();
        }
        config.setAppendType(AppendType.LOMBOK.name());
        config.setTbPrefixType(PrefixType.DEL.name());
        config.setTbPrefix(StringUtils.EMPTY);
        config.setFdPrefixType(PrefixType.STET.name());
        config.setFdPrefix(StringUtils.EMPTY);
        config.setColumnChk(true);
        config.setMybatisPlusChk(true);
        config.setLocalDateChk(false);
        config.setBuilderChk(false);
        config.setMapperSuffix(Constants.MAPPER_NAME);
        config.save();
    }

    public String getMapperSuffix() {
        return mapperSuffix;
    }

    public void setMapperSuffix(String mapperSuffix) {
        this.mapperSuffix = mapperSuffix;
    }

    public boolean getMybatisPlusChk() {
        return mybatisPlusChk;
    }

    public void setMybatisPlusChk(boolean mybatisPlusChk) {
        this.mybatisPlusChk = mybatisPlusChk;
    }

    public String getAppendType() {
        return appendType;
    }

    public void setAppendType(String appendType) {
        this.appendType = appendType;
    }

    public String getTbPrefixType() {
        return tbPrefixType;
    }

    public void setTbPrefixType(String tbPrefixType) {
        this.tbPrefixType = tbPrefixType;
    }

    public String getTbPrefix() {
        return tbPrefix;
    }

    public void setTbPrefix(String tbPrefix) {
        this.tbPrefix = tbPrefix;
    }

    public String getFdPrefixType() {
        return fdPrefixType;
    }

    public void setFdPrefixType(String fdPrefixType) {
        this.fdPrefixType = fdPrefixType;
    }

    public String getFdPrefix() {
        return fdPrefix;
    }

    public void setFdPrefix(String fdPrefix) {
        this.fdPrefix = fdPrefix;
    }

    public boolean getColumnChk() {
        return columnChk;
    }

    public void setColumnChk(boolean columnChk) {
        this.columnChk = columnChk;
    }


    public boolean isLocalDateChk() {
        return localDateChk;
    }

    public void setLocalDateChk(boolean localDateChk) {
        this.localDateChk = localDateChk;
    }

    public boolean isBuilderChk() {
        return builderChk;
    }

    public void setBuilderChk(boolean builderChk) {
        this.builderChk = builderChk;
    }
}
