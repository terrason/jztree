/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.exception;

/**
 * 使用反射机制创建业务树时出错.
 *
 * @author terrason
 */
public class TreeReflectException extends RuntimeException {

    public TreeReflectException(String treeName, Throwable thrwbl) {
        super("反射业务树[treeName=" + treeName + "]失败", thrwbl);
    }

    public TreeReflectException(String treeName) {
        super("反射业务树[treeName" + treeName + "]失败");
    }
}
