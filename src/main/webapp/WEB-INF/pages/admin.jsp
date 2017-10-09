<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>

    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">

    <title>Malov Serg</title>


    <link rel="stylesheet" href="resources/css/bootstrap.min.css">

    <link href="resources/style.css" rel="stylesheet">


</head>
<body>


<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a id="main" class="navbar-brand" href="/">Главная</a>
            <a id="name" class="navbar-brand" href="/name">"Название ресторана"</a>


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

                <li><a href="/statistic_cooked_order"> <b>Приготовленные заказы</b></a></li>
                <li><a href="/statistic_viewed_advertisement"><b>Показанная реклама</b></a></li>
                <li><a href="/statistic_no_advertisement"><b>Отсутствувала реклама</b></a></li>
                <li><a href="/advertisement/add_page"><b>Добавить рекламу</b></a></li>
                <li><a href="/advertisementList"><b>Список рекламы</b></a></li>
                <li><a href="/cook_add"><b>Добавить повара</b></a></li>
                <li><a href="/cookList"><b>Список поваров</b></a></li>
                <li><a href="/add_tablet"><b>Добавить стол</b></a></li>
                <li><a href="/tabletList"><b>Список столов</b></a></li>
                <li><a href="/add_dish"><b>Добавить блюдо</b></a></li>
                <li><a href="/dishesList"><b>Список блюд</b></a></li>
                <li><a href="/register_admin"><b>Создать пользователя</b></a></li>

            </ul>



        </div>

        <div class="col-sm-8 col-sm-offset-5 col-md-10 col-md-offset-2 main">



        </div>

    </div>
</div>


</body>
</html>

