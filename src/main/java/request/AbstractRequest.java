package request;

import java.util.HashMap;

public class AbstractRequest implements IRequest {
    protected HashMap<String, String> parameters = new HashMap<String, String>();

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters.putAll(parameters);
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }
}
