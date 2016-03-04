package config;

public interface  IConfig {
    String getEndpoint();
    String getUsername();
    String getPassword();
    int getMaxRetries();
    void setEndpoint(String endpoint);
    void setCredentials(String user, String password);
    void setMaxRetries(int n);
}
