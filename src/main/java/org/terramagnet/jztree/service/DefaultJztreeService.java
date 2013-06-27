package org.terramagnet.jztree.service;

import org.terramagnet.jztree.Jztree;
import org.terramagnet.jztree.JztreeService;
import org.terramagnet.jztree.exception.TreeNotFoundException;
import org.terramagnet.jztree.exception.TreeReflectException;
import org.terramagnet.jztree.exception.TreeSyncOperationUnsupportException;
import org.terramagnet.jztree.servlet.Context;
import org.terramagnet.jztree.util.JsonUtils;
import org.terramagnet.jztree.util.StringUtils;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * jztree主服务的默认实现.
 *
 * <p>当将节点转换为JSON字符串时，会为每个节点设置属性，属性名由{@link Jztree 业务树}的{@code nodeXxxxXxxx(Object node)}方法定义。</p>
 * <p>例如若一个业务树中含有以下方法：</p>
 * <pre>
 *  public class XxxxJztree&lt;T&gt; implements Jztree&lt;T&gt {
 *      ....
 *      public String nodeId(T node) {...}
 *      public String nodeParentId(T node){...}
 *      public String nodeName(T node){...}
 *      public Boolean nodeIsParent(T node){...}
 *      public Boolean nodeChecked(T node){...}
 *  }
 * </pre>那么在生成节点的JSON字符串时，每个节点都拥有{@code id parentId name isParent checked}这五个属性（如果对应方法不返回{@code null}的话）。
 *
 * @author terrason
 */
public class DefaultJztreeService implements JztreeService {

    private static final Log logger = LogFactory.getLog(JztreeService.class);
    private Map<String, Class<Jztree>> trees;
    private Map<Thread, Object> clients;
    private Pattern nodePropertyRegexp;

    public DefaultJztreeService() {
        trees = new HashMap<String, Class<Jztree>>(5);
        clients = new HashMap<Thread, Object>();
        nodePropertyRegexp = Pattern.compile("node((\\p{Upper}\\p{Lower}*)+)");
        logger.info("实例化JztreeService主服务");
    }

    @Override
    public Class<Jztree> getTree(String name) {
        return trees.get(name);
    }

    @Override
    public Jztree instanceTree(String name) {
        try {
            Class<Jztree> cls = trees.get(name);
            if (cls == null) {
                return null;
            } else {
                Jztree tree = cls.newInstance();
                Context<Object> context = Context.currentContext();
                tree.setContext(context);
                try {
                    BeanUtils.populate(tree, context);
                } catch (InvocationTargetException ex) {
                    throw new TreeReflectException(name, ex);
                }
                return tree;
            }
        } catch (InstantiationException ex) {
            throw new TreeReflectException(name, ex);
        } catch (IllegalAccessException ex) {
            throw new TreeReflectException(name, ex);
        }
    }

    @Override
    public void registTree(String name, Class<Jztree> provider) {
        trees.put(name, provider);
        if (logger.isDebugEnabled()) {
            logger.debug("注册了一个业务树:" + name);
        }
    }

    @Override
    public void unRegistTree(String name) {
        Class<Jztree> removed = trees.remove(name);
        if (removed == null) {
            if (logger.isWarnEnabled()) {
                logger.info("没有注册过业务树 " + name);
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("注销了一个业务树:" + name);
            }
        }
    }

    @Override
    public List<String> listAvailableTrees() {
        return new ArrayList<String>(trees.keySet());
    }

    @Override
    public Object currentClient() {
        return clients.get(Thread.currentThread());
    }

    @Override
    public boolean bindingClient(Object client) {
        clients.put(Thread.currentThread(), client);
        return true;
    }

    @Override
    public void unBindingClient() {
        clients.remove(Thread.currentThread());
    }

