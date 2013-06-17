/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terramagnet.jztree.servlet;

import org.terramagnet.jztree.JztreeService;
import org.terramagnet.jztree.config.jaxb.JztreeConfig;
import org.terramagnet.jztree.config.jaxb.Tree;
import org.terramagnet.jztree.config.jaxb.WebConfig;
import org.terramagnet.jztree.facade.JztreeFacade;
import org.terramagnet.jztree.servlet.parameter.Option;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.terramagnet.xmlconfiguration.xml.ClassPathXmlConfiguration;

/**
 * JZTree Servlet
 *
 * @author terrason
 */
public class JztreeServlet extends HttpServlet {

    private static final Log logger = LogFactory.getLog(JztreeServlet.class);
    private static final long serialVersionUID = 1L;
    //--------------------------------------------------------------------------------
    private JztreeService service;
    private org.terramagnet.jztree.config.jaxb.ServletConfig servletConfig;
    private WebConfig webConfig;
    private Map<String, Operation> operations = new HashMap<String, Operation>(6);
    private String servletPath;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @exception ServletException if a servlet-specific error occurs
     * @exception IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("webConfig", webConfig);
        request.setAttribute("servletPath", servletPath);
        Option option = fillOptionFromRequest(request);
        Object client = request.getSession().getAttribute(servletConfig.getUserObjectSessionKey());
        if (client != null) {
            service.bindingClient(client);
        }
        try {
            String operationName = currentOperation(request);
            Operation operation = operations.get(operationName);
            if (operation == null) {
                throw new ServletException("找不到指定的操作！operationId=" + operationName);
            }
            Context context = Context.currentContext();
            context.putAll(request.getParameterMap());
            context.put(servletConfig.getUserObjectSessionKey(), client);
            context.setPrincipal(client);
            String rtnVal = operation.execute(option, context, service, webConfig);
            if (rtnVal != null) {
                if (rtnVal.matches("[\\{\\[].*[\\}\\]]")) {
                    sendJson(rtnVal, request, response);
                } else if (rtnVal.startsWith("/")) {
                    sendDispath(rtnVal, request, response);
                }
            }
        } catch (Exception ex) {
            try {
                logger.warn("operation操作出错！", ex);
                request.setAttribute("exception", ex);
                request.getRequestDispatcher(webConfig.getNamespace()+webConfig.getPage().get("error")).forward(request, response);
            } catch (Exception exception) {
                throw new ServletException("跳转到错误页面出错！", exception);
            }
        } finally {
            service.unBindingClient();
        }
    }

    @Override
    public void init() throws ServletException {
        servletPath = getServletContext().getContextPath();
        service = JztreeFacade.openService();
        String configClasspath = getInitParameter("config-classpath");
        if(configClasspath==null){
            configClasspath="/jztree-config.xml";
        }
        JztreeConfig config = (JztreeConfig)new ClassPathXmlConfiguration("/org/terramagnet/jztree/jztree-config.default.xml", configClasspath).configure();
        servletConfig = config.getServletConfig();
        webConfig = config.getWebConfig();
        try {
            initTrees(config);
            initOperations(config);
        } catch (ServletException servletException) {
            logger.error("JztreeServlet启动失败！", servletException);
            throw servletException;
        }
    }

    private void initTrees(JztreeConfig config) throws ServletException {
        Map<String, Tree> trees = config.getTreeConfig().getTree();
        Iterator<String> iterator = trees.keySet().iterator();
        String treeName = null;
        Tree treeConfig = null;
        String treeClassName = null;
        while (iterator.hasNext()) {
            treeName = iterator.next();
            treeConfig = trees.get(treeName);
            treeClassName = treeConfig.getClazz();
            try {
                Class jztree = Class.forName(treeClassName);
                service.registTree(treeName, jztree);
            } catch (ClassNotFoundException ex) {
                throw new ServletException("所配置的业务树（name=" + treeName + "）无法定位", ex);
            }
        }
    }

    private void initOperations(JztreeConfig config) throws ServletException {
        Map<String, String> operationClasses = config.getServletConfig().getOperation();
        Iterator<String> iterator = operationClasses.keySet().iterator();
        while (iterator.hasNext()) {
            String operationName = iterator.next();
            String operationClassName = operationClasses.get(operationName);
            try {
                Operation operation = (Operation) Class.forName(operationClassName).newInstance();
                operations.put(operationName, operation);
            } catch (InstantiationException ex) {
                throw new ServletException("所配置的操作（id=" + operationName + "）无法加载", ex);
            } catch (IllegalAccessException ex) {
                throw new ServletException("所配置的操作（id=" + operationName + "）实现类或其空构造方法是不可访问的", ex);
            } catch (ClassNotFoundException ex) {
                throw new ServletException("所配置的操作（id=" + operationName + "）无法定位", ex);
            }
        }
    }

    protected String currentOperation(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        int end = requestURI.lastIndexOf(servletConfig.getUrlSuffix());
        if (end < 0) {
            throw new ServletException("jztree-config.xml中servlet-config配置不正确！无法将url-suffix与实际URL对应。");
        }
        int start = requestURI.lastIndexOf("/");
        if (start < 0) {
            throw new ServletException("解析URL时出错！");
        }
        return requestURI.substring(start + 1, end);
    }

    protected Option fillOptionFromRequest(HttpServletRequest request) {
        Option option = new Option();
        String anchorPattern = request.getParameter("anchorPattern");
        if (anchorPattern != null) {
            option.setAnchorPattern(anchorPattern);
        }
        String anchorTarget = request.getParameter("anchorTarget");
        if (anchorTarget != null) {
            option.setAnchorTarget(anchorTarget);
        }
        String anchorUrl = request.getParameter("anchorUrl");
        if (anchorUrl != null) {
            option.setAnchorUrl(anchorUrl);
        }
        String async = request.getParameter("async");
        if (async != null) {
            option.setAsync(Boolean.parseBoolean(async));
        }
        String autoSubmit = request.getParameter("autoSubmit");
        if (autoSubmit != null) {
            option.setAutoSubmit(Boolean.parseBoolean(autoSubmit));
        }
        String checkStyle = request.getParameter("checkStyle");
        if (checkStyle != null) {
            option.setCheckStyle(checkStyle);
        }
        String formId = request.getParameter("formId");
        if (formId != null) {
            option.setFormId(formId);
        }
        String idInput = request.getParameter("idInput");
        if (idInput != null) {
            option.setIdInput(idInput);
        }
        String modal = request.getParameter("modal");
        if (modal != null) {
            option.setModal(Boolean.parseBoolean(modal));
        }
        String nameInput = request.getParameter("nameInput");
        if (nameInput != null) {
            option.setNameInput(nameInput);
        }
        String returnType = request.getParameter("returnType");
        if (returnType != null) {
            option.setReturnType(returnType);
        }
        String selectPattern = request.getParameter("selectPattern");
        if (selectPattern != null) {
            option.setSelectPattern(selectPattern);
        }
        String treeName = request.getParameter("treeName");
        if (treeName != null) {
            option.setTreeName(treeName);
        }
        return option;
    }

    private void sendJson(String rtnVal, HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            out.print(rtnVal);
        } finally {
            out.close();
        }
    }

    private void sendDispath(String rtnVal, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(webConfig.getNamespace()+rtnVal);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Jztree Servlet";
    }// </editor-fold>
}
