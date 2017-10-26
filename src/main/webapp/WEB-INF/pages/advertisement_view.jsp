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


    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


</head>
<body>

<div align="center">
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
                <a id="order" class="navbar-brand" href="/search_hot_snacks">Сделать заказ</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">

                <ul class="nav navbar-nav navbar-right">

                    <c:if test="${login ne null}">
                        <li> <a>Ваш логин: ${login}</a></li>
                        <li><a href="/logout">Выход</a></li>
                    </c:if>


                </ul>



                <form class="navbar-form navbar-left" role="search" action="/advertisement_viewed_${photo_id}" method="post">


                    <button type="submit" class="btn btn-primary">Предыдущая реклама</button>
                </form>
                <form class="navbar-form navbar-left" action="/advertisement_view_${photo_id}" method="post">


                    <button type="submit"  class="btn btn-primary">Следующая реклама</button>

                </form>


            </div>
        </div>
    </nav>

    <br/><br/><img src="/photo/advertisement/${photo_id}"/>
</div>
</body>
</html>