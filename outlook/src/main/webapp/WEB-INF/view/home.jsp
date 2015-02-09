<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Home page</h1>
${response}
<p>
<a href="${pageContext.request.contextPath}/mail/showmail">Show Mail</a><br/>
<a href="${pageContext.request.contextPath}/mail/sendmail">Send an email</a>
</p>