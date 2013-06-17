package org.terramagnet.jztree;

import org.terramagnet.jztree.exception.TreeNotFoundException;
import org.terramagnet.jztree.exception.TreeSyncOperationUnsupportException;
import java.util.List;

/**
 * 主业务逻辑，jztree 的调用入口. <ol><li>负责管理业务树</li><li>根据树名称返回树结构的JSON字符串</li></ol>
 *
 * @author terrason
 * @see Jztree 业务树
 */
public interface JztreeService {

    /**
     * 向<cite>JZTree</cite>注册业务树.
     *
     * @param name 为您的树指定一个名称.请确保唯一，否则后注册的树会替换已存在的同名树。
     * @param provider 具体义务树
     */
    public void registTree(String name, Class<Jztree> provider);

    /**
     * 注销业务树.
     *
     * @param name 要删除的树名称
     */
    public void unRegistTree(String name);

    /**
     * 获取业务树.
     *
     * @param name 业务树名称
     * @return 业务树或{@code null}
     */
    public Class<Jztree> getTree(String name);

    /**
     * 创建业务树的实例.
     *
     * @param name 业务树名称
     * @return 业务树实例或{@code null}
     */
    public Jztree instanceTree(String name);

    /**
     * 列出系统中可用的所有树名称.
     *
     * @return 系统中的树名称集合
     */
    public List<String> listAvailableTrees();

    /**
     * 当前客户对象. <strong>请确保在<code>HttpSession</code>中能取到用户信息，且在<cite>jztree-config.xml</cite>中配置了正确的用户对象<code>session</code>属性名</strong>。
     * <p>所有树形操作的发起者会与初始所发起的线程相绑定，若执行本方法的时候没有更换线程，其他配置正确，则本方法能返回发起者信息。</p>
     *
     * @return 客户用户对象，找不到时返回<code>null</code>.
     */
    public Object currentClient();

    /**
     * 绑定客户信息.一般由Servlet调用。</p> <p><strong><em>绑定</em>、{@link #unBindingClient() 解绑定}和{@link #currentClient() 获取当前用户}三项操作必须在同一个线程内才能保证逻辑正确！</strong></p>
     *
     * @param client 当前客户
     * @return 成功从<code>session</code>中取到用户信息返回<code>true</code>，否则返回<code>false</code>.
     */
    public boolean bindingClient(Object client);

    /**
     * 解绑定客户信息.一般由Servlet调用，用来解绑定当前线程与操作客户。
     */
    public void unBindingClient();

    /**
     * 以JSON对象格式返回业务树的全部节点数据.
     *
     * @param treeName 业务树名称
     * @return 树数据JSON字符串
     * @exception TreeNotFoundException 找不到指定名称的业务选择器时，抛出此异常！
     */
    public String jsonNodes(String treeName) throws TreeNotFoundException;

    /**
     * 以JSON对象格式返回树的第一批次节点.
     *
     * @param treeName 业务树名称
     * @return 树数据JSON字符串
     * @exception TreeNotFoundException 找不到指定名称的业务选择器时，抛出此异常！
     * @exception TreeSyncOperationUnsupportException 业务树不支持异步操作
     */
    public String jsonPreviewNodes(String treeName) throws TreeNotFoundException, TreeSyncOperationUnsupportException;

    /**
     * 以JSON对象格式返回树中的部分节点. <p>主要用来异步请求某业务树的部分节点，一般通过调用{@link Jztree#listChildNodes(java.lang.String)
     * }方法来实现。</p>
     *
     * @param treeName 业务树名称
     * @param parentNodeId 父节点id号
     * @return 业务树treeName中ID号为parentNodeId的节点的子节点的JSON数据
     * @exception TreeNotFoundException 找不到指定名称的业务树时，抛出此异常！
     * @exception TreeSyncOperationUnsupportException 指定的业务树不支持{@link Jztree#listChildNodes(java.lang.String) 取部分节点数据}操作（即收到了{@link TreeSyncOperationUnsupportException}异常）
     */
    public String jsonChildNodes(String treeName, String parentNodeId) throws TreeNotFoundException, TreeSyncOperationUnsupportException;

    /**
     * 以JSON对象格式返回配置信息.
     *
     * @param treeName 业务树名称
     * @see Jztree#getSetting() 业务树的配置信息
     */
    public String jsonSetting(String treeName) throws TreeNotFoundException;
}
