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
            <a id="name" class="navbar-brand">"Название ресторана"</a>
            <a id="bonus_user" class="navbar-brand"> <b>Количество бонусов: <c:out
                    value="${bonus_user}"/> шт. </b></a>
            <form class="navbar-form navbar-left" method="post">
                <button class="btn btn-success" type="button" id="modal" >Сделать заказ</button>
            </form>

        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a>Ваш логин: ${login}</a></li>
                <li><a href="/logout">Выход</a></li>
            </ul>
            <c:if test="${login ne null}">
                <input type="hidden" name="log" id="log" value="${login}">
            </c:if>
            <c:if test="${login eq null}">
                <input type="hidden" name="log" id="log" value="empty">
            </c:if>

            <c:if test="${bonus ne null}">
                <input type="hidden" name="bonus" id="bonus" value="${bonus}">
            </c:if>
            <c:if test="${bonus eq null}">
                <input type="hidden" name="bonus" id="bonus" value="little">
            </c:if>

        </div>
    </div>
</nav>


<div class="col-sm-8 col-sm-offset-5 col-md-12 col-md-offset-0 main">

    <form >
        <div class="table-responsive">
            <p align="center">
                <font size="7" color="#6a5acd" face="Monotype Corsiva"><em>
                    Список блюд, которые имеют скидку </em></font>
            </p>
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
                    <th width="15%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                        Скидка</em></font>
                    </th>
                    <th width="30%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                        К-во бонусов</em></font>
                    </th>
                    <th width="30%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                        Фото</em></font>
                    </th>

                </tr>
                </thead>
                <form class="form-control" enctype="multipart/form-data" >
                    <c:forEach var="item" items="${dishes}">
                        <tr>
                            <td><input type="checkbox" align="center" id="checkbox_${item.id}" name="toOrder[]"
                                       value="${item.id}"/>
                            </td>

                            <td><c:out value="${item.name}"/>
                            <td><c:out value="${item.weight}"/>
                            <td><c:out value="${item.cost}"/>
                            <td><c:out value="${item.discount}"/>
                            <td><c:out value="${item.bonus}"/>
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
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/photo.js"></script>
<script src="resources/jquery.confirm/js/script.js"></script>
<script src="resources/jquery.confirm/jquery.confirm/jquery.confirm.js"></script>

</body>
</html>

