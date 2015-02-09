<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container-fluid">   
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 ">

        <h1>Your Messages:</h1>
        <a href="${pageContext.request.contextPath}/showallmail">Back</a> |
        <a href="${pageContext.request.contextPath}/forward?id=${mail.getMessageid()}">Forward</a> |
        <a href="${pageContext.request.contextPath}/reply?id=${mail.getMessageid()}">Reply</a>
        <hr>
        ${mail.getFromemail()} ||   ${email.getSubject()}
        <hr >
        ${mail.getContent()}
        </div>
    </div>
</div>