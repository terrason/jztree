<%-- 
    Document   : error
    Created on : 2011-12-12, 13:46:16
    Author     : LEE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="${servletPath}${webConfig.namespace}${webConfig.cssPackage}/zTreeWrapper.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${servletPath}${webConfig.namespace}${webConfig.jqueryUrl}"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $(".hoverable").hover(function(){
                    $(this).addClass("highlight");
                }, function(){
                    $(this).removeClass("highlight");
                });
                $(":button.cancel").click(function(){
                    var minLength=$.browser.mozilla ? 1 : 0;
                    var length=window.top.history.length;
                    if(length && length>minLength){
                        window.top.history.back();
                    }else{
                        window.top.close();
                    }
                });
            });
        </script>
        <title>Jztree操作出错</title>
    </head>
    <body style="margin:0px;padding:0px;text-align:center;">
        <div class="ztree-wrapper">
            <div class="ztree-wrapper-title">Jztree操作出错</div>
            <div class="ztree-wrapper-content">${exception}</div>
            <div class="ztree-wrapper-bottom">
                <button class="hoverable cancel" type="button">返回</button>
            </div>
        </div>
    </body>
</html>
