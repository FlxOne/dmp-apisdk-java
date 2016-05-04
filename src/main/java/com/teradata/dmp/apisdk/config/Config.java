package com.teradata.dmp.apisdk.config;

public class Config extends AbstractConfig implements IConfig {
    public static IConfig getDefault() {
        IConfig conf = new Config();
        conf.setEndpoint("https://platform.flxone.com/api/v2");
        conf.setMaxRetries(5);
        conf.setHMACEnabled(false);
        return conf;
    }
}
