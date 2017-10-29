<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Новая реклама</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">

</head>
<body>
<div class="container">
    <form enctype="multipart/form-data" role="form" class="form-horizontal" action="/advertisement/add" method="post">
        <h3>Новая реклама</h3>

        <c:if test="${name ne null}">
            <label for="name" class="sr-only">name</label>
            <input class="form-control form-group" id="name" type="text" name="name" value="${name}" required>
        </c:if>
        <c:if test="${name eq null}">
            <label for="name" class="sr-only">name</label>
            <input class="form-control form-group" id="name" type="text" name="name" placeholder="Имя" required>
        </c:if>

        <c:if test="${cost ne null}">
            <label for="cost" class="sr-only">cost</label>
            <input class="form-control form-group" id="cost" type="text" name="cost" value="${cost}" required>
        </c:if>
        <c:if test="${cost eq null}">
            <label for="cost" class="sr-only">cost</label>
            <input class="form-control form-group" id="cost" type="text" name="cost" placeholder="Стоимость" required>
        </c:if>

        <c:if test="${amount ne null}">
            <label for="amount" class="sr-only">amount</label>
            <input class="form-control form-group" id="amount" type="text" name="amount" value="${amount}" required>
        </c:if>
        <c:if test="${amount eq null}">
            <label for="amount" class="sr-only">amount</label>
            <input class="form-control form-group" id="amount" type="text" name="amount" placeholder="Оставшееся количество оплаченных показов" required>
        </c:if>

        <c:if test="${total_amount ne null}">
            <label for="total_amount" class="sr-only">total_amount</label>
            <input class="form-control form-group" id="total_amount" type="text" name="total_amount" value="${total_amount}" required>
        </c:if>
        <c:if test="${total_amount eq null}">
            <label for="total_amount" class="sr-only">total_amount</label>
            <input class="form-control form-group" id="total_amount" type="text" name="total_amount" placeholder="Общее количество оплаченных показов" required>
        </c:if>

        <c:if test="${photo ne null}">
            <label for="photo" class="sr-only">photo</label>
            <input  type="file" id="photo" name="photo" value="${photo}" required>
        </c:if>
        <c:if test="${photo eq null}">
            <label for="photo" class="sr-only">photo</label>
            <input  type="file" id="photo" name="photo" required>
        </c:if>


        <input class="form-control form-group" type="hidden" name="advertisement_id" value="${advertisement_id}">

        <p></p>
        <input type="submit" class="btn btn-primary" value="Добавить">
    </form>
</div>


</body>
</html>
