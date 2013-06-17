package org.terramagnet.jztree.config.jaxb;

/**
 * 对应<cite>jztree-config.xml</cite>中的配置内容.
 */
public class JztreeConfig {

    protected WebConfig webConfig;
    protected ServletConfig servletConfig;
    protected TreeConfig treeConfig;

    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    public TreeConfig getTreeConfig() {
        return treeConfig;
    }

    public void setTreeConfig(TreeConfig treeConfig) {
        this.treeConfig = treeConfig;
    }

    public WebConfig getWebConfig() {
        return webConfig;
    }

    public void setWebConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }
}
