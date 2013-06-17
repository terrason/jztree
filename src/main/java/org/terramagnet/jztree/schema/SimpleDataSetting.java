/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.schema;

import org.terramagnet.jztree.Jztree;

/**
 * 默认data.simpleData配置信息.
 *
 * <p>包含属性如下：（<span style="color:#888888">属性名——默认值</span>）</p>
 *
 * <ul>
 *
 * <li>enable——{@code true}</li>
 *
 * <li>idKey——{@code "id"}</li>
 *
 * <li>pIdKey——{@code "parentId"}</li>
 *
 * <li>rootPId——{@code "0"}</li>
 *
 * </ul>
 *
 * @author terrason
 */
public class SimpleDataSetting {

    private boolean enable = true;
    private String idKey = "id";
    private String pIdKey = "parentId";
    private String rootPId = Jztree.DEFAULT_ROOT_PID;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getpIdKey() {
        return pIdKey;
    }

    public void setpIdKey(String pIdKey) {
        this.pIdKey = pIdKey;
    }

    public String getRootPId() {
        return rootPId;
    }

    public void setRootPId(String rootPId) {
        this.rootPId = rootPId;
    }
}
