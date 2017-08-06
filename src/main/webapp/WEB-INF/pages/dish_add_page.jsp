<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 26.07.2017
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>New Dish</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form enctype="multipart/form-data" role="form" class="form-horizontal" action="/dish/add" method="post">
        <h3>New dish</h3>

        <input class="form-control form-group" type="text" name="name" placeholder="Name">
        <input class="form-control form-group" type="text" name="cost" placeholder="Cost">
        <input class="form-control form-group" type="text" name="weight" placeholder="Weight">
        <input class="form-control form-group" type="text" name="discount" placeholder="Discount">
        <input class="form-control form-group" type="text" name="duration" placeholder="Duration">
        <input type="file" name="photo">
        <p></p>
        <input type="submit" class="btn btn-primary" value="Add">
    </form>
</div>

<!-- enctype="multipart/form-data" -->
</body>
</html>
