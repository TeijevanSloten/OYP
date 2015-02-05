<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="base/header.jsp" %>
<h1>Your Messages:</h1>
<a href="${pageContext.request.contextPath}/mail/showmail/${mail.getMessageNumber()}">Back</a>
<hr>
${mail.getFrom()}
<hr >
${mail.getContent()}

<%@ include file="base/footer.jsp" %>