    @Override
    public String jsonNodes(String treeName) throws TreeNotFoundException {
        Jztree jztree = instanceTree(treeName);
        if (jztree == null) {
            throw new TreeNotFoundException(treeName);
        }
        try {
            return jsonNodes(jztree, jztree.listNodes());
        } catch (RuntimeException ex) {
            logger.error("业务树在加载数据时出错：" + ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public String jsonPreviewNodes(String treeName) throws TreeNotFoundException, TreeSyncOperationUnsupportException {
        Jztree jztree = instanceTree(treeName);
        if (jztree == null) {
            throw new TreeNotFoundException(treeName);
        }
        Collection childNodes;
        try {
            childNodes = jztree.listPreviewNodes();
        } catch (RuntimeException ex) {
            logger.error("业务树在加载数据时出错：" + ex.getMessage(), ex);
            throw ex;
        }
        return jsonNodes(jztree, childNodes);
    }

    @Override
    public String jsonChildNodes(String treeName, String parentNodeId) throws TreeNotFoundException, TreeSyncOperationUnsupportException {
        Jztree jztree = instanceTree(treeName);
        if (jztree == null) {
            throw new TreeNotFoundException(treeName);
        }
        Collection childNodes;
        try {
            childNodes = jztree.listChildNodes(parentNodeId);
        } catch (RuntimeException ex) {
            logger.error("业务树在加载数据时出错：" + ex.getMessage(), ex);
            throw ex;
        }
        return jsonNodes(jztree, childNodes);
    }

    @Override
    public String jsonSetting(String treeName) throws TreeNotFoundException {
        Jztree jztree = instanceTree(treeName);
        if (jztree == null) {
            throw new TreeNotFoundException(treeName);
        }
        try {
            return jsonSetting(jztree);
        } catch (Exception ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("无法获取业务树 " + treeName + " 的配置信息");
            }
            return "{}";
        }
    }

    /**
     * 树节点模型转换成JSON字符串. 每个节点的会追加属性，属性名为所有{@code tree.nodeXxxxXxx(T)}方法中的{@code xxxxXxx}集合.
     *
     * @param tree 业务树名称
     * @param nodes 树节点模型（可以为空列表，但不能为空。）
     * @return JSON字符串
     */
    public String jsonNodes(Jztree tree, Collection nodes) {
        StringBuilder jsonArray = new StringBuilder();
        jsonArray.append(JsonUtils.LEFT_SQUARE_BRACKET);
        String[] methodNames = nodeMethods(tree);
        Class nodeType = nodeType(tree);
        for (Object node : nodes) {
            jsonArray.append(JsonUtils.LEFT_BIG_BRACKET).append(JsonUtils.NEXT_LINE_SYMBOL);
            boolean empty = true;
            for (int i = 0; i < methodNames.length; i++) {
                String methodName = methodNames[i];
                String attributeName = StringUtils.lowFirstChar(methodName.substring(4));

                Object attributeValue = null;
                if (node instanceof Map) {
                    Map n = (Map) node;
                    attributeValue = n.get(attributeName);
                } else {
                    try {
                        try {
                            attributeValue = tree.getClass().getMethod(methodName, nodeType).invoke(tree, node);
                        } catch (NoSuchMethodException ex) {
                            attributeValue = tree.getClass().getMethod(methodName, Object.class).invoke(tree, node);
                        }
                    } catch (Exception ex) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("业务树操作节点方法调用失败", ex);
                        }
                    }
                }
                if (attributeValue == null) {
                } else if (attributeValue.getClass() == String.class) {
                    jsonArray.append(JsonUtils.QUOTE).append(attributeName).append(JsonUtils.QUOTE).append(JsonUtils.COLON_SYMBOL).append(JsonUtils.QUOTE).append(attributeValue).append(JsonUtils.QUOTE).append(JsonUtils.COMMA);
                    empty = false;
                } else {
                    jsonArray.append(JsonUtils.QUOTE).append(attributeName).append(JsonUtils.QUOTE).append(JsonUtils.COLON_SYMBOL).append(attributeValue).append(JsonUtils.COMMA);
                    empty = false;
                }
            }
            if (!empty) {
                jsonArray.deleteCharAt(jsonArray.length() - 1);
            }
            jsonArray.append(JsonUtils.RIGHT_BIG_BRACKET).append(JsonUtils.COMMA);
        }
        if (!nodes.isEmpty()) {
            jsonArray.deleteCharAt(jsonArray.length() - 1);
        }
        jsonArray.append(JsonUtils.RIGHT_SQUARE_BRACKET);
        return jsonArray.toString();
    }

    private String[] nodeMethods(Jztree tree) {
        Method[] origins = tree.getClass().getMethods();
        Set<String> methods = new HashSet<String>();
        for (int i = 0; i < origins.length; i++) {
            Method method = origins[i];
            if (nodePropertyRegexp.matcher(method.getName()).matches()) {
                methods.add(method.getName());
            }
        }
        return methods.toArray(new String[methods.size()]);
    }

    private Class nodeType(Jztree tree) {
        Type genericSuperclass = tree.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    /**
     * 树配置信息转换成json字符串.
     *
     * @param tree 业务树名称
     * @return 配置信息的json字符串
     */
    public String jsonSetting(Jztree tree) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object setting = tree.getSetting();
        if (setting == null) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        JsonUtils.buildJson(setting, sb);
        return sb.toString();
    }
}
