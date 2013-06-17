/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.schema;

/**
 * 默认data.keep配置信息.
 *
 * <p>包含属性如下：（<span style="color:#888888">属性名——默认值</span>）</p>
 *
 * <ul>
 *
 * <li>leaf——{@code true}</li> <li>parent——{@code true}</li>
 *
 * </ul>
 *
 * @author terrason
 */
public class KeepSetting {

    private boolean leaf = true;
    private boolean parent = true;

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }
}
