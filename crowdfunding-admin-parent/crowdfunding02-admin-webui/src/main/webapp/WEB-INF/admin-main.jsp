<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>

<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <!-- admin-main.jsp -->
            <security:authorize access="hasRole('经理')">
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img data-src="holder.js/200x200/auto/sky" class="img-responsive"
                         alt="Generic placeholder thumbnail">
                    <div>
                        欢迎您，<security:authentication property="principal.originalAdmin.userName"/>经理。
                    </div>
                    <span class="text-muted">Something else</span>
                </div>
            </security:authorize>
        </div>
    </div>
</div>
</body>
</html>

