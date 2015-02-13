<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="send" method="post" enctype="multipart/form-data">
    <div class="col-md-3">
        <h3>Compose email:</h3>
        <div class="form-group">
            <label>To:</label><br>
            <select class="select-to email-select" name="to" multiple>
                <c:forEach var="address" items="${addresses}">
                    <c:if test="${address.getName() != ''}">
                    <option value="${address.getEmail()}">${address.getName()}</option>
                    </c:if>
                    <c:if test="${address.getName() == ''}">
                    <option value="${address.getEmail()}">${address.getEmail()}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>CC:</label><br>
            <select class="select-cc email-select" name="CC" multiple>
                <c:forEach var="address" items="${addresses}">
                    <c:if test="${address.getName() != ''}">
                    <option value="${address.getEmail()}">${address.getName()}</option>
                    </c:if>
                    <c:if test="${address.getName() == ''}">
                    <option value="${address.getEmail()}">${address.getEmail()}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>BCC:</label><br>
            <select class="select-bcc email-select" name="BCC" multiple>
                <c:forEach var="address" items="${addresses}">
                    <c:if test="${address.getName() != ''}">
                    <option value="${address.getEmail()}">${address.getName()}</option>
                    </c:if>
                    <c:if test="${address.getName() == ''}">
                    <option value="${address.getEmail()}">${address.getEmail()}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="col-md-9">
        <div class="form-group">
            <input class="subject" placeholder="Add a Subject" type="text" name="subject">
            <br>
        </div>
        <div id="attachmentbutton"></div>
        <div class="form-group">
            <textarea placeholder="Add a message here."  id="textareaInput" class="form-control" style="" rows="10" name="message"></textarea>
            <br>
        </div> 
        <label>From: </label> <c:out value="${mail.getFromemail()}"></c:out>
        <br>
        <label>Subject:</label>  <c:out value="${mail.getSubject()}"></c:out>
        <br>
        <label>Message:</label>
        <br>
        <pre>${mail.getContent()}</pre>
    </div>
    <input type="hidden" name="messageid" value="${mail.getMessageid()}">
</form>
<script src="${initParam.jsPath}composemail.js"></script>