/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.schema;

/**
 *
 * @author terrason
 */
public class JsonExpression {

    private String expression;

    public JsonExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression;
    }
}
