<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Доступ запрещен</title>

    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <link href="resources/css/cover.css" rel="stylesheet">

</head>
<body>


<div class="site-wrapper">

    <div class="site-wrapper-inner">

        <div class="cover-container">

            <div class="masthead clearfix">
                <div class="inner">
                    <h3 class="masthead-brand"></h3>
                    <nav class="nav nav-masthead">
                        <a class="nav-link active" href="/">Главная</a>
                        <a class="nav-link active" href="/search_hot_snacks">Меню</a>

                    </nav>
                </div>
            </div>

            <div class="inner cover">
                <h1 class="cover-heading">Для пользователя "${login}" доступ на эту страничку запрещен</h1>
                <p class="lead">
                    <a href="/logout" class="btn btn-lg btn-secondary">Выход</a>
                </p>
            </div>

            <div class="mastfoot">
                <div class="inner">
                    <p>С уважением,</p>
                    <p>администрация ресторана "Название ресторана"</p>
                </div>
            </div>

        </div>

    </div>

</div>


</body>
</html>
