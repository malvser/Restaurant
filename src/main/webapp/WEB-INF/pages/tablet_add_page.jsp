<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<html>
<head>
    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Новый стол</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
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
