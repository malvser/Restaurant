<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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


            <form class="navbar-form navbar-left">

                <button class="btn btn-success" type="button"  id="new_order">Заказ готов / Получить новый заказ</button>
            </form>


        </div>
        <div id="navbar" class="navbar-collapse collapse">

            <form class="navbar-form navbar-right">
                <button class="btn btn-danger" type="button" id="home">Заказ готов / Выйти</button>

            </form>

        </div>
    </div>
</nav>


<div class="container-fluid">
    <div class="row">


        <div class="col-sm-8 col-sm-offset-5 col-md-12 col-md-offset-0 main">

            <form>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Блюдо</em></font>
                            </th>
                            <th width="25%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Вес</em></font>
                            </th>
                            <th width="25%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Стол №</em></font>
                            </th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Готовка (мин.)</em></font>
                            </th>
                            <th width="20%"><font size="6" color="#d2691e" face="Monotype Corsiva"><em>
                                Фото</em></font>
                            </th>


                        </tr>
                        </thead>
                        <form class="form-control" enctype="multipart/form-data" method="post">
                            <c:forEach var="item" items="${dishesList}">
                                <tr>

                                    <td><c:out value="${item.name}"/>
                                    <td><c:out value="${item.weight}"/>
                                    <td><c:out value="${numberTable}"/>
                                    <td><c:out value="${item.duration}"/>


                                    <td>
                                        <div id="aaa"
                                             style="border: 0px; display: inline-block; position: relative; overflow: hidden;">
                                            <a href="#" class="thumbnail" style="">
                                                <img width="60" height="60" src="/photo/${item.id}"/>
                                            </a>
                                        </div>

                                    </td>

                                </tr>
                            </c:forEach>


                        </form>
                    </table>

                    <input hidden name="id" id="id" value="${orderId}">

                </div>


            </form>


        </div>
    </div>
</div>

<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/photo.js"></script>
<script src="resources/js/home.js"></script>
<script src="resources/js/new_order.js"></script>
<script src="resources/jquery.confirm/jquery.confirm/jquery.confirm.js"></script>


</body>
</html>

























