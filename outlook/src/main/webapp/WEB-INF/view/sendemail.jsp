<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container-fluid">   
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 ">
    
        <h1>Type here your email:</h1>
        <hr>

        <form  action="send" method="post"  enctype="multipart/form-data" >
            <div class="form-group">
            <label for="emailInput" class="label label-primary">To:</label>            
                <input id="emailInput" type="email" name="to" placeholder="email" class="form-control" style="">
            </div>
            <div class="form-group">
            <label for="subjectInput" class="label label-primary">Subject:</label>            
                <input id="subjectInput" type="subject" name="subject" placeholder="subject" class="form-control" style="">
            </div> 
            
            <div class="form-group">
            <label for="textareaInput" class="label label-primary" >Message:</label><br>
                 <textarea id="textareaInput" style="" class="form-control" name="message" rows="10" placeholder="Type message here"></textarea> <br><br>
            </div>
            <div class="form-group">
                <label for="exampleInputFile">File input</label>
                <input type="file" name="attachment[1]"> <br>
                <button type="button" onclick="myFunction()" id="attachmentbutton">Add New Attachment</button><br>
                    
            </div>
                <button class="btn btn-primary" type="submit">Submit</button>
        </form>
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