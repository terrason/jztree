/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.facade;

import org.terramagnet.jztree.JztreeService;
import org.terramagnet.jztree.exception.TreeNotFoundException;
import org.terramagnet.jztree.service.DefaultJztreeService;

/**
 * <code>JZTree</code>门面
 *
 * @author terrason
 */
public class JztreeFacade {

    private static JztreeService service = new DefaultJztreeService();

    /**
     * 获取Jztree的主服务.
     */
    public static JztreeService openService() {
        return service;
    }

    /**
     * 获取当前客户对象. <strong>请确保在<code>HttpSession</code>中能取到用户信息，且在<cite>jztree-config.xml</cite>中配置了正确的用户对象<code>session</code>属性名</strong>。
     * <p>所有树形操作的发起者会与初始所发起的线程相绑定，若执行本方法的时候没有更换线程，其他配置正确，则本方法能正确返回发起者信息。</p>
     *
     * @return 客户用户对象，找不到时返回<code>null</code>.
     */
    public static Object currentClient() {
        return service.currentClient();
    }

    /**
     * 使用默认配置方案返回指定业务树的节点数据. <p>业务树请在<cite>jztree-config.xml</cite>中的<code>tree-config</code>节点中配置。</p>
     *
     * @param treeName 业务树名称
     * @return JSON字符串格式的节点数据
     * @throws TreeNotFoundException 指定的业务树找不到时抛出此异常
     */
    public static String jsonNodes(String treeName) throws TreeNotFoundException {
        return service.jsonNodes(treeName);
    }

    /**
     * 获取一个JSON格式的ztree配置方案。
     *
     * @param treeName 业务树名称
     * @return ztree的配置
     */
    public static String jsonSetting(String treeName) {
        return service.jsonSetting(treeName);
    }
}
