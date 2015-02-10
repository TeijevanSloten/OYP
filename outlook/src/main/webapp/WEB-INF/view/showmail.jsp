<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Your Messages:</h1>
<a href="${pageContext.request.contextPath}/showallmail">Back</a> |
<a href="${pageContext.request.contextPath}/forward?id=${mail.getMessageid()}">Forward</a> |
<a href="${pageContext.request.contextPath}/reply?id=${mail.getMessageid()}">Reply</a>
<hr>
${mail.getFromemail()} ||   ${email.getSubject()}
<hr >
${mail.getContent()}
<hr>
<h4>Attachments</h4>
<c:forEach var="attachment" items="${attachments}">
    <a href="${pageContext.request.contextPath}Attachments/${attachment.getEmailid()}-${attachment.getFilename()}" target="_blank">
        ${attachment.getFilename()}
    </a> <br>
</c:forEach>
