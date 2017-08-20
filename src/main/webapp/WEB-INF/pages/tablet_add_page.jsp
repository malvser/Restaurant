<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
    <title>Новый стол</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form enctype="multipart/form-data" role="form" class="form-horizontal" action="/tablet/add" method="post">
        <h3>Новый стол</h3>

        <input class="form-control form-group" type="text" name="number" placeholder="Номер стола">
        <p></p>
        <input type="submit" class="btn btn-primary" value="Добавить">
    </form>
</div>


</body>
</html>
