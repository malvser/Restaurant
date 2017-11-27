<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Ожидание заказа</title>


    <link rel="stylesheet" href="resources/css/bootstrap.min.css">


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
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">

                <c:if test="${login ne null}">
                    <li> <a>Ваш логин: ${login}</a></li>
                    <li><a href="/logout">Выход</a></li>
                </c:if>

            </ul>
            <form class="navbar-form navbar-left" action="/advertisement_view" method="post">
                <input hidden name="IdDishes" value="${Id}">
                <div class="btn-group" role="group">
                    <input type="submit" class="btn btn-info" value="Загляните сюда!!!">
                </div>

            </form>
            <form class="navbar-form navbar-left" role="group" action="/search_hot_snacks">
                <div class="btn-group" role="group">
                    <input type="submit" class="btn btn-success" value="Сделать еще заказ">
                </div>

            </form>



        </div>
    </div>
</nav>


<div class="col">

    <form >
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <br /> <br /> <br />

                <p align="center">
                    <font size="7" color="#6a5acd" face="Monotype Corsiva">
                        <em> Заказ принят! Ожидайте, пожалуйста! </em></font>
                </p>

                <tr>
                    <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                        Блюдо</em></font>
                    </th>
                    <th width="15%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                        Вес</em></font>
                    </th>
                    <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                        Стоимость</em></font>
                    </th>
                    <th width="30%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                        Время готовки (мин.)</em></font>
                    </th>
                    <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                        Фото</em></font>
                    </th>


                </tr>
                </thead>
                <form class="form-control" enctype="multipart/form-data" >
                    <c:forEach var="item" items="${dishesArrayId}">
                        <tr>

                            <td><c:out value="${item.name}"/>
                            <td><c:out value="${item.weight}"/>
                            <td><c:out value="${item.cost}"/>
                            <td><c:out value="${item.duration}"/>
                            <td>
                                <div id="aaa"
                                     style="border: 0px; display: inline-block; position: relative; overflow: hidden;">
                                    <a href="#" class="thumbnail" style="">
                                        <img width="60" height="60" src="/photo/${item.id}"/>
                                    </a>
                                </div>
                            </td>
                            </td>
                        </tr>
                    </c:forEach>
                </form>
            </table>
        </div>
    </form>



</div>



</body>
</html>

