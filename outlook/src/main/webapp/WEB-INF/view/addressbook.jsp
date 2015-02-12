<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-3">
    <h2>Addressbook</h2>
    <form action="address" method="post">
        <div class="form-group">
            <label>Full name;</label>
            <input type="text" name="fullname" class="form-control" placeholder="Full name">
        </div>
        <div class="form-group">
            <label>Email address:</label>
            <input type="email" name="email" class="form-control" placeholder="Email-address">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-success pull-right">
                Submit
            </button> 
        </div>
    </form>
</div>
<div class="col-md-9">
    <h2>&nbsp;</h2>
    <table class="table table-hover">
      <thead>
        <tr>
          <th>Full name</th>
          <th>Email</th>
        </tr>
      </thead>
      <tbody>
        
             <c:forEach var="address" items="${addresses}">
                <tr>
                    <th scope="row">${address.getName()}</th>
                    <td>${address.getEmail()}</td>
                </tr>
             </c:forEach>
      </tbody>
    </table>
</div>