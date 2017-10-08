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

    <!-- <link rel='stylesheet' href='resources/css/bootstrap.min.css' type='text/css' media='all'> -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <!-- Bootstrap core CSS -->
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
            <a class="navbar-brand" href="/login">"Название ресторана"</a>


        </div>
        <div id="navbar" class="navbar-collapse collapse">

            <form action="/spring_security_check" class="navbar-form navbar-right" method="post">
                <c:if test="${param.error ne null}">
                   <p  style="color: red" > <b>Не правильный логин или пароль!</b></p>
                </c:if>

                <!--  -->

               <div class="form-group">
                    <input type="text" name="login" placeholder="Login" class="form-control" >
                </div>
                <div class="form-group">
                    <input type="password" name="password" placeholder="Password" class="form-control">
                </div>
                <button type="submit" class="btn btn-success">Войти</button>


            </form>

            <form action="/register" class="navbar-form navbar-right">
                <button type="submit" class="btn btn-success">Регистрация</button>
            </form>

        </div><!--/.navbar-collapse -->
    </div>
</nav>

<div class="jumbotron">
    <div class="container">

        <form class="form-signin">

            <h1>Hello, world!</h1>
            <p>This is a template for a simple marketing or informational website. It includes a large callout called a
                jumbotron and three supporting pieces of content. Use it as a starting point to create something more
                unique.</p>
            <p>This is a template for a simple marketing or informational website. It includes a large callout called a
                jumbotron and three supporting pieces of content. Use it as a starting point to create something more
                unique.</p>
            <p>This is a template for a simple marketing or informational website. It includes a large callout called a
                jumbotron and three supporting pieces of content. Use it as a starting point to create something more
                unique.</p>
            <p><a class="btn btn-primary btn-lg" href="/search_hot_snacks" role="button">Меню</a></p>
        </form>
    </div>

</div>


</body>
</html>
