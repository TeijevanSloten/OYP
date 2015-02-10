<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
    .email-ul {
        border: 0;
        border-top: 1px solid #bbb;
        position: relative; 
        left: -15px;
        height: 800px !important;
        overflow-y: scroll;
    }
    .email-list {
        border: 0;
    }
    .email-list:hover {
        background: rgb(207, 207, 207);
        border-radius: 0;
    }
    .title {
       
        padding-bottom: 5px;
        margin-right: 15px;
    }
    .mail-header {
        margin-bottom: 10px;
    }
    
</style>
<div class="row">
    <div class="col-md-3" >
        <h2 class="title">Messages:</h2>
        <ul class="list-group email-ul">  
            <c:forEach var="email" items="${messages}">
                <a href="showmail?id=${email.getMessageid()}" style="color: black; text-decoration: none;">
                    <li class="list-group-item email-list">
                        <span class="pull-right" >${email.getSimpleDate()}</span>
                        ${email.getSubject()}
                    </li>
                </a>
            </c:forEach>
        </ul>
    </div>

    <div class="col-md-9" style="padding-left: 0;">
        <c:if test="${mail != null}">
        <h2 class="title">${mail.getSubject()}</h2>
        <div class="row mail-header">
            <div class="col-md-1">
                <span class="badge">From: </span> 
            </div>
            <div class="col-md-11">
                ${mail.getFromemail()}
            </div>
        </div>
        <div class="row mail-header">
            <div class="col-md-1">
                <span class="badge">Subject: </span> 
            </div>
            <div class="col-md-11">
               ${mail.getSubject()}
            </div>
        </div>
        <div class="row mail-header">
            <div class="col-md-1">
                <span class="badge">Sent: </span> 
            </div>
            <div class="col-md-11">
               ${mail.getDate()}
            </div>
        </div>
        <c:if test="${attachments.size() > 0}">
        <div class="row mail-header">
            <div class="col-md-1">
                <span class="badge">Attachments: </span> 
            </div>
            <div class="col-md-11">
                <c:forEach var="attachment" items="${attachments}">
                <a href="D:/projecten/Attachments/${attachment.getEmailid()}-${attachment.getFilename()}" target="_blank">
                    ${attachment.getFilename()}
                </a> <br>
            </c:forEach>
            </div>
        </div>
        </c:if>
        <pre style="min-height: 100px !important">${mail.getContent()}</pre>
        </c:if>
        <c:if test="${mail == null}">
            <h2>
                No message selected
            </h2>
        </c:if>
    </div>       
</div>