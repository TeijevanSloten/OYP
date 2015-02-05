<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="base/header.jsp" %>
<h1>Your Messages:</h1>
<a href="${pageContext.request.contextPath}/mail/showmail">Back</a> |
<a href="${pageContext.request.contextPath}/mail/forward?id=${mail.getMessageNumber()}">Forward</a> |
<a href="${pageContext.request.contextPath}/mail/reply?id=${mail.getMessageNumber()}">Reply</a>
<hr>
${mail.getFrom()}
<hr >
${mail.getContent()}

<%@ include file="base/footer.jsp" %>