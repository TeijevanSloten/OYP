<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid">   
    <div class="row">        
        
        <div  class="col-md-6 col-md-offset-3">

            <h1>Your Messages:</h1>
            <a href="${pageContext.request.contextPath}/showmail?id=${mail.getMessageid()}">Back</a><br>
            <form action="sendforward" method="post" enctype="multipart/form-data">
                <div class="form-group">                
                <label for="emailInput" class="label label-primary">To: </label><br>
                <input id="emailInput" class="form-control" type="email" name="to" style=""><br>
                </div>
                <div class="form-group">
                <label class="label label-primary" >Subject:</label><br>
                <input class="form-control" type="text" name="subject" style=""><br>
                </div>
                <div class="form-group">
                <label class="label label-primary" >Message:</label><br>
                <textarea class="form-control" style="" name="message">Type message here</textarea> <br><br>
                </div>
                <div class="form-group">
                <input type="file" name="attachment[1]"> <br>
                <button type="button" onclick="myFunction()" id="attachmentbutton">Add New Attachment</button><br>
                </div>
                <div class="form-group">
                <button type="submit">Submit</button>
                <input type="hidden" name="messageid" value="${mail.getMessageid()}">
                </div>
            </form>
            <hr>
            From: ${mail.getFromemail()} <br>
            Subject: ${mail.getSubject()}
            <hr >
            ${mail.getContent()}

        </div>
    </div>        
</div> 
            
            
<script>
    var attachmentsint = 2;
    function myFunction() {
        $("#attachmentbutton").before('<input type="file" name="attachment['+attachmentsint+']"><br>');
        attachmentsint++;
    }
</script>