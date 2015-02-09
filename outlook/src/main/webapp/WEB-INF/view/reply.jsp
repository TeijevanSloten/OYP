<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Your Messages:</h1>
<a href="${pageContext.request.contextPath}/showmail?id=${mail.getMessageid()}">Back</a>
<form action="sendreply" method="post">
    <label>Message:</label><br>
    <textarea style="width: 500px; height: 50px;" name="message">Type message here</textarea> <br><br>
    <button type="submit">Submit</button>
    <input type="hidden" name="messageid" value="${mail.getMessageid()}">
</form>
<hr>
From: ${mail.getFromemail()} <br>
Subject: ${mail.getSubject()}
<hr >
${mail.getContent()}