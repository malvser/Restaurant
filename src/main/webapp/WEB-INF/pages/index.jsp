<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Вход в профиль</title>


    <link rel="stylesheet" href="resources/css/bootstrap.min.css">

</head>
<body>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">"Название ресторана"</a>
            <a class="navbar-brand" href="/admin">Админ</a>
            <a class="navbar-brand" href="/enter_cook">Повар</a>
            <a class="navbar-brand" href="/user">Клиент</a>


        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <form class="navbar-form navbar-left" action="/advertisement_view" method="post">
                <input hidden name="IdDishes" value="${Id}">
                <div class="btn-group" role="group">
                    <input type="submit" class="btn btn-info" value="Загляните сюда!">
                </div>

            </form>
            <ul class="nav navbar-nav navbar-right">
                <li> <a>Ваш логин: ${login}</a></li>
                <li><a href="/logout">Выйти</a></li>
            </ul>

        </div>

    </div>
</nav>

<div class="jumbotron">
    <div class="container">

        <form class="form-signin">


            <p></p>
            <p>Пользователь под ролью ADMIN сможет зайти только по ссылке "Админ" (не сможет зайти по ссылкам "Клиент", "Повар")</p>
            <p>Пользователь под ролью USER сможет зайти только по ссылке "Клиент" (не сможет зайти по ссылкам "Админ", "Повар")</p>
            <p>Пользователь под ролью COOK сможет зайти только по ссылке "Повар" (не сможет зайти по ссылкам "Клиент", "Админ")<p></p>
            </p>
            <p><a class="btn btn-primary btn-lg" href="/search_hot_snacks" role="button">Меню</a></p>
        </form>
    </div>

</div>



</body>
</html>
