<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Malov Serg</title>


    <link rel="stylesheet" href="resources/css/bootstrap.min.css">

    <link href="resources/style.css" rel="stylesheet">

</head>
<body>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Navigation</span>

            </button>

            <a class="navbar-brand" href="/">Главная</a>
            <a class="navbar-brand" href="/admin">Страничка администратора</a>
            <a class="navbar-brand" href="/enter_cook_admin">Обновить данные</a>


        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li> <a>Ваш логин: ${login}</a></li>
                <li><a href="/logout">Выход</a></li>
            </ul>

        </div>
    </div>
</nav>


<div  class="carousel-control"> <!-- class="container-fluid"  "carousel-caption" -->
    <div class="row">
        <div class="col-sm-3 col-md-12 sidebar">


            <ul class="nav nav-sidebar">

                <li><a> <b>Количество новых заказов: <c:out value="${count_order}"/> шт.</b></a></li>
                <li><a> <b>Общее время приготовления новых заказов: <c:out value="${totalCookingTimeNewOrder}"/> мин.</b></a></li>
                <li><a><b>Количество блюд в новых заказах: <c:out value="${countDishesNewOrder}"/> шт.</b></a></li>
                <li><a><b>Количество готовящихся сейчас заказов: <c:out value="${count_cooking_order}"/> шт.</b></a></li>
                <li><a> <b>Общее время приготовления готовящихся заказов: <c:out value="${totalCookingTime}"/> мин.</b></a></li>
                <li><a><b>Количество блюд готовящихся заказов: <c:out value="${countDishesCookingOrder}"/> шт.</b></a></li>


            </ul>



        </div>

        <div class="col-sm-8 col-sm-offset-5 col-md-10 col-md-offset-2 main">



        </div>

    </div>
</div>



</body>
</html>
