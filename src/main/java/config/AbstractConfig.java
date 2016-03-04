package config;

public class AbstractConfig implements IConfig {
    protected String endpoint;
    protected String username;
    protected String password;
    protected int maxRetries;

    public String getEndpoint() {
        return this.endpoint;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getMaxRetries() { return this.maxRetries; }

    public void setMaxRetries(int n) {
        this.maxRetries = n;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setCredentials(String username, String password) {
        this.setPassword(password);
        this.setUsername(username);
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setUsername(String username) {
        this.username = username;
    }
}
