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

    <!-- подтверждающее окно -->
    <link href='https://fonts.googleapis.com/css?family=Cuprum&amp;subset=latin' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="resources/jquery.confirm/jquery.confirm/jquery.confirm.css"/>


    <link rel="stylesheet" href="resources/css/bootstrap.min.css">

    <link href="resources/style.css" rel="stylesheet">

    <script type="text/javascript" src="resources/js/jquery-3.2.1.js"></script>

</head>
<body>

<!-- window -->
<div class="modal fade" id="image-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <div class="modal-lg">Photo</div>
            </div>
            <div class="modal-body">
                <img class="img-responsive center-block" src="" alt="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

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
            <a id="add_contact" class="navbar-brand" href="/">Главная</a>

            <form class="navbar-form navbar-left" method="post">
                <button class="btn btn-success" type="button" id="modal" >Сделать заказ</button>
            </form>


        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/search_hot_snacks">Сброс поиска</a></li>
                <li><a href="/logout">Выход</a></li>
            </ul>

            <form class="navbar-form navbar-right" role="search" action="/search_dishes" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="pattern" placeholder="Поиск">
                </div>
                <button type="submit" class="btn btn-primary">Поиск</button>

            </form>

        </div>
    </div>
</nav>


<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">

                <ul class="nav nav-sidebar">
                    <li <c:if test="${pattern eq 'Все блюда'}">
                        class="active"
                    </c:if>><a href="/search_all"><b>Все блюда</b></a></li>

                    <li <c:if test="${pattern eq 'Горячии закуски'}">
                        class="active"
                    </c:if>><a href="/search_hot_snacks"><b>Горячии закуски</b></a></li>

                    <li <c:if test="${pattern eq 'Холодные закуски'}">
                        class="active"
                    </c:if>><a href="/search_cold_snacks"><b>Холодные закуски</b> </a></li>

                    <li <c:if test="${pattern eq 'Салаты'}">
                        class="active"
                    </c:if>><a href="/search_salads"><b>Салаты</b></a></li>

                    <li <c:if test="${pattern eq 'Первые блюда'}">
                        class="active"
                    </c:if>><a href="/search_first_meal"><b>Первые блюда</b></a></li>
                </ul>
                <ul class="nav nav-sidebar">
                    <li <c:if test="${pattern eq 'Алкогольные напитки'}">
                        class="active"
                    </c:if>><a href="/search_alcoholic_beverages"><b>Алкогольные напитки</b></a></li>

                    <li <c:if test="${pattern eq 'Гарниры'}">
                        class="active"
                    </c:if>><a href="/search_garnishes"><b>Гарниры</b></a></li>

                    <li <c:if test="${pattern eq 'Напитки'}">
                        class="active"
                    </c:if>><a href="/search_beverages"><b>Напитки</b></a></li>

                    <li <c:if test="${pattern eq 'Супы'}">
                        class="active"
                    </c:if>><a href="/search_supe"><b>Супы</b></a></li>

                </ul>



        </div>


        <div class="col-sm-8 col-sm-offset-5 col-md-10 col-md-offset-2 main">
            <p align="center">
                <font size="7" color="#6a5acd" face="Monotype Corsiva"><em>
                    Выберите желаемое блюдо, отметьте его и закажите </em></font>
            </p>
            <form action="/order"  method="post"> <!-- action="/order" -->
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th></th>

                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Имя</em></font>
                            </th>
                            <th width="25%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Вес</em></font>
                            </th>
                            <th width="25%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Стоимость</em></font>
                            </th>
                            <th width="30%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Скидка</em></font>
                            </th>
                            <th width="30%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Фото</em></font>
                            </th>

                        </tr>
                        </thead>
                        <form class="form-control" enctype="multipart/form-data" action="/order" method="post">
                            <c:forEach var="item" items="${dishes}">
                                <tr>
                                    <td><input type="checkbox" align="center" id="checkbox_${item.id}" name="toOrder[]" value="${item.id}"/>
                                    </td>

                                    <td><c:out value="${item.name}"/>
                                    <td><c:out value="${item.weight}"/>
                                    <td><c:out value="${item.cost}"/>
                                    <td><c:out value="${item.discount}"/>
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
    </div>
</div>


<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/photo.js"></script>
<script src="resources/jquery.confirm/js/script.js"></script>
<script src="resources/jquery.confirm/jquery.confirm/jquery.confirm.js"></script>



</body>
</html>

