/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.schema;

/**
 * 默认zTree setting配置信息.
 *
 * <p>包含属性如下：（<span style="color:#888888">属性名——默认值</span>）</p>
 *
 * <ul>
 *
 * <li>data——{@link DataSetting}</li> <li>view——{@link ViewSetting}</li>
 *
 * </ul>
 *
 * @author terrason
 */
public class TreeSetting {

    private DataSetting data = new DataSetting();
    private ViewSetting view = new ViewSetting();

    public DataSetting getData() {
        return data;
    }

    public void setData(DataSetting data) {
        this.data = data;
    }

    public ViewSetting getView() {
        return view;
    }

    public void setView(ViewSetting view) {
        this.view = view;
    }
}
