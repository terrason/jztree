package org.terramagnet.jztree.config.jaxb;

import java.util.Map;

public class WebConfig {

    protected String namespace;
    protected String jqueryUrl;
    protected String jsPackage;
    protected String cssPackage;
    protected Map<String, String> page;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getCssPackage() {
        return cssPackage;
    }

    public void setCssPackage(String cssPackage) {
        this.cssPackage = cssPackage;
    }

    public String getJqueryUrl() {
        return jqueryUrl;
    }

    public void setJqueryUrl(String jqueryUrl) {
        this.jqueryUrl = jqueryUrl;
    }

    public String getJsPackage() {
        return jsPackage;
    }

    public void setJsPackage(String jsPackage) {
        this.jsPackage = jsPackage;
    }

    public Map<String, String> getPage() {
        return page;
    }

    public void setPage(Map<String, String> page) {
        this.page = page;
    }
}
