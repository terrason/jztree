package org.terramagnet.jztree.servlet.parameter;

import org.terramagnet.jztree.util.JsonUtils;

/**
 * Jztree 操作参数. <p>用来封装URL参数信息。提交的参数中没有的会取默认值，全部参数的默认值如下：<br/> <table border="1"
 * style="font-family:courier"> <tr><td>参数名</td><td>默认值</td></tr>
 * <tr><td>treeName</td><td>jztree-tree-demo</td></tr>
 * <tr><td>schemaName</td><td>jztree-scheme-default</td></tr> <tr><td>async</td><td>{@link Boolean false}</td></tr>
 * <tr><td>checkStyle</td><td>none</td></tr>
 * <tr><td>selectPattern</td><td>leaf</td></tr> <tr><td>anchorUrl</td><td>{@code null}</td></tr>
 * <tr><td>anchorTarget</td><td>subframe</td></tr>
 * <tr><td>anchorApply</td><td>both</td></tr> <tr><td>modal</td><td>{@link Boolean false}</td></tr>
 * <tr><td>returnType</td><td>string</td></tr>
 * <tr><td>idInput</td><td>ids</td></tr>
 * <tr><td>nameInput</td><td>names</td></tr>
 * <tr><td>formId</td><td>infoForm</td></tr> <tr><td>autoSubmit</td><td>{@link Boolean false}</td></tr>
 * </table> </p>
 *
 * @author terrason
 */
public class Option {

    private String treeName = "jztree-tree-demo";
    private boolean async = false;
    private String checkStyle = "none";
    private String selectPattern = "leaf";
    private String anchorUrl = null;
    private String anchorTarget = "subframe";
    private String anchorApply = "both";
    private boolean modal = false;
    private String returnType = "string";
    private String idInput = "ids";
    private String nameInput = "names";
    private String formId = "infoForm";
    private boolean autoSubmit = false;

    public String getAnchorApply() {
        return anchorApply;
    }

    public void setAnchorApply(String anchorApply) {
        this.anchorApply = anchorApply;
    }

    public String getAnchorTarget() {
        return anchorTarget;
    }

    public void setAnchorTarget(String anchorTarget) {
        this.anchorTarget = anchorTarget;
    }

    public String getAnchorUrl() {
        return anchorUrl;
    }

    public void setAnchorUrl(String anchorUrl) {
        this.anchorUrl = anchorUrl;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isAutoSubmit() {
        return autoSubmit;
    }

    public void setAutoSubmit(boolean autoSubmit) {
        this.autoSubmit = autoSubmit;
    }

    public String getCheckStyle() {
        return checkStyle;
    }

    public void setCheckStyle(String checkStyle) {
        this.checkStyle = checkStyle;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getIdInput() {
        return idInput;
    }

    public void setIdInput(String idInput) {
        this.idInput = idInput;
    }

    public boolean isModal() {
        return modal;
    }

    public void setModal(boolean modal) {
        this.modal = modal;
    }

    public String getNameInput() {
        return nameInput;
    }

    public void setNameInput(String nameInput) {
        this.nameInput = nameInput;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getSelectPattern() {
        return selectPattern;
    }

    public void setSelectPattern(String selectPattern) {
        this.selectPattern = selectPattern;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    /**
     * 转换成<code>JSON Object</code>格式的字符串.
     *
     * @return
     * <code>JSON Object</code>格式的字符串
     */
    public String toJson() {
        StringBuilder jsonObject = new StringBuilder();
        jsonObject.append(JsonUtils.LEFT_BIG_BRACKET).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("treeName").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(treeName).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("async").append(JsonUtils.COLON_SYMBOL).append(async).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("checkStyle").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(checkStyle).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("selectPattern").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(selectPattern).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        if (anchorUrl != null) {
            jsonObject.append("anchorUrl").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(anchorUrl).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
            jsonObject.append("anchorTarget").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(anchorTarget).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
            jsonObject.append("anchorApply").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(anchorApply).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        }
        jsonObject.append("modal").append(JsonUtils.COLON_SYMBOL).append(modal).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("returnType").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(returnType).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("idInput").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(idInput).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("nameInput").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(nameInput).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("formId").append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(formId).append(JsonUtils.QUOTE).append(JsonUtils.COMMA).append(JsonUtils.NEXT_LINE_SYMBOL);
        jsonObject.append("autoSubmit").append(JsonUtils.COLON_SYMBOL).append(autoSubmit).append(JsonUtils.NEXT_LINE_SYMBOL);

        return jsonObject.append(JsonUtils.RIGHT_BIG_BRACKET).toString();
    }
}
