<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Новое блюдо</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form enctype="multipart/form-data" role="form" class="form-horizontal" action="/dish/add" method="post">
        <h3>Новое блюдо</h3>

        <input class="form-control form-group" type="text" name="name" placeholder="Название">
        <input class="form-control form-group" type="text" name="cost" placeholder="Стоимость">
        <input class="form-control form-group" type="text" name="weight" placeholder="Вес">
        <input class="form-control form-group" type="text" name="discount" placeholder="Скидка">
        <input class="form-control form-group" type="text" name="duration" placeholder="Время приготовления (мин.)">
        <input class="form-control form-group" type="text" name="type" placeholder="Тип блюда">
        <input type="file" name="photo">
        <p></p>
        <input type="submit" class="btn btn-primary" value="Добавить">
    </form>
</div>

<!-- enctype="multipart/form-data" -->
</body>
</html>
