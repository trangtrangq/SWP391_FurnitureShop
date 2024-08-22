<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.HashMap, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <title>Dashboard</title>
        <link rel="icon" href="image/logoshop.png" type="image/png">
    </head>
    <body>
        <div class="wrapper">
            <%@include file="DashboardNavbar.jsp" %>
            <div class="main">
                <%@include file="DashboardHeader.jsp" %>
                <div class="container bg-light">

                    <div class="row mb-3 mt-3">
                        <div class="col-auto d-none d-sm-block">
                            <h1><strong>Marketing</strong> Dashboard</h1>
                        </div>
                    </div>

                    <form action="MarketingDashboard">
                        <div class="row mb-3 align-items-end">
                            <div class="col-sm-6 col-md-3 mb-2">
                                <label class="col-form-label">Ngày bắt đầu:</label>
                                <input type="date" class="form-control" name="start" required value="${param.start != null ? param.start : ''}">
                            </div>
                            <div class="col-sm-6 col-md-3 mb-2">
                                <label class="col-form-label">Ngày kết thúc:</label>
                                <input type="date" class="form-control" name="end" required value="${param.end != null ? param.end : ''}">
                            </div>
                            <div class="col-md-2 mb-2">
                                <input type="submit" class="btn btn-primary btn-block" value="Lọc"
                                       style="width:auto">
                            </div>
                        </div>
                    </form>

                    <div class="row">
                        <div class="col-sm-6 col-xl-3">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col mt-0">
                                            <h5 class="card-title">Bài viết</h5>
                                        </div>

                                        <div class="col-auto">
                                            <div class="stat text-primary">
                                                <i class="bi bi-newspaper"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <h1 class="mt-1 mb-3 ">${postall}</h1>  
                                    <h4 class="mt-1 mb-3">+ ${post} bài viết mới</h4> 
                                </div>

                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col mt-0">
                                            <h5 class="card-title">Sản phẩm</h5>
                                        </div>

                                        <div class="col-auto">
                                            <div class="stat text-primary">
                                                <i class="bi bi-cart4"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <h1 class="mt-1 mb-3">${productall}</h1>
                                    <h4 class="mt-1 mb-3">+ ${product} sản phẩm mới</h4> 
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col mt-0">
                                            <h5 class="card-title">Khách hàng</h5>
                                        </div>

                                        <div class="col-auto">
                                            <div class="stat text-primary">
                                                <i class="bi bi-people"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <h1 class="mt-1 mb-3">${customerall}</h1>
                                    <h4 class="mt-1 mb-3">+ ${customer} khách hàng mới</h4> 
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xl-3">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col mt-0">
                                            <h5 class="card-title">Phản hồi</h5>
                                        </div>

                                        <div class="col-auto">
                                            <div class="stat text-primary">
                                                <i class="bi bi-chat-left-text"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <h1 class="mt-1 mb-3">${feedbackall}</h1>
                                    <h4 class="mt-1 mb-3">+ ${feedback} phản hồi mới</h4> 
                                </div>
                            </div>
                        </div>
                    </div>   

                    <div class="row justify-content-center d-flex mt-3">
                        <div class="col-8 card">
                            <canvas id="myChart"></canvas>   
                        </div>
                    </div>

                    <div class="row justify-content-center d-flex mt-3">
                        <div class="col-8 card">
                            <h3 class="text-center mt-2"><strong>Top sản phẩm bán chạy</strong></h3>
                            <table>
                                <tr class="border">
                                    <th class="border">Top</th>
                                    <th class="border">Tên sản phẩm</th>
                                    <th class="border">Số lượng bán</th>
                                </tr>
                                <c:set var="rank" value="1" />
                                <c:forEach var="entry" items="${topsell}">
                                    <tr>
                                        <td class="border">${rank}</td>
                                        <td class="border">${entry.key}</td>
                                        <td class="border">${entry.value}</td>
                                    </tr>
                                    <c:set var="rank" value="${rank + 1}" />
                                </c:forEach>
                            </table>   
                        </div>
                    </div>            


                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

                    <script>
                        <% 
                                            List<Map<String, Object>> customerCounts = (List<Map<String, Object>>) request.getAttribute("customerCounts");
                                            StringBuilder dates = new StringBuilder();
                                            StringBuilder counts = new StringBuilder();
            
                                            for (Map<String, Object> row : customerCounts) {
                                                dates.append("'").append(row.get("date").toString()).append("',");
                                                counts.append(row.get("customer_count")).append(",");
                                            }
                        %>

                        const ctx = document.getElementById('myChart').getContext('2d');
                        const customerChart = new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels: [<%= dates.substring(0, dates.length() - 1) %>], // Loại bỏ dấu phẩy cuối cùng
                                datasets: [{
                                        label: 'Số lượng khách hàng mới',
                                        data: [<%= counts.substring(0, counts.length() - 1) %>], // Loại bỏ dấu phẩy cuối cùng
                                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                        borderColor: 'rgba(75, 192, 192, 1)',
                                        borderWidth: 1
                                    }]
                            },
                            options: {
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                        ticks: {
                                            stepSize: 1 // Đảm bảo các giá trị trên trục tung là số nguyên
                                        }
                                    },
                                    x: {
                                        grid: {
                                            display: false // Bỏ lưới trục X
                                        }
                                    }
                                },
                                plugins: {
                                    legend: {
                                        labels: {
                                            font: {
                                                size: 16 // Tăng kích thước chữ của legend
                                            }
                                        }
                                    },
                                    title: {
                                        display: true,
                                        text: 'Biểu đồ số lượng khách hàng mới đăng kí theo ngày',
                                        font: {size: 20}
                                    }
                                }
                            }
                        });
                    </script>
                </div>
            </div>
        </div>
        <%@include file="DashboardFooter.jsp" %>
    </body>
</html>