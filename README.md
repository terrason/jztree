jztree
======

A simple solution to J2ee Tree Module with little javascript skill (a [ztree](http://www.ztree.me) wrapper). 

jztree 可以帮你在自己的J2EE项目中构建通用树模块。
在WEB端，它提供了一些简单实用的 javascriptAPI 来辅助调出树形；
在java后台声明了树的接口，开发人员可以自己实现各种业务树并按照说明做好配置即可；


在项目中使用 jztree
------------------
### 1.  下载必要的架包，放入工程classpath中。
[jztree-1.0.jar](blob/master/target/jztree-1.0.jar) 主要架包
[XmlConfiguration-1.0.jar](../XmlConfiguration/blob/master/target/XmlConfiguration-1.0.jar)     一个xml→java的快速反射工具
dom4j及其依赖的架包（xml-apis commons-beanutils commons-logging 等）
### 2.  下载web资源部分，放入工程WEB根目录。
[jztree-1.0-web.zip](blob/master/target/jztree-1.0-web.zip)     网页部分资源，包括js、css和jsp文件，项目中可自行修改样式。
### 3.  修改项目的部署描述符——web.xml，增加一个servlet，如下所示：
        <servlet>
            <description>JZTree树形选择器</description>
            <servlet-name>jztree</servlet-name>
            <servlet-class>com.suncreate.jztree.servlet.JztreeServlet</servlet-class>
        </servlet>
        <servlet-mapping>
            <servlet-name>jztree</servlet-name>
            <url-pattern>*.tree</url-pattern>
        </servlet-mapping>
### 4.  在项目源码目录建立一个配置文件：jztree-config.xml ，基本内容如下：
        <?xml version="1.0" encoding="UTF-8"?>
        <jztree-config>
            <tree-config>
                ...
                <tree name="XxxxTree" class="the.fullname.of.your.JztreeImpl"/>
                ...
            </tree-config>
        </jztree-config>
其中<tree .../>节点就是配置各种业务树，例如：部门树、产品数…… ，业务树名称不能重复，实例必须实现Jztree接口，推荐
扩展[TypicalJztree]抽象类。详细请参见Javadoc.
### 5.  在网页中调用树
弹出树：在页面上加载jztree/js/ztree4j.js（该文件位于jztree-1.0-web.zip中），
使用Jztree.select({treeName:"XxxxTree",checkStyle:"checkbox"})来弹出模态树形选择器。
绑定树：加载所有必要的js（确保路径正确）
        <script type="text/javascript" src="jztree/js/jquery.js"></script>
        <script type="text/javascript" src="jztree/js/jquery.ztree.core-3.x.min.js"></script>
        <script type="text/javascript" src="jztree/js/jquery.ztree.excheck-3.x.min.js"></script>
        <script type="text/javascript" src="jztree/js/jquery.jztree.js"></script>
        <script type="text/javascript">
        $(document).ready(function(){
            $(".ztree").jztree({treeName:"XxxxTree",anchorUrl:"xxxx/xxx?id=attr{id}"});
        });
        </script>
### 详细参见[JavaScript文档](wiki/JavascriptApi)