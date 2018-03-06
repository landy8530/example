package com.yaml.setting;

import org.junit.Assert;
import org.junit.Test;

public class SettingsFileConfigTest {

    @Test
    public void testDefaultConfigPath() {
        SettingsFileConfig settingsFileConfig = new SettingsFileConfig();
        Assert.assertEquals("classpath*:config/*.yml", settingsFileConfig.getConfigPath());
        settingsFileConfig.setConfigPath("/data/bov2/config/");
        Assert.assertEquals("/data/bov2/config/", settingsFileConfig.getConfigPath());
    }
}
