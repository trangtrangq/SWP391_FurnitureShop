<%-- 
    Document   : ProductListMKT
    Created on : Jun 5, 2024, 2:27:59 PM
    Author     : HELLO
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="css/main.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        th,
        td {
            padding: 10px;
            text-align: left;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 150px;
            /* Set a maximum width as needed */
            text-align: center;
        }

        .dropdown {
            position: relative;
            display: inline-block;
            font-family: Arial, sans-serif;
            margin: 10px 5px;
        }

        .dropbtn {
            background-color: white;
            color: black;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            cursor: pointer;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
            z-index: 1;
            padding-left: 0px;
        }

        .dropdown-content a {
            color: black;
            padding: 5px 10px;
            text-decoration: none;
            display: block;
        }

        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        .dropdown:hover .dropdown-content {
            display: block;
        }

        .dropdown:hover .dropbtn {
            background-color: #f1f1f1;
        }
        .actions {
            /*                display: flex;
                            justify-content: center;
                            align-items: center;*/
        }
        .actions a {
            margin: 0 5px;
            color: black;
            text-decoration: none;
        }
        .actions a:hover {
            color: blue;
        }
        .add-new-product-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 40px;
            width: 155px;
            background-color: #fff;
            border: 1px solid #ccc;
            font-size: 16px;
            cursor: pointer;
        }
        .image {
            border-radius: 4px;
            overflow: hidden;
            margin-right: 15px;
            max-width: 50px;
            width: 100%;
            height: 50px;
        }
        .image img {
            width: 100%;
        }

        .custom-close-btn {
            position: absolute;
            right: 1.5rem; /* Adjust as needed */
            z-index: 1051; /* Ensure it appears above the modal content */
        }
    </style>
</head>

