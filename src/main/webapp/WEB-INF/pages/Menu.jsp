<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Malov Serg</title>

    <!-- <link rel='stylesheet' href='resources/css/bootstrap.min.css' type='text/css' media='all'> -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link href="resources/style.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</head>
<body>


<div class="modal fade" id="image-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <div class="modal-title">Photo</div>
            </div>
            <div class="modal-body">
                <img class="img-responsive center-block" src="" alt="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
            <a id="add_contact" class="navbar-brand">MENU</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/">Home</a></li>
                <li><a href="/add_dish">Add dish</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right" role="search" action="/search" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="pattern" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
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


        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <!--  <form action="/order"  method="post"> -->
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th width="10%" align="right">
                            <div class="btn-group btn-group-justified" role="group" aria-label="...">
                                <div class="btn-group" role="group">
                                    <input type="submit" class="btn btn-success" value="Order">
                                </div>
                            </div>
                        </th>
                        <td width="20%"><h3>Name</h3></td>
                        <td width="20%"><h3>Weight</h3></td>
                        <td width="40%"><h3>Cost</h3></td>
                        <td width="30%"><h3>Discount</h3></td>
                        <td width="30%"><h3>Photo</h3></td>

                    </tr>
                    </thead>
                    <form enctype="multipart/form-data" action="/order" method="post">
                        <c:forEach var="item" items="${dishes}">
                            <tr>
                                <td><input type="checkbox" align="center" name="check" value="${item.getId()}"/>
                                </td>
                                <td><c:out value="${item.getName()}"/>
                                <td><c:out value="${item.getWeight()}"/>
                                <td><c:out value="${item.getCost()}"/>
                                <td><c:out value="${item.getDiscount()}"/>
                                <td>
                                    <div id="aaa"
                                         style="border: 0px; display: inline-block; position: relative; overflow: hidden;">
                                        <a href="#" class="thumbnail" style="">
                                            <img width="60" height="60" src="/photo/${item.getId()}"/>
                                        </a>
                                    </div>
                                </td>
                                </td>
                            </tr>
                        </c:forEach>
                    </form>
                </table>
            </div>

            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <c:if test="${allPages ne null}">
                        <c:forEach var="i" begin="1" end="${allPages}">
                            <li><a href="/?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
                        </c:forEach>
                    </c:if>
                </ul>
            </nav>
            <input type="submit" value="Make order"/>
            </form>
            <!-- <input type="submit" value="Back to mainpage" onclick="window.location='/';"/> -->

        </div>
    </div>


</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>


<script type="text/javascript">
    $(function () {
        $('a.thumbnail').click(function (e) {
            e.preventDefault();
            $('#image-modal .modal-body img').attr('src', $(this).find('img').attr('src'));
            $("#image-modal").modal('show');
        });
        $('#image-modal .modal-body img').on('click', function () {
            $("#image-modal").modal('hide')
        });
    });

</script>


</body>
</html>

