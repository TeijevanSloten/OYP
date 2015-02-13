<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
    <div class="col-md-3">
        <h2>Messages:</h2>
        <ul class="list-group email-ul">  
            <c:forEach var="email" items="${messages}">
                <a href="showmail?id=${email.getMessageid()}" style="color: black; text-decoration: none;">
                    <li class="list-group-item email-list">
                        <span class="pull-right" >${email.getSimpleDate()}</span>
                        <c:out value="${email.getSubject()}"></c:out>&nbsp;
                        </li>
                    </a>
            </c:forEach>
        </ul>
    </div>

    <div class="col-md-9">
        <c:if test="${mail != null}">
            <h2> 
                <c:out value="${mail.getSubject()}"></c:out>
                <c:if test="${mail.getSubject() == null}">
                    &nbsp;
                </c:if>
            </h2>
            <div class="row mail-header">
                <div class="col-md-1">
                    <span class="badge">From: </span> 
                </div>
                <div class="col-md-11">
                    <c:out value="${mail.getFromemail()}"></c:out>
                    </div>
                </div>
                <div class="row mail-header">
                    <div class="col-md-1">
                        <span class="badge">Subject: </span> 
                    </div>
                    <div class="col-md-11">
                    <c:out value="${mail.getSubject()}"></c:out>
                    </div>
                </div>
                <div class="row mail-header">
                    <div class="col-md-1">
                        <span class="badge">Sent: </span> 
                    </div>
                    <div class="col-md-11">
                    ${mail.getSimpleDate()}
                </div>
            </div>
            <c:if test="${attachments.size() > 0}">
                <div class="row mail-header">
                    <div class="col-md-1">
                        <span class="badge">Attachments: </span> 
                    </div>
                    <div class="col-md-11">
                        <c:forEach var="attachment" items="${attachments}">
                            <a href="${pageContext.request.contextPath}/download?id=${mail.getMessageid()}-${attachment.getFilename()}">${attachment.getFilename()}</a>   

                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <pre id="mail-text" >${mail.getContent()}</pre> 
        </c:if>
        <c:if test="${mail == null}">
            <h2>
                No message selected
            </h2>
        </c:if>
    </div>       
</div>