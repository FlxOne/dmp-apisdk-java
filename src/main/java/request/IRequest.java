package request;

import java.util.HashMap;

public interface IRequest {
    void setParameter(String key, String value);
    void setParameters(HashMap<String, String> parameters);
    HashMap<String, String> getParameters();
    String getService();
}
