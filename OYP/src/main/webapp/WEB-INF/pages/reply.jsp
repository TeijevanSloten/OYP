<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="base/header.jsp" %>
<h1>Your Messages:</h1>
<a href="${pageContext.request.contextPath}/mail/showmail/${mail.getMessageNumber()}">Back</a>
<form action="sendreply" method="post">
    <label>Message:<label><br>
    <textarea style="width: 500px; height: 50px;" name="message">Type message here</textarea> <br><br>
    <button type="submit">Submit</button>
    <input type="hidden" name="messageid" value="${mail.getMessageNumber()}">
</form>
<hr>
From: ${mail.getFrom()} <br>
Subject: ${mail.getSubject()}
<hr >
${mail.getContent()}

<%@ include file="base/footer.jsp" %>