<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Malov Serg</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Restaurant</a>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">

        <div class="jumbotron">
            <div class="container">

                <div align="center">
                    <h1>Menu:</h1>

                    <table align="center">
                        <tr>
                            <td width="20px"></td>
                            <td width="400px"><h3>Choose Dish</h3></td>
                            <td width="400px"><h3>Duration (min)</h3></td>
                        </tr>
                        <form action="/order" method="post">
                            <c:forEach var="item" items="${dishes}">
                            <tr>
                                <td><input type="checkbox" name="check" value="${item.name()}"/></td>
                                <td><c:out value="${item.name()}"/>
                                <td><c:out value="${item.getDuration()}"/>
                                </td>
                            </tr>
                            </c:forEach>
                    </table>
                    <input type="submit" value="Make order"/>
                    </form>
                    <!-- <input type="submit" value="Back to mainpage" onclick="window.location='/';"/> -->
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
