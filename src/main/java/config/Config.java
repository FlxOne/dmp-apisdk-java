package config;

public class Config extends AbstractConfig implements IConfig {
    public static IConfig getDefault() {
        IConfig conf = new Config();
        conf.setEndpoint("https://platform.flxone.com/api/v2");
        return conf;
    }
}
