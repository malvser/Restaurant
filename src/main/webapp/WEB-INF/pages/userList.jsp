<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>

    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">

    <title>Список пользователей</title>

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
                <li><a href="/userlist">Сброс поиска</a></li>
                <li> <a>Ваш логин: ${login}</a></li>
                <li><a href="/logout">Выход</a></li>
            </ul>

            <form class="navbar-form navbar-right" role="search" action="/search_user" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="pattern" placeholder="ФИО">
                </div>
                <button type="submit" class="btn btn-primary">Поиск</button>

            </form>

        </div>
    </div>
</nav>


<div class="container-fluid" >
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">


            <ul class="nav nav-sidebar">

                <li><a href="/enter_cook_admin"> <b>Просмотр состояния заказов</b></a></li>
                <li><a href="/statistic_cooked_order"> <b>Статистика приготовленных заказов</b></a></li>
                <li><a href="/statistic_viewed_advertisement"><b>Статистика по просмотренной рекламе</b></a></li>
                <li><a href="/statistic_no_advertisement"><b>Статистика по отсутствию рекламы</b></a></li>
                <li><a href="/advertisement_add_page"><b>Добавить рекламу</b></a></li>
                <li><a href="/advertisementList"><b>Список рекламы</b></a></li>
                <li><a href="/add_tablet"><b>Добавить стол</b></a></li>
                <li><a href="/tabletList"><b>Список столов</b></a></li>
                <li><a href="/add_dish"><b>Добавить блюдо</b></a></li>
                <li><a href="/dishesList"><b>Список блюд</b></a></li>
                <li><a href="/register_admin"><b>Создать пользователя</b></a></li>
                <li class="active"><a href="/userlist"><b>Список пользователей</b></a></li>

            </ul>



        </div>


        <div class="col-sm-8 col-sm-offset-5 col-md-10 col-md-offset-2 main">


            <form  method="post"> <!-- action="/order" -->
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th width="5%" align="right">
                                <button class="btn btn-danger" type="button" id="delete" >Удалить</button>

                            </th>
                            <th></th>
                            <th width="15%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Логин</em></font>
                            </th>
                            <th width="25%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Роль</em></font>
                            </th>
                            <th width="25%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                ФИО</em></font>
                            </th>
                            <th width="25%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Почтовый адресс</em></font>
                            </th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Телефон</em></font>
                            </th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Кол-во бонусов</em></font>
                            </th>


                        </tr>
                        </thead>
                        <form class="form-control" enctype="multipart/form-data"  method="post">
                            <c:forEach var="customUser" items="${customUsers}">
                                <jsp:useBean id="customUser" scope="page" type="malov.serg.Model.CustomUser" />
                                <tr>
                                    <td><input type="checkbox" align="center"  name="toOrder[]" value="${customUser.id}"/>
                                    </td>
                                    <th width="1%" align="right">
                                        <button class="btn btn-success" OnClick="sendPost(this);" type="button"
                                                name="edit"
                                                value="${customUser.id}">
                                            Изменить
                                        </button>

                                    </th>
                                    <td><c:out value="${customUser.login}"/>
                                    <td><c:out value="${customUser.role}"/>
                                    <td><c:out value="${customUser.full_name}"/>
                                    <td><c:out value="${customUser.email}"/>
                                    <td><c:out value="${customUser.phone}"/>
                                    <td><c:out value="${customUser.bonus}"/>


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
                            <li><a href="/userlist?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
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
<script src="resources/js/deleteUser.js"></script>
<script src="resources/jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script>

    function sendPost(id) {

        window.location.href = '/register_edit_admin_' + id.value;

    }
</script>

</body>
</html>

