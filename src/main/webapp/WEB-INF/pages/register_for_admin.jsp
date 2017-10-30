<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Регистрация</title>

    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <link href="resources/css/signin.css" rel="stylesheet">

</head>

<body>

<div class="container">

    <c:url value="/newanyuser" var="regUrl" />


    <form action="${regUrl}" method="post" class="form-signin">
        <h2 class="form-signin-heading"><font size="7" color="#d2691e" face="Monotype Corsiva"><em>
            <b>Регистрация</b> </em></font></h2>
        <c:if test="${login ne null}">
            <label for="inputLogin" class="sr-only">login</label>
            <input type="text" id="inputLogin" name="login" class="form-control" value="${login}" required autofocus>
        </c:if>
        <c:if test="${login eq null}">
            <label for="inputLogin" class="sr-only">login</label>
            <input type="text" id="inputLogin" name="login" class="form-control" placeholder="Логин" required autofocus>
        </c:if>

            <label for="inputPassword" class="sr-only">password</label>
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Пароль" required>

        <c:if test="${role ne null}">
            <select class="selectpicker form-control form-group" name="role">
                <option value="-1">${role}</option>
                <c:forEach items="${userRole}" var="role">
                    <jsp:useBean id="role" scope="page" type="malov.serg.Model.UserRole" />
                    <option value="${role.name().toString()}">${role.name().toString()}</option>
                </c:forEach>
            </select>
        </c:if>
        <c:if test="${role eq null}">
            <select class="selectpicker form-control form-group" name="role">

                <c:forEach items="${userRole}" var="roles">
                    <jsp:useBean id="roles" scope="page" type="malov.serg.Model.UserRole" />
                    <option value="${roles.name().toString()}">${roles.name().toString()}</option>
                </c:forEach>
            </select>
        </c:if>

        <c:if test="${email ne null}">
            <label for="inputEmail" class="sr-only">email</label>
            <input type="email" id="inputEmail" name="email" class="form-control" value="${email}" required>
        </c:if>
        <c:if test="${email eq null}">
            <label for="inputEmail" class="sr-only">email</label>
            <input type="email" id="inputEmail" name="email" class="form-control" placeholder="Email" required>
        </c:if>
        <c:if test="${phone ne null}">
            <label for="inputPhone" class="sr-only">phone</label>
            <input type="text" id="inputPhone" name="phone" class="form-control" value="${phone}" required>
        </c:if>
        <c:if test="${phone eq null}">
            <label for="inputPhone" class="sr-only">phone</label>
            <input type="text" id="inputPhone" name="phone" class="form-control" placeholder="Телефон" required>
        </c:if>

        <c:if test="${full_name ne null}">
            <label for="inputFull_name" class="sr-only">full_name</label>
            <input type="text" id="inputFull_name" name="full_name" class="form-control" value="${full_name}" required>
        </c:if>
        <c:if test="${full_name eq null}">
            <label for="inputFull_name" class="sr-only">full_name</label>
            <input type="text" id="inputFull_name" name="full_name" class="form-control" placeholder="ФИО" required>
        </c:if>


        <input class="form-control form-group" type="hidden" name="user_id" value="${user_id}">
        <input class="form-control form-group" type="hidden" name="bonus" value="${bonus}">

        <button class="btn btn-lg btn-primary btn-block" type="submit">Зарегистрировать</button>

        <c:if test="${exists ne null}">
            <div class="alert alert-warning" role="alert">Такой логин уже существует</div>
        </c:if>
    </form>

</div>

</body>
</html>
