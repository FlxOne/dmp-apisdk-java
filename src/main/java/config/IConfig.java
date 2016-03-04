package config;

public interface  IConfig {
    String getEndpoint();
    String getUsername();
    String getPassword();
    void setEndpoint(String endpoint);
    void setCredentials(String user, String password);
}
