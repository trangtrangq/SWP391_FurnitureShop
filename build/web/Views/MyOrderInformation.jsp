<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html >

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyOrderInformation</title>
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
            background: #eee;
        }

        .card {
            box-shadow: 0 20px 27px 0 rgb(0 0 0 / 5%);
        }

        .card {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #fff;
            background-clip: border-box;
            border: 0 solid rgba(0, 0, 0, .125);
            border-radius: 1rem;
        }

        .text-reset {
            --bs-text-opacity: 1;
            color: inherit !important;
        }

        a {
            color: #5465ff;
            text-decoration: none;
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
                        <li class="active" itemprop="itemListElement" itemscope
                            itemtype="">
                            <a href="" target="_self">
                                <span itemprop="item" content=""><span
                                        itemprop="name">Chi tiết đơn hàng</span>
                                </span>
                            </a>
                            <meta itemprop="position" content="3" />
                        </li>
                    </ol>
                </div>
            </div>

        </div>
    </div>
    <div class="container-fluid" style="margin-top: 15px">
        <div class="container">
            <div class="row">
                <div class="col-md-1">
                </div>
                <div class="col-md-7">
                    <!-- Details -->
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="mb-3 d-flex justify-content-between">
                                <c:set var="order" value="${requestScope.order}"/>
                                <input type="hidden" name="order_id" value="${order.id}">
                                <div class="me-auto">
                                    <span><h3>OrderID: #${order.id}</h3></span>
                                </div>
                                <div class="ms-auto">
                                    <span>OrderTime: ${order.orderDate}</span>
                                </div>
                            </div>
                            <table class="table table-borderless">
                                <tbody>
                                    <c:forEach var="orderDetail" items="${orderDetailList}">
                                        <c:if test="${orderDetail.order_id == order.id}">
                                            <c:forEach var="productDetail" items="${productDetailList}">
                                                <c:if test="${productDetail.id == orderDetail.productdetail_id}">
                                                    <c:forEach var="product" items="${productList}">
                                                        <c:if test="${product.id == productDetail.product_id}">
                                                            <c:forEach var="user" items="${userList}"> 
                                                            </c:forEach> 
                                                        <input type="hidden" id="id" name="id" value="${productDetail.id}">
                                                        <tr>
                                                            <td>
                                                                <div class="d-flex mb-2">
                                                                    <div class="flex-shrink-0">
                                                                        <img src="image/product/${product.image}"
                                                                             alt="" width="35" class="img-fluid">
                                                                    </div>
                                                                    <div class="flex-lg-grow-1 ms-3">
                                                                        <h6 class="small mb-0">${product.name}</h6>
                                                                        <c:forEach var="color" items="${colorList}">
                                                                            <c:if test="${productDetail.color_id == color.id}">
                                                                                <span class="small">${color.colorname}</span>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                        <c:forEach var="category" items="${categoryList}">
                                                                            <c:if test="${product.category_id == category.id}">
                                                                                <span class="small">(Phân loại: ${category.category})</span> <!-- Thêm dấu ngoặc đơn ở đây -->
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td>${orderDetail.quantity}</td>
                                                            <td class="text-end text-danger" style="text-decoration: line-through;">
                                                                <fmt:formatNumber value="${product.price}" type="number" minFractionDigits="0" maxFractionDigits="0" groupingUsed="true" />₫
                                                            </td>
                                                            <td class="text-end">
                                                                <fmt:formatNumber value="${orderDetail.price}" type="number" minFractionDigits="0" maxFractionDigits="0" groupingUsed="true" />₫
                                                            </td>
                                                        <div style="display: none">
                                                            <input type="hidden" id="priceunit" name="priceunit" value="${orderDetail.price}">
                                                        </div>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                    <tr class="fw-bold">
                                        <td colspan="4" style="text-align: right; padding-right: 50px">TOTAL: 
                                            <fmt:formatNumber value="${order.totalcost}" type="number" minFractionDigits="0" maxFractionDigits="0" groupingUsed="true" />₫
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 card mb-4">
                    <div class="card-body">
                        <div class="row">
                            <div>
                                <c:set var="order" value="${requestScope.order}"/>
                                <input type="hidden" name="order_id" value="${order.id}">
                                <c:forEach items="${address}" var="address">
                                    <c:if test="${address.id == order.address_id}">
                                        <c:forEach items="${userList}" var="user">
                                            <c:if test="${user.id == address.customer_id}">

                                                <h3 class="h6">Thông tin người nhận</h3>
                                                <address>
                                                    <strong>Full name: ${address.fullname}</strong><br>
                                                    Gender: ${user.gender}<br>
                                                    Email: ${user.email}<br>
                                                    Phone: ${address.phonenumber}<br>
                                                    Address: ${address.address}<br>
                                                    <!--                                                        <abbr title="Phone">P:</abbr> (123) 456-7890-->
                                                </address>
                                                <div style="display: flex; justify-content: center">
                                                    <c:choose>
                                                        <c:when test="${order.status == 'Order'}">
                                                            <button class="btn btn-secondary" style="width: 80px; height: 30px; margin-right: 20px">Đang xử lí</button>
                                                            <button id="buy_now" class="btn btn-primary" style="height: 30px; margin-top: 0;">Cập nhật đơn hàng</button>
                                                            <button class="btn btn-danger" onclick="confirmCancelOrder(${order.id})" style="width: 80px; height: 30px; margin-left: 20px">Hủy đơn hàng</button>
                                                        </c:when>
                                                        <c:when test="${order.status == 'Canceled'}">
                                                            <button class="btn btn-danger" style="width: 80px; height: 30px;">Đã hủy</button>
                                                        </c:when>
                                                        <c:when test="${order.status == 'Confirmed'}">
                                                            <button style=" height: 30px" class="btn btn-info">Đang xử lí</button>
                                                        </c:when> 
                                                        <c:when test="${order.status == 'Done'}">
                                                            <c:set var="hasFeedback" value="false" />
                                                            <c:forEach items="${requestScope.historyFeedbackOrder}" var="history">
                                                                <c:if test="${order.id == history}">
                                                                    <c:set var="hasFeedback" value="true" />
                                                                </c:if>
                                                            </c:forEach>

                                                            <c:choose>
                                                                <c:when test="${hasFeedback}">
                                                                    <button class="btn btn-success" style="margin-right: 10px">Hoàn thành đơn hàng</button>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <button class="btn btn-warning" style="margin-right: 10px">Hoàn thành đơn hàng</button>
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <c:if test="${!hasFeedback}">
                                                                <button class="btn btn-primary" style=" height: 30px; margin-left: 10px">
                                                                    <a href="FeedbackServlet?order_id=${order.id}" style="color: white; text-decoration: none;">Đánh giá</a>
                                                                </button>
                                                            </c:if>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button class="btn btn-warning" style="width: 80px; height: 30px;">${order.status}</button>
                                                        </c:otherwise>
                                                    </c:choose>

                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form id="hiddenForm" action="${pageContext.request.contextPath}/AddToCart" method="get" style="display: none;">
        <input type="hidden" id="productDetailId" name="productDetailId" value="">
        <input type="hidden" id="quantityproduct" name="quantityproduct" value="">
        <input type="hidden" id="price" name="price" value="">
        <input type="hidden" id="action" name="action" value="">
    </form>
    <%@include file="HomeFooter.jsp" %>
    <script>
        function confirmCancelOrder(order_id) {
            var confirmCancel = confirm("Bạn có muốn hủy đơn hàng của mình không?");
            if (confirmCancel) {
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "MyOrderInformationServlet", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            // Xử lý phản hồi thành công (nếu cần)
                            alert("Đã hủy đơn hàng thành công!");
                            // Reload trang sau khi xác nhận hủy đơn hàng thành công
                            window.location.reload();
                        } else {
                            // Xử lý lỗi nếu cần
                            alert("Có lỗi xảy ra khi hủy đơn hàng.");
                        }
                    }
                };
                xhr.send("order_id=" + order_id);
            } else {
                // Người dùng chọn No, không làm gì cả
            }
        }


        document.getElementById('buy_now').addEventListener('click', function () {

            var productDetailId = document.getElementById('id').value;
            var quantity = 1;
            var price = document.getElementById('priceunit').value;

            document.getElementById('productDetailId').value = productDetailId;
            document.getElementById('quantityproduct').value = quantity;
            document.getElementById('price').value = price;
            document.getElementById('action').value = "buynow";

            document.getElementById('hiddenForm').submit();
        });
    </script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>




</html>