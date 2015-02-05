<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="base/header.jsp" %>
<h1>Your Messages:</h1>
<c:forEach var="email" items="${messages}">
    <hr>
    <a href="${email.getMessageNumber()}">
        ${email.getSubject()} || ${email.getReceivedDate()}
    </a>
</c:forEach>
<%@ include file="base/footer.jsp" %>