<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Type here your email:</h1>
<hr>
<form action="send" method="post" enctype="multipart/form-data">
    <label>To:</label><br>
    <input type="email" name="to" style="width: 500px;"><br>
    <label>Message:</label><br>
    <textarea style="width: 500px; height: 50px;" name="message">Type message here</textarea> <br>
    <input type="file" name="attachment[1]"> <br>
    <button type="button" onclick="myFunction()" id="attachmentbutton">Add New Attachment</button><br>
    <button type="submit">Submit</button>
</form>

<script>
    var attachmentsint = 2;
    function myFunction() {
        $("#attachmentbutton").before('<input type="file" name="attachment['+attachmentsint+']"><br>');
        attachmentsint++;
    }
</script>