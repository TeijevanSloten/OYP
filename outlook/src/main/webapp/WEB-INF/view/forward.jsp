<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Your Messages:</h1>
        <a href="${pageContext.request.contextPath}/showmail?id=${mail.getMessageNumber()}">Back</a><br>
        <form action="sendforward" method="post">
            <label>To:</label><br>
            <input type="email" name="to" style="width: 500px;"><br>
            <label>Message:</label><br>
            <textarea style="width: 500px; height: 50px;" name="message">Type message here</textarea> <br><br>
            <button type="submit">Submit</button>
            <input type="hidden" name="messageid" value="${mail.getMessageNumber()}">
        </form>
        <hr>
        From: ${mail.getFrom()} <br>
        Subject: ${mail.getSubject()}
        <hr >
        ${mail.getContent()}

    </body>
</html>