/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree;

import org.terramagnet.jztree.exception.TreeSyncOperationUnsupportException;
import org.terramagnet.jztree.servlet.Context;
import java.util.Collection;

/**
 * Jztree的业务接口，表示一颗业务树.
 *
 * <p>在配置文件<cite>jztree-config.xml</cite>中配置的<code>tree</code>需要实现此接口。</p>
 * <p>业务树的实例的创建不再是单例模式！而是一次请求一个实例，就像Struts2的Action一样，从而避免了多线程同步的问题。
 *
 * 其所有{@code nodeXxxx(..)}方法都使用当前Jztree实例作为上下文，可以放心使用属性字段，方便开发。</p>
 *
 * <p>{@link #setContext(Context) 设置上下文} 方法会在业务树被实例化后立即调用并传入上下文参数。</p>
 * <p><cite>JZTree</cite>本身不参与数据库操作，只对<em>视图层</em>进行了一些封装，
 * 并在<em>业务逻辑层</em>提供了相关接口供<em>客户</em>自己实现。</p>
 *
 *
 * @param <T> 节点对象类型. 节点对象类型可以是任意java对象。
 * @author terrason
 *
 */
public interface Jztree<T> {

    /**
     * 默认根节点父节点ID. "0"
     */
    public static final String DEFAULT_ROOT_PID = "0";

    /**
     * 返回树的配置信息.
     *
     * 请参考<a href="http://www.ztree.me/v3/api.php">zTree
     * v3.1 API 文档</a>中的<cite> setting 配置详解</cite>
     *
     * @return 树的配置信息.
     */
    public Object getSetting();

    /**
     * 设置当前上下文参数. <p>所有Http请求中的参数都在上下文参数中，但是需要特别注意：<strong>所有http请求的参数以{@code String}为键，以{@code String[]}数组为值！</strong></p>
     *
     * @param context 上下文参数.（所有Http请求中的参数都能在这里找到）
     */
    public void setContext(Context context);

    /**
     * 列出所有节点. <p>返回这个业务树的全部节点数据。</p>
     *
     * @return 节点列表.<strong>（没有节点请返回空列表）</strong>
     * @exception UnsupportedOperationException 不支持此项操作时抛出此异常
     */
    public Collection<T> listNodes() throws UnsupportedOperationException;

    /**
     * 列出少量供以展示的节点. <p>异步加载的树需要实现此方法，这里返回树页面要加载的第一批节点。抛出{@link TreeSyncOperationUnsupportException}则表示不支持<em>异步操作</em>。</p>
     *
     * @return 首批节点数据
     * @exception TreeSyncOperationUnsupportException 表示不支持异步操作时抛出此异常
     * @see #listChildNodes(java.lang.String)
     */
    public Collection<T> listPreviewNodes() throws TreeSyncOperationUnsupportException;

    /**
     * 根据父节点列出其子节点. <p>异步加载的树需要实现此方法，若不想提供异步获取节点数据功能，可以抛出{@link TreeSyncOperationUnsupportException}以表示不支持<em>异步操作</em>。</p>
     *
     * @param parentNodeId 父节点id号.
     * @exception TreeSyncOperationUnsupportException 表示不支持异步操作时抛出此异常
     * @return 子节点列表.<strong>（没有节点请返回空列表）</strong>
     */
    public Collection<T> listChildNodes(String parentNodeId) throws TreeSyncOperationUnsupportException;
}
