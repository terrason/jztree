/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.servlet;

import org.terramagnet.jztree.JztreeService;
import org.terramagnet.jztree.config.jaxb.WebConfig;
import org.terramagnet.jztree.servlet.parameter.Option;
import java.util.Map;

/**
 *
 * @author terrason
 */
public interface Operation {

    /**
     * 执行操作. <ul> <li>操作后若要跳转到一个URL则返回这个URL字符串（必须以"/"开头）。</li>
     * <li>若该操作属于异步操作，需要向客户端传送JSON数据，则返回这个JSON字符串。</li>
     * <li>若返回值不匹配以上两项，将忽略结果。</li> </ul>
     *
     * @param option 常用选项
     * @param context 上下文集合. 所有Http请求中的参数都在上下文参数中，但是需要特别注意：<strong>所有http请求的参数以{@code String}为键，以{@code String[]}数组为值！</strong>
     * @param service jztree主服务
     * @param config jztree的Web相关配置
     * @return 操作完后需要跳转到的URL地址（<strong>以"/"开头</strong>）或JSON字符串（JSON对象格式）
     */
    public String execute(Option option, Map context, JztreeService service, WebConfig config);
}
