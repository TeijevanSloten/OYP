<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Your Messages:</h1>
        <a href="${pageContext.request.contextPath}/showallmail">Back</a> |
        <a href="${pageContext.request.contextPath}/forward?id=${mail.getMessageid()}">Forward</a> |
        <a href="${pageContext.request.contextPath}/reply?id=${mail.getMessageid()}">Reply</a>
        <hr>
        ${mail.getFromemail()} ||   ${email.getSubject()}
        <hr >
        ${mail.getContent()}
    </body>
</html>