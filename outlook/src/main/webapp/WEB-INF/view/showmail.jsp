<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <div class="col-lg-2 col-lg-offset-0 ">

        <h1>Messages:</h1>
        <ul class="list-group">
            <c:forEach var="email" items="${messages}">
                <li class="list-group-item">
                <a  href="showmail?id=${email.getMessageid()}">
                    ${email.getSubject()}
                </a>
                </li>
            </c:forEach>
       </ul>
    </div>

    <div class="col-lg-10  ">

        <h1>Your Messages:</h1>
        <a class="btn btn-primary"  href="${pageContext.request.contextPath}/showallmail">Back</a> 
        <a class="btn btn-primary"  href="${pageContext.request.contextPath}/forward?id=${mail.getMessageid()}">Forward</a> 
        <a class="btn btn-primary"  href="${pageContext.request.contextPath}/reply?id=${mail.getMessageid()}">Reply</a>
        <hr>
        <div class="form-group">
            <span class="badge">From: </span> ${mail.getFromemail()}
        </div>
        <div class="form-group">
            <span class="badge">Subject: </span> ${mail.getSubject()} 
        </div>
        <div class="form-group">
            <span class="badge"> Sent: </span> ${mail.getDate()} 

        </div>
        <hr>
        <div class="from-group">
            ${mail.getContent()}
        </div>
        <hr>



        <h4>Attachments</h4>
        <c:forEach var="attachment" items="${attachments}">
            <a href="${pageContext.request.contextPath}Attachments/${attachment.getEmailid()}-${attachment.getFilename()}" target="_blank">
                ${attachment.getFilename()}
            </a> <br>
        </c:forEach>


    </div>       
