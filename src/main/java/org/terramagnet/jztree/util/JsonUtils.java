package org.terramagnet.jztree.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.terramagnet.jztree.schema.JsonExpression;

/**
 * 简单对象与<code>JSON</code>转换工具
 *
 * @author terrason
 */
public class JsonUtils {

    /**
     * 英文引号（"）
     */
    public static final char QUOTE = '\"';     //双引号
    /**
     * 英文逗号（,）
     */
    public static final char COMMA = ',';           //逗号
    /**
     * 英文冒号（:）
     */
    public static final char COLON_SYMBOL = ':';    //冒号
    /**
     * 英文左大括号（{）
     */
    public static final char LEFT_BIG_BRACKET = '{';      //左大括号
    /**
     * 英文右大括号（}）
     */
    public static final char RIGHT_BIG_BRACKET = '}';      //右大括号
    /**
     * 英文左中括号（[）
     */
    public static final char LEFT_SQUARE_BRACKET = '[';      //左中括号
    /**
     * 英文左中括号（]）
     */
    public static final char RIGHT_SQUARE_BRACKET = ']';      //右中括号
    /**
     * 换行符（\n）
     */
    public static final char NEXT_LINE_SYMBOL = '\n';             //换行符
    public static final char TAB = '\t';
    public static PropertyUtilsBean beanUtil = new PropertyUtilsBean();

    public static void buildJson(Object obj, StringBuilder builder) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (obj.getClass() == String.class) {
            builder.append(JsonUtils.QUOTE).append(obj).append(JsonUtils.QUOTE);
        } else if (obj instanceof Number || obj instanceof Boolean || obj instanceof JsonExpression) {
            builder.append(obj);
        } else if (obj instanceof Collection) {
            builder.append(JsonUtils.LEFT_SQUARE_BRACKET);
            boolean empty = true;
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                Object value = it.next();
                if (value != null) {
                    buildJson(value, builder);
                    builder.append(JsonUtils.COMMA);
                    empty = false;
                }
            }
            if (!empty) {
                builder.deleteCharAt(builder.length() - 1);
            }
            builder.append(JsonUtils.RIGHT_SQUARE_BRACKET);
        } else if (obj instanceof Map) {
            builder.append(JsonUtils.LEFT_BIG_BRACKET);
            boolean empty = true;
            Iterator<Map.Entry> it = ((Map) obj).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry next = it.next();
                Object key = next.getKey();
                Object value = next.getValue();
                if (value != null) {
                    builder.append(QUOTE).append(key).append(QUOTE).append(JsonUtils.COLON_SYMBOL);
                    buildJson(value, builder);
                    builder.append(JsonUtils.COMMA);
                    empty = false;
                }
            }
            if (!empty) {
                builder.deleteCharAt(builder.length() - 1);
            }
            builder.append(JsonUtils.RIGHT_BIG_BRACKET);
        } else {
            builder.append(JsonUtils.LEFT_BIG_BRACKET);
            boolean empty = true;
            Map<String, Object> describe = beanUtil.describe(obj);
            Iterator<Entry<String, Object>> iterator = describe.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (value != null && !"class".equals(key)) {
                    builder.append(QUOTE).append(key).append(QUOTE).append(JsonUtils.COLON_SYMBOL);
                    buildJson(value, builder);
                    builder.append(JsonUtils.COMMA);
                    empty = false;
                }
            }
            if (!empty) {
                builder.deleteCharAt(builder.length() - 1);
            }
            builder.append(JsonUtils.RIGHT_BIG_BRACKET);
        }
    }

    /**
     * 生成用于错误提示的节点数据. <p>生成的树仅有一个节点。这个节点的结构如下:</p>
     * <pre>[{name:message}]</pre>
     *
     *
     * @param message 错误信息
     * @return 用于错误提示的节点数据.
     */
    public static String generateErrorTree(String message) {
        StringBuilder jsonArray = new StringBuilder();
        jsonArray.append(LEFT_SQUARE_BRACKET).append(LEFT_BIG_BRACKET);
        jsonArray.append(QUOTE).append("name").append(QUOTE).append(COLON_SYMBOL).append(QUOTE).append(message).append(QUOTE);
        jsonArray.append(RIGHT_BIG_BRACKET).append(RIGHT_SQUARE_BRACKET);
        return jsonArray.toString();
    }
}
