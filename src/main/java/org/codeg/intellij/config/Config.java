package org.codeg.intellij.config;

import com.intellij.ide.util.PropertiesComponent;
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

    public void save() {
        PropertiesComponent.getInstance().setValue("appendType", appendType);
        PropertiesComponent.getInstance().setValue("tbPrefixType", tbPrefixType);
        PropertiesComponent.getInstance().setValue("tbPrefix", tbPrefix);
        PropertiesComponent.getInstance().setValue("fdPrefixType", fdPrefixType);
        PropertiesComponent.getInstance().setValue("fdPrefix", fdPrefix);
    }

    public static Config getInstant() {
        if (Objects.isNull(config)) {
            config = new Config();
            config.setAppendType(PropertiesComponent.getInstance().getValue("appendType", StringUtils.EMPTY));
            config.setTbPrefixType(PropertiesComponent.getInstance().getValue("tbPrefixType", PrefixType.DEL.name()));
            config.setTbPrefix(PropertiesComponent.getInstance().getValue("tbPrefix", StringUtils.EMPTY));
            config.setFdPrefixType(PropertiesComponent.getInstance().getValue("fdPrefixType", PrefixType.STET.name()));
            config.setFdPrefix(PropertiesComponent.getInstance().getValue("fdPrefix", StringUtils.EMPTY));
        }
        return config;
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
}
