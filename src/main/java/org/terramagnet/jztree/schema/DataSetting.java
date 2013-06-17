/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.schema;

/**
 * 默认data配置信息.
 *
 * <p>包含属性如下：（<span style="color:#888888">属性名——默认值</span>）</p>
 *
 * <ul>
 *
 * <li>keep——{@link KeepSetting}</li>
 * <li>simpleData——{@link SimpleDataSetting}</li>
 *
 * </ul>
 *
 * @author terrason
 */
public class DataSetting {

    private KeepSetting keep = new KeepSetting();
    private SimpleDataSetting simpleData = new SimpleDataSetting();

    public KeepSetting getKeep() {
        return keep;
    }

    public void setKeep(KeepSetting keep) {
        this.keep = keep;
    }

    public SimpleDataSetting getSimpleData() {
        return simpleData;
    }

    public void setSimpleData(SimpleDataSetting simpleData) {
        this.simpleData = simpleData;
    }
}
