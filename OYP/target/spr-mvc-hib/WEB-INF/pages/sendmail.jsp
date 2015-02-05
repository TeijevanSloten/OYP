<%@ include file="base/header.jsp" %>
<h1>Type here your email:</h1>
<hr>
<form action="sendmail" method="post">
    <label>To:</label><br>
    <input type="email" name="to" style="width: 500px;"><br>
    <label>Message:<label><br>
    <textarea style="width: 500px; height: 50px;" name="message">Type message here</textarea> <br><br>
    <button type="submit">Submit</button>
</form>
<%@ include file="base/footer.jsp" %>