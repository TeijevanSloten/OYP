<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container-fluid">   
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 ">

            <h1>Your Messages:</h1>

            <a href="retrievemail">Refresh</a>
            <c:forEach var="email" items="${messages}">
                <hr>
                <a href="showmail?id=${email.getMessageid()}">
                    ${email.getSubject()} || ${email.getDate()}
                </a>
            </c:forEach>
                
        </div>
    </div>
</div>    