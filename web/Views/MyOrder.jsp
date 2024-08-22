<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Details</title>
    <link rel="icon" href="image/logoshop.png" type="image/png">
    <link rel="preload stylesheet" as="style" fetchpriority="low"
          href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="preload stylesheet" as="style" fetchpriority="low" href="css/style-theme.scss.css">
    <link rel="preload stylesheet" as="style" fetchpriority="low"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css">
    <link rel="preload stylesheet" as="style" fetchpriority="low"
          href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script defer fetchpriority="low" src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"></script>

    <script defer fetchpriority="low" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    <style>
        body {
            margin-top: 20px;
        }
        .order-card {
            /*background-color: rgb(237 237 237);*/
            color: rgb(0, 0, 0);
            padding: 20px;
            /*            border-radius: 8px;*/
            border: solid 1px;
            border-color: rgb(0, 0, 0);
        }
        .order-card h3,
        .order-card h6 {
            margin: 0;
        }
        .order-card table thead {
            background-color: #343a40;
            color: rgb(199, 180, 180);
        }
        .order-card table tbody tr {
            background-color: #cdbebe;
            color: black;
        }
        .order-card table tbody tr:nth-child(even) {
            background-color: #b8b1b1;
        }
        .table-response{
            margin-top: 20px;
        }
        .button-order{
            margin-left: 500px;
        }
    </style>
</head>

<body>
    <%@include file="HomeHeader.jsp" %>
    <div class="breadcrumb-shop">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pd5  ">
                    <ol class="breadcrumb breadcrumb-arrows" itemscope
                        itemtype="http://schema.org/BreadcrumbList">
                        <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
                            <a href="HomePage" target="_self" itemprop="item" content=""><span
                                    itemprop="name">Trang chủ</span></a>
                            <meta itemprop="position" content="1" />
                        </li>

                        <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
                            <a href="MyOrderServlet" target="_self">
                                <span itemprop="item" content=""><span
                                        itemprop="name">Đơn hàng của tôi</span></span>
                            </a>
                            <meta itemprop="position" content="2" />
                        </li>
                    </ol>
                </div>
            </div>

        </div>
    </div>
    <div class="container" style="margin-top: 15px">
        <div class="row justify-content-center">
            <div class="col-md-2">
            </div>
            <div class="col-md-8" id="order-container">
                <c:set value="${requestScope.htmlResponse}" var="htmlResponse"/>
                ${htmlResponse}
            </div>
            <div class="col-md-2">
            </div>
        </div>
    </div>
    <div class="sortpagibar pagi clearfix text-center">
        <div id="pagination" class="clearfix">

            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <form id="paginationForm" action="MyOrderServlet" method="post">
                    <input type="hidden" name="action" value="pagination">
                    <div id="page-pagination">
                        <c:forEach var="page" items="${pagenumber}">
                            <input type="radio" name="page" id="page${page}" value="${page}" style="display: none;">
                            <label for="page${page}" style="width: 25px; border: groove;" class="page-node" aria-label="Trang ${page}">${page}</label>
                        </c:forEach>
                        <span class="page-node">&hellip;</span>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@include file="HomeFooter.jsp" %>
    <script>
        function handleReceivedButtonClick(order_id) {
            // Gửi yêu cầu AJAX để cập nhật trạng thái đơn hàng trên máy chủ
            var confirmDone = confirm("Bạn đã nhận được đơn hàng?");
            if (confirmDone) {
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "MyOrderServlet", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            // Xử lý phản hồi thành công (nếu cần)
                            alert("Đã nhận đơn hàng thành công!");
                            // Reload trang sau khi xác nhận hủy đơn hàng thành công
                            window.location.reload();
                        } else {
                            // Xử lý lỗi nếu cần
                            alert("Có lỗi xảy ra khi nhận đơn hàng.");
                        }
                    }
                };
                xhr.send("order_id=" + order_id + "&action=ConfirmOrder");
            } else {
                // Người dùng chọn No, không làm gì cả
            }
        }

        //Phân trang
        $(document).ready(function () {
            $('.page-node').click(function (event) {
                event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết
                var pageValue = $(this).text();
                $("input[name='page'][value='" + pageValue + "']").prop('checked', true);
                submitFormWithAjax();
            });
            function submitFormWithAjax() {
                var form = $('#paginationForm')[0]; // Lấy form theo id
                $.ajax({
                    type: form.getAttribute("method"),
                    url: form.getAttribute("action"),
                    data: $(form).serialize(),
                    success: function (response) {
                        $("#order-container").html(response); // Cập nhật nội dung sản phẩm
                    },
                    error: function (xhr, status, error) {
                        console.error("Error: " + error);
                    }
                });
            }
        });


    </script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>

</html>
