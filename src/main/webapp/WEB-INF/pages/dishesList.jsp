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
                <div class="modal-lg"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                    Фото</em></font>
                </div>
            </div>
            <div class="modal-body">
                <img class="img-responsive center-block" src="" alt="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal">Закрыть</button>
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
            <a id="main" class="navbar-brand" href="/">Главная</a>


        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/dishesList">Сброс поиска</a></li>
                <li><a>Ваш логин: ${login}</a></li>
                <li><a href="/logout">Выход</a></li>
            </ul>

            <form class="navbar-form navbar-right" role="search" action="/search_dishes" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="pattern" placeholder="Название блюда">
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

                <li><a href="/enter_cook_admin"> <b>Просмотр состояния заказов</b></a></li>
                <li><a href="/statistic_cooked_order"> <b>Приготовленные заказы</b></a></li>
                <li><a href="/statistic_viewed_advertisement"><b>Показанная реклама</b></a></li>
                <li><a href="/statistic_no_advertisement"><b>Отсутствувала реклама</b></a></li>
                <li><a href="/advertisement_add_page"><b>Добавить рекламу</b></a></li>
                <li><a href="/advertisementList"><b>Список рекламы</b></a></li>
                <li><a href="/add_tablet"><b>Добавить стол</b></a></li>
                <li><a href="/tabletList"><b>Список столов</b></a></li>
                <li><a href="/add_dish"><b>Добавить блюдо</b></a></li>
                <li class="active"><a href="/dishesList"><b>Список блюд</b></a></li>
                <li><a href="/register_admin"><b>Создать пользователя</b></a></li>
                <li><a href="/userlist"><b>Список пользователей</b></a></li>

            </ul>


        </div>


        <div class="col-sm-8 col-sm-offset-5 col-md-10 col-md-offset-2 main">


            <form method="post"> <!-- action="/order" -->
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th width="10%" align="right">
                                <button class="btn btn-danger" type="button" id="modal_dish"> Удалить </button>

                            </th>

                            <th></th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Имя</em></font>
                            </th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Вес</em></font>
                            </th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Цена</em></font>
                            </th>
                            <th width="15%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Время готовки</em></font>
                            </th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Скидка %</em></font>
                            </th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Бонусы для скидки</em></font>
                            </th>
                            <th width="30%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Фото</em></font>
                            </th>

                        </tr>
                        </thead>

                        <form class="form-control" enctype="multipart/form-data" method="post">
                            <c:forEach var="dish" items="${dishes}">
                                <jsp:useBean id="dish" scope="page" type="malov.serg.Model.Dish"/>
                                <tr>
                                    <td><input type="checkbox" align="center" name="toOrder[]" value="${dish.id}"/>
                                    </td>

                                    <th width="1%" align="right">
                                        <button class="btn btn-success" OnClick="sendPost(this);" type="button"
                                                name="edit"
                                                value="${dish.id}">
                                            Изменить
                                        </button>

                                    </th>
                                    <td><c:out value="${dish.name}"/>
                                    <td><c:out value="${dish.weight}"/>
                                    <td><c:out value="${dish.cost}"/>
                                    <td><c:out value="${dish.duration}"/>
                                    <td><c:out value="${dish.discount}"/>
                                    <td><c:out value="${dish.bonus}"/>
                                    <td>
                                        <div id="aaa"
                                             style="border: 0px; display: inline-block; position: relative; overflow: hidden;">
                                            <a href="#" class="thumbnail" style="">
                                                <img width="60" height="60" src="/photo/${dish.id}"/>
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

            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <c:if test="${allPages ne null}">
                        <c:forEach var="i" begin="1" end="${allPages}">
                            <li><a href="/dishesList?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
                        </c:forEach>
                    </c:if>
                </ul>
            </nav>


            </form>


        </div>
    </div>
</div>


<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/photo.js"></script>
<script src="resources/js/deleteDishes.js"></script>
<script src="resources/jquery.confirm/jquery.confirm/jquery.confirm.js"></script>


<script>

    function sendPost(id) {

        window.location.href = '/dish_edit_page_' + id.value;

    }
</script>

</body>
</html>

