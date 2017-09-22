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


<div class="col">

    <form action="" method="post">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <td></td>
                <td></td>
                <td></td>
                <td></td>

                <tr>

                    <td width="5%"><h3>Date</h3></td>
                    <td width="5%"><h3>Table</h3></td>
                    <td width="5%"><h3>Name cook</h3></td>
                    <td width="40%"><h3>Time cooking</h3></td>



                </tr>
                </thead>
                <form class="form-control" enctype="multipart/form-data" action="/order" method="post">
                    <c:forEach var="item" items="${cookedOrderList}">
                        <tr>

                            <td><c:out value="${item.date}"/>
                            <td><c:out value="${item.tabletNumber}"/>
                            <td><c:out value="${item.cookCookedOrder.name}"/>
                            <td><c:out value="${item.cookingTimeSeconds}"/>
                        <c:forEach var="item2" items="${item.cookingDishes}">
                            <table class="table table-striped">
                                <thead>
                                <td width="20%"><h3>Name dish</h3></td>
                                <td width="20%"><h3>Weight</h3></td>
                                <td width="20%"><h3>Cost</h3></td>
                                <td width="20%"><h3>Discount</h3></td>
                                <td width="20%"><h3>Photo</h3></td>
                                </thead>
                            <td><c:out value="${item2.name}"/>
                            <td><c:out value="${item2.weight}"/>
                            <td><c:out value="${item2.cost}"/>
                            <td><c:out value="${item2.discount}"/>
                                <td>
                                    <div id="aaa"
                                         style="border: 0px; display: inline-block; position: relative; overflow: hidden;">
                                        <a href="#" class="thumbnail" style="">
                                            <img width="60" height="60" src="/photo/${item2.id}"/>
                                        </a>
                                    </div>
                                </td>
                            </table>
                        </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </form>
            </table>
        </div>
    </form>


    </form>



</div>
<!-- <input type="submit" value="Back to mainpage" onclick="window.location='/';"/> -->


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>

<!-- подтверждающее окно  jquery -->
<script src="resources/jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script src="resources/jquery.confirm/js/script.js"></script>
<script src="resources/js/photo.js"></script>


</body>
</html>


