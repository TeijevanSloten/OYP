<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div  class="col-md-6 col-md-offset-3">
    <h1>Compose Reply:</h1>
    <hr>
    <form action="send" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label class="label label-primary" for="emailInput">To:</label><br>
            <input id="emailInput" class="form-control" type="email" name="toempty" value="${mail.getFromemail()}" readonly>
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
        <input type="hidden" name="messageid" value="${mail.getMessageid()}">
    </form>
    <hr>
    <label>Original message:</label><br>
    <pre>${mail.getContent()}</pre>
</div>

<script>
    var attachmentsint = 2;
    function myFunction() {
        $("#attachmentbutton").before('<input type="file" name="attachment['+attachmentsint+']"><br>');
        attachmentsint++;
    }
</script>