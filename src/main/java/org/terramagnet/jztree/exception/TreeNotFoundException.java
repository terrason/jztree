/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.exception;

/**
 * 找不到指定名称的{@link org.terramagnet.jztree.Jztree 业务选择器}时，抛出此异常！
 * @author terrason
 */
public class TreeNotFoundException extends RuntimeException {

    public TreeNotFoundException(String treeName) {
        super("找不到名称为 " + treeName + " 的业务树!");
    }
}
