/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.exception;

/**
 *jztree在加载配置信息时出错.
 * @author terrason
 */
public class JztreeConfigInitException extends Exception {

    private static final String msg = "jztree在加载配置信息时出错";

    public JztreeConfigInitException(Throwable cause) {
        super(msg, cause);
    }

    public JztreeConfigInitException(String message, Throwable cause) {
        super(msg + "：" + message, cause);
    }

    public JztreeConfigInitException(String message) {
        super(msg + "：" + message);
    }

    public JztreeConfigInitException() {
        super(msg);
    }
}
