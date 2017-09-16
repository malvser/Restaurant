<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
    <title>New Dish</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form enctype="multipart/form-data" role="form" class="form-horizontal" action="/advertisement/add" method="post">
        <h3>Новая реклама</h3>

        <input class="form-control form-group" type="text" name="name" placeholder="Имя">
        <input class="form-control form-group" type="text" name="initialAmount" placeholder="Стоимость">
        <input class="form-control form-group" type="text" name="amount" placeholder="Оставшееся количество оплаченных показов">
        <input class="form-control form-group" type="text" name="total_amount" placeholder="Общее количество оплаченных показов">
        <input type="file" name="photo">
        <p></p>
        <input type="submit" class="btn btn-primary" value="Add">
    </form>
</div>


</body>
</html>
