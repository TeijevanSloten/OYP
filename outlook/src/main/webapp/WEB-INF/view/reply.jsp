<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Your Messages:</h1>
<a href="${pageContext.request.contextPath}/showmail?id=${mail.getMessageid()}">Back</a>
<form action="sendreply" method="post" enctype="multipart/form-data">
    <label>Subject</label><br>
    <input type="text" name="subject" style="width: 500px;"><br>
    <label>Message:</label><br>
    <textarea style="width: 500px; height: 50px;" name="message">Type message here</textarea> <br><br>
    <input type="file" name="attachment[1]"> <br>
    <button type="button" onclick="myFunction()" id="attachmentbutton">Add New Attachment</button><br>
    <button type="submit">Submit</button>
    <input type="hidden" name="messageid" value="${mail.getMessageid()}">
</form>
<hr>
From: ${mail.getFromemail()} <br>
Subject: ${mail.getSubject()}
<hr >
${mail.getContent()}

<script>
    var attachmentsint = 2;
    function myFunction() {
        $("#attachmentbutton").before('<input type="file" name="attachment['+attachmentsint+']"><br>');
        attachmentsint++;
    }
</script>