/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.exception;

/**
 * 业务树不支持{@link org.terramagnet.jztree.Jztree#listChildNodes(java.lang.String) 异步操作}的异常.
 *
 * @author terrason
 */
public class TreeSyncOperationUnsupportException extends RuntimeException {

    private static final String msg = "业务树不支持异步操作";

    public TreeSyncOperationUnsupportException() {
        super(msg);
    }

    public TreeSyncOperationUnsupportException(String treeName, Throwable thrwbl) {
        super(msg + "[treeName=" + treeName + "]", thrwbl);
    }

    public TreeSyncOperationUnsupportException(String treeName) {
        super(msg + "[treeName=" + treeName + "]");
    }
}
