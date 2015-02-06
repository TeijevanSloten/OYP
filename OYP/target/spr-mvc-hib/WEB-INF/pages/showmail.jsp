<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="base/header.jsp" %>
<h1>Your Messages:</h1>
<a href="${pageContext.request.contextPath}/mail/showmail">Back</a> |
<a href="${pageContext.request.contextPath}/mail/forward?id=${mail.getMessageNumber()}">Forward</a> |
<a href="${pageContext.request.contextPath}/mail/reply?id=${mail.getMessageNumber()}">Reply</a>
<hr>
${mail.getFrom()}
<hr>
${mail.getReceivedDate()}
<hr>
${mail.getSubject()}
<hr >
${mail.getContent()}
<hr>
<c:forEach var="attachment" items="${mail.getAttachmentsStringRepresentation()}">
   <c:out value="${attachment}"/>    
</c:forEach>

<%@ include file="base/footer.jsp" %>