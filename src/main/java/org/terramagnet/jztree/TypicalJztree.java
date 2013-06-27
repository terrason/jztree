/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree;

import org.terramagnet.jztree.exception.TreeSyncOperationUnsupportException;
import org.terramagnet.jztree.schema.TreeSetting;
import org.terramagnet.jztree.servlet.Context;
import java.util.Collection;

/**
 * 业务树的抽象超类，定义了一些典型节点属性. 使用{@link TreeSetting 默认配置}。
 * 
 * <p>在扩展此类时可以添加任意多个形如<code>nodeXxxx(T node)</code>的方法，在最终转化成JSON数据时，会将此类方法作为自定义属性添加到节点元素中。</p>
 * <p>例如若一个业务树中定义如下：</p>
 * <pre>
 *  public class XxxxJztree&lt;T&gt; extends TypicalJztree&lt;T&gt {
 *      ....
 *      public String nodeId(T node) {...}
 *      public String nodeParentId(T node){...}
 *      public String nodeName(T node){...}
 *      public Boolean nodeIsParent(T node){...}
 *      public Boolean nodeChecked(T node){...}
 *  }
 * </pre>那么在生成节点的JSON字符串时，每个节点都拥有{@code id parentId name isParent checked}这五个属性（如果方法不返回{@code null}的话）。
 * 
 * <p>在扩展此类时，可以有自定义实例变量，它们会尽可能的注入JS引擎传入的同名参数（必须要有setter方法且是基本类型才能成功）。</p>
 * @author terrason
 */
public abstract class TypicalJztree<T> implements Jztree<T> {

    private TreeSetting setting;
    private Context context;

    @Override
    public TreeSetting getSetting() {
        if (setting == null) {
            setting = new TreeSetting();
            initSetting(setting);
        }
        return setting;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 返回上下文参数.
     *
     * <p>所有Http请求中的参数都在上下文参数中，但是需要特别注意：<strong>所有http请求的参数以{@code String}为键，以{@code String[]}数组为值！</strong></p>
     *
     * @see #getParameter(java.lang.String)
     */
    protected Context getContext() {
        return context;
    }

    /**
     * 获取Http请求中的参数. 如果参数有多个值只返回第一个值。
     *
     * @param name 参数名
     * @return 第一个找到的值或{@code null}
     */
    protected String getParameter(String name) {
        String[] param = (String[]) context.get(name);
        if (param != null && param.length > 0) {
            return param[0];
        } else {
            return null;
        }
    }

    /**
     * 设置树的配置信息. 请参考<a
     * href="http://172.16.5.18/common/docs/zTree/api/API_cn.html">zTree v3.1
     * API 文档</a>中的<cite> setting 配置详解</cite>
     *
     * @deprecated 通过覆盖{@link #initSetting(TreeSetting) }方法来更改配置。
     *
     * <p>一般情况下不需要调用此方法，使用<pre>
     *  TreeSetting setting=getSetting();
     *  setting.setXxx(...);
     *  setting.getXxx().setXxx(...);</pre> 即可改变配置。 </p>
     *
     * @param setting 新的配置.
     */
    protected void setSetting(TreeSetting setting) {
        this.setting = setting;
    }

    /**
     * 更改默认配置.
     *
     * <p>请参考<a
     * href="http://www.ztree.me/v3/api.php">zTree v3.1
     * API 文档</a>中的<cite> setting 配置详解</cite></p>
     *
     * @param setting 树的配置信息
     */
    protected void initSetting(TreeSetting setting) {
    }

    /**
     * 获取树的根节点父节点ID.
     *
     * @return {@link #getSetting() 配置信息}中的配置
     */
    protected String getRootPId() {
        return getSetting().getData().getSimpleData().getRootPId();
    }

    /**
     * 设置rootPId，树节点中父节点等于此属性的节点为根节点.
     */
    protected void setRootPId(String rootPId) {
        getSetting().getData().getSimpleData().setRootPId(rootPId);
    }

    /**
     * {@inheritDoc } <p>除非覆盖此方法，否则抛出{@link TreeSyncOperationUnsupportException}。</p>
     */
    @Override
    public Collection<T> listChildNodes(String parentNodeId) throws TreeSyncOperationUnsupportException {
        throw new TreeSyncOperationUnsupportException();
    }

    /**
     * {@inheritDoc } <p>除非覆盖此方法，否则抛出{@link TreeSyncOperationUnsupportException}。</p>
     */
    @Override
    public Collection<T> listPreviewNodes() throws TreeSyncOperationUnsupportException {
        throw new TreeSyncOperationUnsupportException();
    }

    /**
     * 返回节点的唯一标识. 默认配置下JS引擎中自动将此文本作为节点对象的{@code "id"}属性。这个ID号必须在整个树中唯一！<strong>请特别注意以下情况：</strong>
     * <p>树的枝节点是部门数据，叶节点是人员信息数据，部门的ID号与人员信息的ID号可能相同！</p>
     * <p>解决方法是：增加ID前缀！例如部门的节点ID为 {@code "dept_"+node.id}，人员信息的节点ID为
     * {@code "user_"+node.id}。这样保证节点ID在树中是唯一的。<strong>另外js引擎中已能够自动识别"_"连字符，
     * 但需要返回节点ID时，自动返回最后一个"_"字符后面的内容。</strong></p>
     *
     * @param node 节点
     * @return 节点的唯一标识
     * @throws IllegalArgumentException 节点{@code node}不在这个树中（可选）
     */
    public abstract String nodeId(T node) throws IllegalArgumentException;

    /**
     * 返回节点的父节点唯一标识. 默认配置下JS引擎中自动将此文本作为节点对象的{@code "parentId"}属性。
     *
     * @param node 节点
     * @return 父节点唯一标识
     * @throws IllegalArgumentException 节点{@code node}不在这个树中（可选）
     * @see #nodeId(java.lang.Object) 树节点的唯一标识
     */
    public abstract String nodeParentId(T node) throws IllegalArgumentException;

    /**
     * 返回节点的标签文本. 该文本用于显示在树上，默认配置下JS引擎中自动将此文本作为节点对象的{@code "name"}属性。
     *
     * @param node 节点
     * @return 节点的显示文本
     * @throws IllegalArgumentException 节点{@code node}不在这个树中（可选）
     */
    public abstract String nodeName(T node) throws IllegalArgumentException;
}
