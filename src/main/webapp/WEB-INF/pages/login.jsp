<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="resources/img/logo.png">
    <title>Логин</title>

    <!-- <link rel='stylesheet' href='resources/css/bootstrap.min.css' type='text/css' media='all'> -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <!-- Bootstrap core CSS -->
    </head>
<body>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">"Название ресторана"</a>


        </div>
        <div id="navbar" class="navbar-collapse collapse">

            <form class="navbar-form navbar-left" action="/advertisement_view" method="post">
                <input hidden name="IdDishes" value="${Id}">
                <div class="btn-group" role="group">
                    <input type="submit" class="btn btn-info" value="Загляните сюда!">
                </div>

            </form>
            <form action="/spring_security_check" class="navbar-form navbar-right" method="post">
                <c:if test="${param.error ne null}">
                   <p  style="color: red" > <b>Неправильный логин или пароль!</b></p>
                </c:if>

                <!--  -->

               <div class="form-group">
                    <input type="text" name="login" placeholder="Login" class="form-control" >
                </div>
                <div class="form-group">
                    <input type="password" name="password" placeholder="Password" class="form-control">
                </div>
                <button type="submit" class="btn btn-success">Войти</button>


            </form>

            <form action="/register" class="navbar-form navbar-right">
                <button type="submit" class="btn btn-success">Регистрация</button>
            </form>

        </div><!--/.navbar-collapse -->
    </div>
</nav>

<div class="jumbotron">
    <div class="container">

        <form class="form-signin">


            <p ><img  alt="УПССС... ) здесь должна быть картинка" src="resources/img/logo.png" align="left"
                      vspace="15" hspace="15"></p>
            <p> <b> admin login: admin / password</b> </p>

            <p> <b> user login: user / password2</b></p>

            <p> <b> cook login: cook / password3</b> </p>

            <p>  <b>  Стек технологий: </b> Spring Security, Spring Boot, Spring MVC,
                Spring Data JPA, Hibernate ORM, JSP, JSTL, MySQL, JavaScript, jQuery, Bootstrap. </p>
            <p><a class="btn btn-primary btn-lg" href="/search_hot_snacks" role="button">Меню</a></p>

            <p>&nbsp;&nbsp;&nbsp;     Java Enterprise проект с регистрацией/авторизацией и интерфейсом на основе ролей (ADMIN, USER, COOK).</p>
            <p><b>Администратор может:</b></p>
            <p> - создавать/редактировать/удалять пользователей (ADMIN, USER, COOK), блюда, столы, рекламу,</p>
            <p> - просматривать состояние заказов,</p>
            <p> - просматривать/удалять статистику:</p>
            <p> &nbsp;&nbsp;&nbsp; - по отсутствию рекламы,</p>
            <p> &nbsp;&nbsp;&nbsp; - по просмотренной клиентами рекламе,</p>
            <p> &nbsp;&nbsp;&nbsp; - по приготовленным заказам.</p>

            <p> <b> Незарегистрированный пользователь может: </b></p>
            <p> - просматривать блюда, информацию о них, фото блюд,</p>
            <p> - делать заказ,</p>
            <p> - просматривать рекламу,</p>
            <p> - зарегистрироваться.</p>
            <p> <b> Зарегистрированный пользователь может: </b></p>
            <p> - просматривать блюда, информацию о них, фото блюд,</p>
            <p> - делать заказ,</p>
            <p> - просматривать рекламу,</p>
            <p> - просматривать количество бонусов в его профиле,</p>
            <p> - просматривать только те блюда, которые имеют скидку по бонусам.</p>
            <p>   Зарегистрированому пользователю за каждое заказанное блюдо начисляется 1 бонус</p>
            <p> <b> Повар может: </b></p>
            <p> - просматривать общее количество новых заказов,</p>
            <p> - просматривать общее время приготовления новых заказов,</p>
            <p> - просматривать количество блюд в новых заказах,</p>
            <p> - просматривать количество готовящихся сейчас заказов,</p>
            <p> - просматривать общее время приготовления готовящихся заказов,</p>
            <p> - просматривать количество блюд готовящихся заказов,</p>
            <p> - приготовить заказ,</p>
            <p> - подтвердить приготовление заказа и взять следующий заказ для приготовления,</p>
            <p> - подтвердить приготовление заказа и выйти из меню приготовления заказов.</p>


        </form>

    </div>

</div>


</body>
</html>
