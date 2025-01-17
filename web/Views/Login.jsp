<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login/Sign Up</title>
        <link rel="icon" href="image/logoshop.png" type="image/png">
        <!--        <link rel="stylesheet" href="css/style.css">-->
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>

        <div class="box" id="box1" style="display: ${showlogin}">
            <div style="width: 400px;" aria-labelledby="dropdownMenuButton">
                <div class="container" style="width: 400px;">
                    <div class="modal-header">
                        <h2>Đăng nhập</h2>
                    </div>
                    <div class="modal-body">
                        <div class="customLogin">
                            <form action="${pageContext.request.contextPath}/LoginServlet" method="post" id="loginForm">
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="text" class="form-control" id="email" name="email" required
                                           placeholder="Nhập email của bạn">
                                </div>
                                <div class="form-group">
                                    <label for="password">Mật khẩu</label>
                                    <input type="password" class="form-control" id="password1" name="password" required
                                    placeholder="Nhập mật khẩu của bạn">
                                </div>
                                <div>
                                    <input type="checkbox" id="togglePasswords" onclick="togglePasswordVisibility1()"> Hiển thị mật khẩu
                                </div>
                                <button type="submit" class="btn btn-primary">Đăng nhập</button>
                                <a href="#" onclick="toggleDiv('box3', 'box1')">Quên mật khẩu </a>
                                <p>Bạn chưa có tài khoản?<a href="#" onclick="toggleDiv('box2', 'box1')">Đăng kí</a></p>
                            </form>
                            <c:if test="${not empty requestScope.errorlogin}">
                                <div style="color: red;">
                                    <c:out value="${requestScope.errorlogin}" />
                                </div>
                            </c:if>
                            <c:if test="${not empty requestScope.sucesspass}">
                                <div class="alert alert-success" role="alert">
                                    <c:out value="${requestScope.sucesspass}" />
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function togglePasswordVisibility1() {
                const passwordField = document.getElementById('password1');
                console.log(passwordField);
                console.log(passwordField.type);
                if (passwordField.type === "password") {
                    passwordField.type = 'text';
                    console.log(passwordField.type);
                } else {
                    passwordField.type = 'password';
                    console.log(passwordField.type);
                }
            }
        </script>

    </body>
</html>
