package org.terramagnet.jztree.config.jaxb;

import java.util.Map;

public class TreeConfig {

    protected Map<String, Tree> tree;

    public Map<String, Tree> getTree() {
        return tree;
    }

    public void setTree(Map<String, Tree> tree) {
        this.tree = tree;
    }
}
