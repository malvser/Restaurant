<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <!-- <link rel='stylesheet' href='resources/css/bootstrap.min.css' type='text/css' media='all'> -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link href="resources/style.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-3.2.1.js"></script>

</head>
<body>

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
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/menu">Сброс поиска</a></li>
                <li><a href="/add_dish">Добавить блюдо</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right" role="search" action="/search" method="post">
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
                <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>
                <li><a href="/order">Order</a></li>
                <li><a href="#">Analytics</a></li>
                <li><a href="#">Export</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="">Nav item</a></li>
                <li><a href="">Nav item again</a></li>
                <li><a href="">One more nav</a></li>
                <li><a href="">Another nav item</a></li>
                <li><a href="">More navigation</a></li>
            </ul>

        </div>


        <h2 align="center">Заказанные Вами блюда</h2>


        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <form action="/delete_choose" method="post">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th width="10%" align="right">
                            </th>
                            <th width="20%"><h3>Name</h3></th>
                            <th width="20%"><h3>Weight</h3></th>
                            <th width="40%"><h3>Cost</h3></th>
                            <th width="30%"><h3>Discount</h3></th>
                            <th width="30%"><h3>Photo</h3></th>


                        </tr>
                        </thead>
                        <form class="form-control" enctype="multipart/form-data" method="post">
                            <c:set var="ser" value="yes"/>
                            <c:forEach var="item" items="${dishesArrayId}">
                                <tr>
                                    <td><input type="checkbox" align="center" name="id_delete" value="${item.id}"/>
                                    </td>
                                    <td><c:out value="${item.name}"/>
                                    <td><c:out value="${item.weight}"/>
                                    <td><c:out value="${item.cost}"/>
                                    <td><c:out value="${item.discount}"/>
                                    <td>
                                        <div id="aaa"
                                             style="border: 0px; display: inline-block; position: relative; overflow: hidden;">
                                            <a href="#" class="thumbnail" style="">
                                                <img width="60" height="60" name="id_list" src="/photo/${item.id}"/>
                                            </a>
                                        </div>
                                    </td>

                                    </td>

                                </tr>
                                <c:set var="bool" value="false"/>
                            </c:forEach>

            </form>
            </table>
        </div>
        </form>
        <form action="/order" method="post">

            <div class="btn-group" role="group">
                <input type="submit" id="buttons-hbox" class="btn btn-success" value="Подтвердить заказ">
            </div>

        </form>
        <form action="/menu" method="post">

            <div class="btn-group" role="group">
                <input type="submit" id="buttons-hbox2" class="btn btn-info" value="Отменить заказ">
            </div>

        </form>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${allPages ne null}">
                    <c:forEach var="i" begin="1" end="${allPages}">
                        <li><a href="/menu?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
                    </c:forEach>
                </c:if>
            </ul>
        </nav>

        </form>
        <!-- <input type="submit" value="Back to mainpage" onclick="window.location='/';"/> -->

    </div>
</div>


</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>

<!-- подтверждающее окно  jquery -->
<script src="resources/jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script src="resources/jquery.confirm/js/script.js"></script>
<script src="resources/js/photo.js"></script>


</body>
</html>