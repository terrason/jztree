/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.servlet.operator;

import org.terramagnet.jztree.JztreeService;
import org.terramagnet.jztree.config.jaxb.WebConfig;
import org.terramagnet.jztree.servlet.Operation;
import org.terramagnet.jztree.servlet.parameter.Option;
import org.terramagnet.jztree.util.JsonUtils;
import java.util.Map;

/**
 *处理异步绑定jztree.
 * <p>处理结果为返回一个JSON字符串，这个JSON对象包含<code>nodes、setting和option</code>三个JSON对象。</p>
 * @author terrason
 */
public class AjaxBindingOperation implements Operation {

    @Override
    public String execute(Option option, Map extParams, JztreeService service, WebConfig config) {
        StringBuilder jsonObject = new StringBuilder();
        jsonObject.append(JsonUtils.LEFT_BIG_BRACKET).append(JsonUtils.NEXT_LINE_SYMBOL);
        String nodes;
        String setting;
        try {
            String treeName = option.getTreeName();
            if (option.isAsync()) {
                nodes = service.jsonPreviewNodes(treeName);
            } else {
                nodes = service.jsonNodes(treeName);
            }
            setting=service.jsonSetting(treeName);
        } catch (Exception ex) {
            nodes = JsonUtils.generateErrorTree(ex.getMessage());
            setting="{}";
        }
        jsonObject.append("nodes:").append(nodes).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("setting:").append(setting).append(JsonUtils.NEXT_LINE_SYMBOL);
        return jsonObject.append(JsonUtils.RIGHT_BIG_BRACKET).toString();
    }
}
