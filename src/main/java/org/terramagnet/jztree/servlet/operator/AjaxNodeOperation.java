/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.servlet.operator;

import org.terramagnet.jztree.JztreeService;
import org.terramagnet.jztree.config.jaxb.WebConfig;
import org.terramagnet.jztree.servlet.Operation;
import org.terramagnet.jztree.servlet.parameter.Option;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author terrason
 */
public class AjaxNodeOperation implements Operation {

    private static final Log logger = LogFactory.getLog(AjaxNodeOperation.class);
    //-----------------------------------------------------------------------------------------
    private final PropertyUtilsBean beanUtil = new PropertyUtilsBean();

    @Override
    public String execute(Option option, Map extParams, JztreeService service, WebConfig config) {
        String treeName = option.getTreeName();
        String parentId = null;
        String[] t = (String[]) extParams.get("parentId");
        if (t != null && t.length > 0) {
            parentId = t[0];
        } else {
            try {
                Object setting = service.instanceTree(treeName).getSetting();
                parentId = beanUtil.getNestedProperty(setting, "data.simpleData.rootPId").toString();
            } catch (Exception e) {
                parentId = "0";
            }
        }
        String nodes;
        try {
            nodes = service.jsonChildNodes(treeName, parentId);
        } catch (Exception ex) {
            logger.warn("异步加载节点数据出现异常！", ex);
            nodes = "[]";
        }
        return nodes;
    }
}
