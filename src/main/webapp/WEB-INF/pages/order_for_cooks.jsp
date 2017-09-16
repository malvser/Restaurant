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
    <!-- <link rel="stylesheet" type="text/css" href="resources/jquery.confirm/css/styles.css" /> -->
    <link rel="stylesheet" type="text/css" href="resources/jquery.confirm/jquery.confirm/jquery.confirm.css"/>

    <!-- <link rel='stylesheet' href='resources/css/bootstrap.min.css' type='text/css' media='all'> -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link href="resources/style.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-3.2.1.js"></script>

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
            <a id="add_contact" class="navbar-brand" href="/enter_cook">Главная</a>
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
                <li><a href="/">Home</a></li>
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
    </div>


    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <form action="/cooked_order" method="post">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td width="20%"><h3>Dish</h3></td>
                        <td width="20%"><h3>Weight</h3></td>
                        <td width="20%"><h3>Table №</h3></td>
                        <td width="20%"><h3>Photo</h3></td>

                    </tr>
                    </thead>
                    <form class="form-control" enctype="multipart/form-data" action="/order" method="post">
                        <c:forEach var="item" items="${dishesList}">
                            <tr>
                                <td><c:out value="${item.name}"/>
                                <td><c:out value="${item.weight}"/>
                                <td><c:out value="${numberTable}"/>


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
                <input hidden name="order_id" value="${orderId}">

            </div>
            <div class="btn-group" role="group">
                <input type="submit" id="buttons-hbox" class="btn btn-success" value="Order is ready / Get new order">
            </div>

        </form>

        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${allPages ne null}">
                    <c:forEach var="i" begin="1" end="${allPages}">
                        <li><a href="/order_for_cooks?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
                    </c:forEach>
                </c:if>
            </ul>
        </nav>


        </form>
        <!-- <input type="submit" value="Back to mainpage" onclick="window.location='/';"/> -->

    </div>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>

<!-- подтверждающее окно  jquery -->
<script src="resources/jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script src="resources/jquery.confirm/js/script.js"></script>
<script src="resources/js/checkboxAll.js"></script>


</body>
</html>