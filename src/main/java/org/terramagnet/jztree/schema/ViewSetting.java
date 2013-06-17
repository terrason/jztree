/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.schema;

/**
 * 默认view配置信息.
 *
 * <p>包含属性如下：（<span style="color:#888888">属性名——默认值</span>）</p>
 *
 * <ul>
 *
 * <li>expandSpeed——{@code "($.browser.msie && Number($.browser.version)>=6)?\"\":\"fast\""}</li>
 *
 * </ul>
 *
 * @author terrason
 */
public class ViewSetting {

    private String expandSpeed = "fast";
    private Boolean showIcon;
    private Boolean showLine;

    public String getExpandSpeed() {
        return expandSpeed;
    }

    public void setExpandSpeed(String expandSpeed) {
        this.expandSpeed = expandSpeed;
    }

    public Boolean getShowIcon() {
        return showIcon;
    }

    public void setShowIcon(Boolean showIcon) {
        this.showIcon = showIcon;
    }

    public Boolean getShowLine() {
        return showLine;
    }

    public void setShowLine(Boolean showLine) {
        this.showLine = showLine;
    }
}
