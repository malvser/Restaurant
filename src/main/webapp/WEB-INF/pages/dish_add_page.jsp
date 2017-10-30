<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Новое блюдо</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <form enctype="multipart/form-data" role="form" class="form-horizontal" action="/dish/add" method="post">
        <h3>Новое блюдо</h3>
        <c:if test="${name ne null}">
            <label for="name" class="sr-only">name</label>
            <input class="form-control form-group" id="name" type="text" name="name" value="${name}" required>
        </c:if>
        <c:if test="${name eq null}">
            <label for="name" class="sr-only">name</label>
            <input class="form-control form-group" id="name" type="text" name="name" placeholder="Название блюда" required>
        </c:if>

        <c:if test="${cost ne null}">
            <label for="cost" class="sr-only">cost</label>
            <input class="form-control form-group" id="cost" type="text" name="cost" value="${cost}" required>
        </c:if>
        <c:if test="${cost eq null}">
            <label for="cost" class="sr-only">cost</label>
            <input class="form-control form-group" id="cost" type="text" name="cost" placeholder="Стоимость" required>
        </c:if>

        <c:if test="${weight ne null}">
            <label for="weight" class="sr-only">weight</label>
            <input class="form-control form-group" id="weight" value="${weight}" type="text" name="weight" required>
        </c:if>
        <c:if test="${weight eq null}">
            <label for="weight" class="sr-only">weight</label>
            <input class="form-control form-group" id="weight" type="text" name="weight" placeholder="Оставшееся количество оплаченных показов" required>
        </c:if>

        <c:if test="${discount ne null}">
            <label for="discount" class="sr-only">discount</label>
            <input class="form-control form-group" id="discount" type="text" name="discount" value="${discount}" required>
        </c:if>
        <c:if test="${discount eq null}">
            <label for="total_amount" class="sr-only">total_amount</label>
            <input class="form-control form-group" id="total_amount" type="text" name="total_amount" placeholder="Общее количество оплаченных показов" required>
        </c:if>
        <c:if test="${bonus ne null}">
            <label for="bonus" class="sr-only">bonus</label>
            <input class="form-control form-group" id="bonus" value="${bonus}" type="text" name="bonus" placeholder="К-во необходимых бонусов для скидки" required>
        </c:if>
        <c:if test="${bonus eq null}">
            <label for="bonus" class="sr-only">bonus</label>
            <input class="form-control form-group" id="bonus" type="text" name="bonus" placeholder="К-во необходимых бонусов для скидки" required>
        </c:if>
        <c:if test="${duration ne null}">
            <label for="duration" class="sr-only">duration</label>
            <input class="form-control form-group" id="duration" value="${duration}" type="text" name="duration" placeholder="Время приготовления (мин.)"  required>
        </c:if>
        <c:if test="${duration eq null}">
            <label for="duration" class="sr-only">duration</label>
            <input class="form-control form-group" id="duration" type="text" name="duration" placeholder="Время приготовления (мин.)"  required>
        </c:if>

        <c:if test="${type ne null}">
            <select class="selectpicker form-control form-group" name="type">
                <option value="-1">${type}</option>
                <option value="Горячие закуски">Горячие закуски</option>
                <option value="Холодные закуски">Холодные закуски</option>
                <option value="Первые блюда">Первые блюда</option>
            </select>
        </c:if>
        <c:if test="${type eq null}">
            <select class="selectpicker form-control form-group" name="type">

                <option value="Горячие закуски">Горячие закуски</option>
                <option value="Холодные закуски">Холодные закуски</option>
                <option value="Первые блюда">Первые блюда</option>
            </select>
        </c:if>



        <c:if test="${photo ne null}">
            <label for="photo" class="sr-only">photo</label>
            <input  type="file" id="photo" name="photo" value="${photo}" required>
        </c:if>
        <c:if test="${photo eq null}">
            <label for="photo" class="sr-only">photo</label>
            <input  type="file" id="photo" name="photo" required>
        </c:if>

        <input class="form-control form-group" type="hidden" name="dish_id" value="${dish_id}">
        <p></p>
        <input type="submit" class="btn btn-primary" value="Добавить">
    </form>
</div>

</body>
</html>
