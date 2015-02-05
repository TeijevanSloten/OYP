<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="base/header.jsp" %>
<h1>Your Messages:</h1>

    <a href="retrievemail">Refresh</a>
<c:forEach var="email" items="${messages}">
    <hr>
    <a href="showmail/${email.getMessageNumber()}">
        ${email.getSubject()} || ${email.getReceivedDate()}
    </a>
</c:forEach>
<%@ include file="base/footer.jsp" %>