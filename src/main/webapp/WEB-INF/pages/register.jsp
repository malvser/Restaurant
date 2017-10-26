<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">


    <title>Регистрация</title>

    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <link href="resources/css/signin.css" rel="stylesheet">

</head>

<body>

<div class="container">

    <c:url value="/newuser" var="regUrl" />


    <form action="${regUrl}" method="post" class="form-signin">
        <h2 class="form-signin-heading"><font size="7" color="#d2691e" face="Monotype Corsiva"><em>
            <b>Регистрация</b> </em></font></h2>
        <label for="inputLogin" class="sr-only">login</label>
        <input type="text" id="inputLogin" name="login" class="form-control" placeholder="Login" required autofocus>
        <label for="inputPassword" class="sr-only">password</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
        <label for="inputEmail" class="sr-only">email</label>
        <input type="email" id="inputEmail" name="email" class="form-control" placeholder="Email" required>
        <label for="inputPhone" class="sr-only">phone</label>
        <input type="text" id="inputPhone" name="phone" class="form-control" placeholder="Phone" required>
        <input type="text" id="inputFull_name" name="full_name" class="form-control" placeholder="ФИО">


        <button class="btn btn-lg btn-primary btn-block" type="submit">Зарегистрировать</button>

        <c:if test="${exists ne null}">
            <div class="alert alert-warning" role="alert">Такой логин уже существует</div>
        </c:if>
    </form>

</div>

</body>
</html>
