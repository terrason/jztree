/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.servlet.operator;

import org.terramagnet.jztree.JztreeService;
import org.terramagnet.jztree.config.jaxb.WebConfig;
import org.terramagnet.jztree.servlet.Operation;
import org.terramagnet.jztree.servlet.parameter.Option;
import java.util.Map;

/**
 *
 * @author lee
 */
public class Selection implements Operation {

    @Override
    public String execute(Option option, Map context, JztreeService service, WebConfig config) {
        return config.getPage().get("selection");
    }
    
}
