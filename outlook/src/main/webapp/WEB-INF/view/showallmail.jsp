<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

        <div class="col-md-3">
            <h3 style="margin-bottom: 0;padding-bottom: 0">Messages:</h3>
            <c:forEach var="email" items="${messages}">
                <a href="showmail?id=${email.getMessageid()}">
                    ${email.getSubject()}
                </a>
            </c:forEach>
                
        </div>