<body>
    <div class="wrapper">
        <%@include file="DashboardNavbar.jsp" %>
        <div class="main">
            <%@include file="DashboardHeader.jsp" %>
            <div class="container" style="margin-top: 20px; margin-bottom: 20px">
                <section class="section">
                    <div class="container-fluid" style="padding-left: 13px">
                        <div class="title-wrapper pt-30">
                            <div class="row align-items-center">
                                <div class="col-md-6">
                                    <div class="title">
                                        <h2>User List</h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="ml-15 mr-15" style="display: flex; justify-content: space-between">
                        <div>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addUserModal">
                                Add New User
                            </button>
                            <button type="button" class="btn btn-primary" onclick="reloadPage()">
                                Reload Page
                            </button>
                        </div>
                        <div class="dropdown" style="width: 180px; margin-left: 65px;">
                            <div class="dropbtn">
                                SẮP XẾP
                                <span><i class="fa fa-chevron-down"></i></span>
                            </div>
                            <ul class="dropdown-content" style="min-width: 180px">
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=IdAsc">ID tăng dần</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=IdDesc">ID giảm dần</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=fullnameAsc">Tên tăng từ A-Z</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=fullnameDesc">Tên giảm từ Z-A</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=genderAsc">Giới tính từ A-Z</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=genderDesc">Giới tính từ Z-A</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=emailAsc">Email từ A-Z</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=emailDesc">Email từ Z-A</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=phoneAsc">Số điện thoại tăng dần</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=phoneDesc">Số điện thoại giảm dần</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=roleAsc">Role từ A-Z</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=roleDesc">Role từ Z-A</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=statusUserAsc">Status từ A-Z</a></li>
                                <li><a href="${pageContext.request.contextPath}/UserListServlet?sortby=statusUserDesc">Status từ Z-A</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- Add New Product Modal -->
                    <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="addProductModalLabel">Add New User</h5>
                                    <button type="button" class="close btn btn-danger" data-dismiss="modal" aria-label="Close" style="margin-left: 532px;">
                                        <span aria-hidden="true" style="width: 40px; height: 20px">X</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form id="addUserForm" action="ManageUserServlet" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="action" value="addNewUser">
                                        <div class="form-group">
                                            <label for="fullname">Họ và tên</label>
                                            <input type="text" class="form-control" id="fullname" name="fullname" required value="${param.fullname}">
                                        </div>
                                        <div class="form-group">
                                            <label>Giới tính</label><br>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="gender" value="Male" id="genderMale" ${param.gender == 'Male' ? 'checked' : ''} required value="${param.gender}">
                                                <label class="form-check-label" for="genderMale">Male</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="gender" value="Female" id="genderFemale" ${param.gender == 'Female' ? 'checked' : ''} required value="${param.gender}">
                                                <label class="form-check-label" for="genderFemale">Female</label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="phone">Số điện thoại</label>
                                            <input type="text" class="form-control" id="phone" name="phone" required value="${param.phone}">
                                        </div>
                                        <div class="form-group">
                                            <label for="address">Địa chỉ</label>
                                            <input type="text" class="form-control" id="address" name="address" required value="${param.address}">
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Email</label>
                                            <input type="email" class="form-control" id="email" name="email" required value="${param.email}">
                                        </div>                                        
                                        <div class="form-group">
                                            <label for="roleId">Role:</label>
                                            <select class="form-control" id="roleId" name="roleID">
                                                <c:forEach items="${requestScope.userRole}" var="userRole">
                                                    <option value="${userRole.id}">${userRole.rolename}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="status">Trạng thái:</label>
                                            <select class="form-control" id="status" name="status">
                                                <option value="In Active">In Active</option>
                                            </select>
                                        </div>
                                        <div class="d-flex" style="justify-content: center; margin-top: 10px">
                                            <button type="submit" class="btn btn-primary">Save</button>
                                        </div>
                                        <div id="error-message" class="alert alert-danger" style="display: none; margin-top: 10px;">${requestScope.mess}</div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="ml-15 mb-15">
                        <form method="post">
                            <div class="d-flex">
                                <input type="text" class="form-control" name="search" placeholder="Họ và tên, email, số điện thoại" style="width: 350px;" />
                                <button class="btn btn-primary" type="submit">Search</button>
                            </div>
                        </form>
                    </div>       

                    <div class="d-flex">
                        <form method="post">
                            <div class="dropdown" style="width: 100px;">
                                <div class="dropbtn">
                                    Gender
                                    <span><i class="fa fa-chevron-down"></i></span>
                                </div>
                                <ul class="dropdown-content" style="min-width: 100px;">
                                        <li>
                                        <input type="checkbox" name="gender-filter" value="Male"
                                               <c:if test="${not empty selectedGenderList and selectedGenderList.contains('Male')}">checked</c:if>
                                                   style="display: inline-block" /> Male
                                        </li>
                                        <li>
                                            <input type="checkbox" name="gender-filter" value="Female"
                                            <c:if test="${not empty selectedGenderList and selectedGenderList.contains('Female')}">checked</c:if>
                                                style="display: inline-block" /> Female
                                        </li>
                                    </ul>
                                </div>
                                <div class="dropdown" style="width: 150px;">
                                    <div class="dropbtn">
                                        Role
                                        <span><i class="fa fa-chevron-down"></i></span>
                                    </div>
                                    <ul class="dropdown-content" style="min-width: 150px;">
                                    <c:forEach items="${requestScope.userRole}" var="userRole">
                                        <li>
                                            <div>
                                                <input type="checkbox" value="${userRole.id}" name="role_id-filter" 
                                                       <c:if test="${not empty selectedRoleList and selectedRoleList.contains(userRole.id)}">checked</c:if>
                                                       /> ${userRole.rolename}
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <div class="dropdown" style="width: 100px;">
                                <div class="dropbtn">
                                    Status
                                    <span><i class="fa fa-chevron-down"></i></span>
                                </div>
                                <ul class="dropdown-content" style="min-width: 100px;">
                                        <li>
                                        <input type="checkbox" name="status-filter" value="Active"
                                               <c:if test="${not empty selectedStatusList and selectedStatusList.contains('Active')}">checked</c:if>
                                                   style="display: inline-block" /> Active
                                        </li>
                                        <li>
                                            <input type="checkbox" name="status-filter" value="Inactive"
                                            <c:if test="${not empty selectedStatusList and selectedStatusList.contains('Inactive')}">checked</c:if>
                                                style="display: inline-block" /> In Active
                                        </li>
                                        <li>
                                            <input type="checkbox" name="status-filter" value="Block"
                                            <c:if test="${not empty selectedStatusList and selectedStatusList.contains('Block')}">checked</c:if>
                                                style="display: inline-block" /> Block
                                        </li>
                                    
                                </ul>
                            </div>
                            |
                            <button type="submit" class="btn btn-primary ml-10 mr-10">Lọc</button>
                            <button class="btn btn-secondary"><a href="${pageContext.request.contextPath}/UserListServlet?clearfilter=yes" style="text-decoration: none; color: black;">Bỏ lọc</a></button>
                        </form>
                    </div>


                    <div>
                        <div class="card-style ml-15 mr-15">
                            <div class="table-responsive">
                                <table class="table top-selling-table">
                                    <thead>
                                        <tr>
                                            <th>
                                                <h6 class="text-sm text-medium" style="text-align: center;">ID</h6>
                                            </th>

                                            <th>
                                                <h6 class="text-sm text-medium" style="text-align: center;">Full name</h6>
                                            </th>
                                            <th class="min-width">
                                                <h6 class="text-sm text-medium" style="text-align: center;">Gender</h6>
                                            </th>
                                            <th class="min-width">
                                                <h6 class="text-sm text-medium" style="text-align: center;">Email</h6>
                                            </th>
                                            <th class="min-width">
                                                <h6 class="text-sm text-medium" style="text-align: center;">Phone Number</h6>
                                            </th>

                                            <th class="min-width">
                                                <h6 class="text-sm text-medium" style="text-align: center;">Role</h6>
                                            </th>
                                            <th class="min-width">
                                                <h6 class="text-sm text-medium" style="text-align: center;">Status</h6>
                                            </th>

                                            <th class="min-width">
                                                <h6 class="text-sm text-medium" style="text-align: center;">Action</h6>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>                                            
                                        <c:forEach items="${requestScope.userList}" var="userList">
                                            <tr>
                                                <td>
                                                    <div>
                                                        <p class="text-sm">${userList.id}</p>
                                                    </div>
                                                </td>

                                                <td>
                                                    <div class="text-sm">
                                                        <p class="text-sm">${userList.fullname}</p>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="text-sm">
                                                        <p class="text-sm">${userList.gender}</p>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="text-sm">
                                                        <p class="text-sm">${userList.email}</p>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="text-sm">
                                                        <p class="text-sm">${userList.phonenumber}</p>
                                                    </div>
                                                </td>
                                                <td>
                                                    <p class="text-sm">
                                                        <c:forEach items="${requestScope.userRole}" var="userRole">
                                                            <c:if test="${userList.role_id == userRole.id}">${userRole.rolename}
                                                            </c:if>
                                                        </c:forEach>
                                                    </p>
                                                </td>                                            
                                                <td>
                                                    <p class="text-sm">${userList.status}</p>
                                                </td>
                                                <td class="actions">
                                                    <p>
                                                        <a href="#" title="View" data-toggle="modal" data-target="#viewProductModal_${userList.id}"><i class="fas fa-eye"></i></a>
                                                        <a href="#" title="Edit" data-toggle="modal" data-target="#editUserModel_${userList.id}"><i class="fas fa-edit"></i></a>
                                                        <a href="#" title="Delete" data-toggle="modal" data-target="#deleteProductModal_${product.id}"><i class="fas fa-trash-alt"></i></a>
                                                    </p>

                                                </td>
                                            </tr>

                                            <!--Modal View Detail-->
                                        <div class="modal fade" id="viewProductModal_${userList.id}" tabindex="-1" aria-labelledby="viewProductModalLabel_${userList.id}" aria-hidden="true">
                                            <div class="modal-dialog modal-fullscreen">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="viewProductModalLabel">User Details</h5>
                                                        <button type="button" class="close custom-close-btn btn btn-danger" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true" style="width: 40px; height: 20px">X</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="col-md-6 row">
                                                                <div class="col-md-6">
                                                                    <img src="image/avatar/${userList.avatar}" alt="" style="max-width: 100%">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <b>User ID:</b> #${userList.id}<br/>
                                                                    <b>Full Name:</b> ${userList.fullname}<br/>
                                                                    <b>Gender:</b> ${userList.gender}<br/>
                                                                    <b>Email:</b> ${userList.email}<br/>
                                                                    <b>Phone Number:</b> ${userList.phonenumber}<br/>
                                                                    <b>Role:</b> 
                                                                    <c:forEach items="${requestScope.userRole}" var="userRole">
                                                                        <c:if test="${userList.role_id == userRole.id}">${userRole.rolename}</c:if>
                                                                    </c:forEach><br/>
                                                                    <b>Address:</b> ${userList.address}<br/>
                                                                    <b>Status:</b> ${product.status}<br/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!--Modal Edit-->
                                        <div class="modal fade" id="editUserModel_${userList.id}" tabindex="-1" aria-labelledby="editUserModelLabel_${userList.id}" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="editUserModelLabel_${userList.id}">Edit User</h5>
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form id="editProductForm" action="ManageUserServlet" method="post">
                                                            <input type="hidden" name="action" value="editUser">                                  
                                                            <input type="hidden" name="editUserId" id="editUserId" value="${userList.id}">
                                                            <b>User ID:</b> #${userList.id}<br/>
                                                            <div style="margin-bottom: 5px">
                                                                <b>Role:</b>
                                                                <select name="roleID" style="margin-left: 38px; width: 200px">
                                                                    <c:forEach items="${requestScope.userRole}" var="userRole">
                                                                        <c:choose>
                                                                            <c:when test="${userRole.id == userList.role_id}">
                                                                                <option value="${userRole.id}" selected>${userRole.rolename}</option>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <option value="${userRole.id}">${userRole.rolename}</option>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>

                                                            <div style="margin-bottom: 5px">
                                                                <b>Status:</b>
                                                                <select name="statusUser" style="margin-left: 25px; width: 200px">
                                                                    <c:choose>
                                                                        <c:when test="${userList.status == 'Active'}">
                                                                            <option value="Active" selected>Active</option>
                                                                            <option value="Inactive">In Active</option>
                                                                            <option value="Block">Block</option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <option value="Active">Active</option>
                                                                            <option value="Inactive" selected>In Active</option>
                                                                            <option value="Block">Block</option>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </select>
                                                            </div>
                                                            <div class="form-group text-center">
                                                                <button type="submit" class="btn btn-primary">Update</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Modal Delete -->
                                        <div class="modal fade" id="deleteProductModal_${product.id}" tabindex="-1" aria-labelledby="deleteProductModalLabel_${product.id}" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="deleteProductModalLabel_${product.id}">Confirm Delete</h5>
                                                        <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>Are you sure you want to delete this product?</p>
                                                        <form id="deleteProductForm" action="ManageProductServlet" method="post">
                                                            <input type="hidden" name="action" value="deleteProduct">
                                                            <input type="hidden" name="deleteProductId" id="deleteProductId" value="${product.id}">
                                                            <div class="d-flex justify-content-center">
                                                                <button type="submit" class="btn btn-danger mx-2" style="width: 70px;">Yes</button>
                                                                <button type="button" class="btn btn-secondary mx-2" data-bs-dismiss="modal" style="width: 70px;">No</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="sortpagibar pagi clearfix text-center">
                        <div id="pagination" class="clearfix">
                            <div>
                                <c:forEach var="page" items="${pagenumber}">
                                    <a class="mr-10" style="color: black; text-decoration: none;" href="UserListServlet?page=${page}" aria-label="Trang ${page}">${page}</a>
                                </c:forEach>
                                <span class="mr-10">&hellip;</span>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <%@include file="DashboardFooter.jsp" %>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
    <script>
                                $(document).ready(function () {
                                    $('#addUserForm').submit(function (e) {
                                        e.preventDefault(); // Ngăn chặn form submit mặc định

                                        $.ajax({
                                            type: 'POST',
                                            url: 'ManageUserServlet', // Đường dẫn tới servlet của bạn
                                            data: $('#addUserForm').serialize(),
                                            success: function (response) {
                                                if (response.trim() === 'success') {
                                                    console.log('Modal hide triggered');
                                                    console.log(response);
                                                    $('#addUserModal').modal('hide'); // Đóng modal khi thành công
                                                    alert('Email đã được gửi. Vui lòng xác nhận.');
                                                    setTimeout(function () {
                                                        location.reload(); // Tải lại trang sau khi modal đã được đóng
                                                    }, 500);
                                                } else {
                                                    $('#error-message').html('Tài khoản đã tồn tại.'); // Hiển thị thông báo lỗi trong div error-message
                                                    $('#error-message').show(); // Hiển thị div error-message
                                                }
                                            },
                                            error: function () {
                                                alert('Đã xảy ra lỗi trong quá trình xử lý.');
                                            }
                                        });
                                    });
                                });
                                function reloadPage() {
                                    fetch('/UserListServlet?action=logout', {method: 'GET'})
                                            .then(response => response.text())
                                            .then(data => {
                                                console.log(data); // Dùng để kiểm tra
                                                window.location.reload();
                                            })
                                            .catch(error => {
                                                console.error('Error:', error);
                                                // Xử lý lỗi nếu cần
                                            });
                                }
    </script>


    <script>
        function previewImage(event, imageId) {
            var input = event.target;
            var reader = new FileReader();

            reader.onload = function () {
                var dataURL = reader.result;
                var image = document.getElementById(imageId);
                image.src = dataURL;
            };
            reader.readAsDataURL(input.files[0]);
        }
    </script>

</body>
</html>
