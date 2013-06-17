package org.terramagnet.jztree.config.jaxb;

import java.util.Map;

public class ServletConfig {

    protected String userObjectSessionKey;
    protected String namespace;
    protected String urlSuffix;
    protected Map<String, String> operation;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Map<String, String> getOperation() {
        return operation;
    }

    public void setOperation(Map<String, String> operation) {
        this.operation = operation;
    }

    public String getUrlSuffix() {
        return urlSuffix;
    }

    public void setUrlSuffix(String urlSuffix) {
        this.urlSuffix = urlSuffix;
    }

    public String getUserObjectSessionKey() {
        return userObjectSessionKey;
    }

    public void setUserObjectSessionKey(String userObjectSessionKey) {
        this.userObjectSessionKey = userObjectSessionKey;
    }
}
