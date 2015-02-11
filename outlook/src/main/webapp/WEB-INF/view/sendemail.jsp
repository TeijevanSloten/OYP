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
        <div id="attachmentbutton"></div>
        <div class="form-group">
            <textarea placeholder="Add a message here."  id="textareaInput" class="form-control" style="" rows="20" name="message"></textarea> <br>
        </div>
    </div>
</form>
<script>
    var attachmentsint = 1;
    function myFunction() {
        $("#attachmentbutton").before(
                '<span style="display: inline; width: 300px;">' + 
                '<input type="file" name="attachment[' + attachmentsint + ']"><div>remove</div></span><br>');
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