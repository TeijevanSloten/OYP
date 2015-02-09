<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Hello World!</h1>
<h1>Type here your email:</h1>
<hr>
<form action="send" method="post">
    <label>To:</label><br>
    <input type="email" name="to" style="width: 500px;"><br>
    <label>Message:</label><br>
    <textarea style="width: 500px; height: 50px;" name="message">Type message here</textarea> <br><br>
    <button type="submit">Submit</button>
</form>