<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="send" method="post" enctype="multipart/form-data">
    <div class="col-md-3">
        <h3>Compose email:</h3>
        <div class="form-group">
            <label>To:</label><br>
            <select class="select-to" style="width: 100%;" name="to" multiple>
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
            <select class="select-cc" style="width: 100%;" name="CC" multiple>
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
            <select class="select-bcc" style="width: 100%;" name="BCC" multiple>
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
            <input class="" placeholder="Add a Subject" type="text" name="subject" style="font-size: 24px;border:0;border-bottom: 1px solid #ccc;width: 100%; margin-top: 15px;"><br>
        </div>
        <div class="form-group">
            <textarea placeholder="Add a message here."  id="textareaInput" class="form-control" style="" rows="10" name="message"></textarea> <br>
        </div>
        <div class="form-group">
            <input type="file" name="attachment[1]"> <br>
            <div id="attachmentbutton"></div>
        </div>
    </div>
</form>
<!--
<div class="col-md-6 col-md-offset-3">
    <h1>Compose email:</h1>
    <hr>
    <form action="send" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label class="label label-primary" for="emailInput">To:</label><br>
            <input id="emailInput" class="form-control" placeholder="email" type="text" name="to">
        </div>
        <div class="form-group">
            <label class="label label-primary" for="emailInput">CC:</label><br>
            <input id="emailInputCC" class="form-control" placeholder="email" type="text" name="CC">
        </div>
        <div class="form-group">
            <label class="label label-primary" for="emailInput">BCC:</label><br>
            <input id="emailInputBCC" class="form-control" placeholder="email" type="text" name="BCC">
        </div>
        <div class="form-group">
            <label  class="label label-primary" for="subjectInput">Subject:</label><br>
            <input id="subjectInput" class="form-control" placeholder="Subject" type="text" name="subject"><br>
        </div>
        <div class="form-group">
            <label class="label label-primary" for="textareaInput">Message:</label><br>
            <textarea placeholder="Type message here"  id="textareaInput" class="form-control" style="" rows="10" name="message"></textarea> <br>
        </div>
        <div class="form-group">
            <input type="file" name="attachment[1]"> <br>
            <button type="button" class="btn btn-primary" onclick="myFunction()" id="attachmentbutton">Add New Attachment</button><br>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-danger" >Submit</button>
        </div>
    </form>
</div>-->
<script>
    var attachmentsint = 2;
    function myFunction() {
        $("#attachmentbutton").before('<input type="file" name="attachment[' + attachmentsint + ']"><br>');
        attachmentsint++;
    }
    $(document).ready(function () {
        $(".select-to").select2({
            minimumInputLength: 1,
            tags: true,
            placeholder: "To",
            tokenSeparators: [',', ' ']
        });
        $(".select-cc").select2({
            minimumInputLength: 1,
            tags: true,
            placeholder: "CC",
            tokenSeparators: [',', ' ']
        });
        $(".select-bcc").select2({
            minimumInputLength: 1,
            tags: true,
            placeholder: "BCC",
            tokenSeparators: [',', ' ']
        });
        
    });
</script>