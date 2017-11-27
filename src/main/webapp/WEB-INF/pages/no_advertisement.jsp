<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Отсутствие рекламы</title>

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
                        <c:if test="${login ne null}">
                            <a class="nav-link active" href="/">Главная</a>
                        </c:if>
                        <c:if test="${login eq null}">
                            <a class="nav-link active" href="/login">Главная</a>
                        </c:if>

                        <a class="nav-link active" href="/search_hot_snacks">Меню</a>

                    </nav>
                </div>
            </div>

            <div class="inner cover">
                <h1 class="cover-heading">УПППСССССССС.. - :)  К сожалению, сейчас Вам нечего показать, зайдите сюда в другой раз и мы Вам покажем что-то интересное! :)</h1>

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
