<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết sản phẩm</title>
        <link rel="icon" href="image/logoshop.png" type="image/png">
        <link rel="stylesheet" href="css/main.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.ckeditor.com/4.16.2/standard/ckeditor.js"></script>

        <style>
        </style>
    </head>
    <body>
        <div class="wrapper">
            <%@include file="DashboardNavbar.jsp" %>
            <div class="main">
                <%@include file="DashboardHeader.jsp" %>
                <div class="container" style="margin-top: 20px; margin-bottom: 20px">
                    <section class="section">
                        <a href="ProductListMKTServlet" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Quay lại</a>
                        <div class="container-fluid" >
                            <div class="title-wrapper pt-30">
                                <div class="row align-items-center">
                                    <div class="col-md-9">
                                        <div class="title">
                                            <h2>Chi tiết sản phẩm</h2>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div style="display: flex; justify-content: space-evenly">
                                            <!-- Edit Product Modal Trigger -->
                                            <button id="edit-button" type="button" class="btn btn-primary ml-10">
                                                <i class="fas fa-edit"></i> Chỉnh sửa
                                            </button>

                                            <!-- Delete Product Modal Trigger -->
                                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteProductModal">
                                                <i class="fas fa-trash-alt"></i> Xóa
                                            </button>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <hr/>
                            <c:set value="${requestScope.product}" var="product"/>
                            <div id="edit-container" style="display: none">
                                <div class="edit-header">
                                    <h3 class="edit-title" id="editProductModalLabel">Chỉnh sửa sản phẩm</h3>
                                </div>
                                <div class="edit-body">
                                    <form id="editProductForm" action="ManageProductServlet" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="action" value="editProduct">
                                        <input type="hidden" name="editProductId" id="editProductId" value="${product.id}">
                                        <div class="row" style="margin-left: 0px">
                                            <div class="col-md-6 row">
                                                <div class="col-md-6" style="border: groove; padding: 0">
                                                    <img id="imageProductPreview" src="image/product/${product.image}" alt="" style="max-width: 100%">
                                                </div>
                                                <div class="col-md-6">
                                                    <div style="margin-bottom: 5px">
                                                        <b>Tên sản phẩm:</b><input name="productName" value="${product.name}" style="width: 243px"/><br/>
                                                    </div>
                                                    <div style="margin-bottom: 5px">
                                                        <b>Danh mục:</b>
                                                        <select name="categoryID" style="width: 120px; height: 23px; margin-left: 35px">
                                                            <c:forEach items="${requestScope.categoryList}" var="category">
                                                                <c:choose>
                                                                    <c:when test="${category.id == product.category_id}">
                                                                        <option value="${category.id}" selected>${category.category}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${category.id}">${category.category}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div style="margin-bottom: 5px">
                                                        <b>Thương hiệu:</b> 
                                                        <select name="brandID" style="margin-left: 20px; width: 120px; height: 23px;">
                                                            <c:forEach items="${requestScope.brandList}" var="brand">
                                                                <c:choose>
                                                                    <c:when test="${brand.id == product.brand_id}">
                                                                        <option value="${brand.id}" selected>${brand.brandname}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${brand.id}">${brand.brandname}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div style="margin-bottom: 5px">
                                                        <b>Phòng:</b>
                                                        <select name="roomID" style="margin-left: 58px; width: 120px; height: 23px;">
                                                            <c:forEach items="${requestScope.roomList}" var="room">
                                                                <c:choose>
                                                                    <c:when test="${room.id == product.room_id}">
                                                                        <option value="${room.id}" selected>${room.roomname}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${room.id}">${room.roomname}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div style="margin-bottom: 5px">
                                                        <b>Giá:</b><input type="text" name="priceProduct" value="${product.price}" style="margin-left: 82px; width: 120px; height: 23px;"/> ₫<br/> 
                                                    </div>

                                                    <div style="margin-bottom: 5px">
                                                        <b>Số lượng:</b><input type="text" name="quantityProduct" value="${product.quantity}" style="margin-left: 44px; width: 120px; height: 23px;"/><br/>
                                                    </div>

                                                    <div style="margin-bottom: 5px">
                                                        <b>Trạng thái:</b>
                                                        <select name="statusProduct" style="margin-left: 36px; width: 120px; height: 23px;">
                                                            <c:choose>
                                                                <c:when test="${product.status == 'Active'}">
                                                                    <option value="Active" selected>Active</option>
                                                                    <option value="Inactive">Inactive</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="Active">Active</option>
                                                                    <option value="Inactive" selected>Inactive</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </div>
                                                    <b>Ảnh chính: </b>
                                                    <input type="hidden" name="oldImageProduct" value="${product.image}"/>
                                                    <input type="file" name="imageProduct" id="imageProductInput" onchange="previewImage(event, 'imageProductPreview')" accept="image/*" style="width: 190px"/>
                                                </div>
                                                <div class="row" style="padding: 0px">
                                                    <div class="col-md-12" style="padding: 0px;">
                                                        <b>Mô tả:</b><br/>
                                                        <textarea name="desciptionProduct" id="editor" rows="8" cols="56">
                                                            ${product.description}
                                                        </textarea>
                                                    </div>
                                                </div>    
                                            </div>
                                            <script>
                                                CKEDITOR.replace('editor');
                                            </script>
                                            <div class="col-md-6">
                                                <div style="margin-top: 5px">
                                                    <c:set var="count" value="1" scope="page"/>
                                                    <c:set var="countImg" value="1" scope="page"/>
                                                    <c:forEach items="${requestScope.productDetailList}" var="productDetail">
                                                        <c:if test="${productDetail.product_id == product.id}">
                                                            <input type="hidden" name="productDetailID_${count}" value="${productDetail.id}"/>
                                                            <c:forEach items="${requestScope.colorList}" var="color">
                                                                <c:if test="${productDetail.color_id == color.id}">
                                                                    <span style="margin-bottom: 5px">
                                                                        <b>Màu sắc:</b>
                                                                        <select name="colorID_${count}" style="width: 140px; margin-left: 14px; height: 23px;">
                                                                            <c:forEach items="${requestScope.colorList}" var="color">
                                                                                <c:choose>
                                                                                    <c:when test="${productDetail.color_id == color.id}">
                                                                                        <option value="${color.id}" selected>${color.colorname}</option>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <option value="${color.id}">${color.colorname}</option>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </span>
                                                                </c:if>
                                                            </c:forEach>
                                                            <b>Số lượng:</b> <input type="text" name="quantityProductDetail_${count}" value="${productDetail.quantity}" style="width: 50px; margin-left: 10px"/><br/>
                                                            <b>Trạng thái:</b>
                                                            <select name="statusProductDetail_${count}" style="margin-left: 4px; width: 140px; height: 23px;">
                                                                <c:choose>
                                                                    <c:when test="${productDetail.status == 'Active'}">
                                                                        <option value="Active" selected>Active</option>
                                                                        <option value="Inactive">Inactive</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="Active">Active</option>
                                                                        <option value="Inactive" selected>Inactive</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </select>
                                                            <c:set var="count" value="${count + 1}" scope="page"/>
                                                            <div class="modal-body row">
                                                                <c:forEach items="${requestScope.attachedImageList}" var="attachedImage">
                                                                    <c:if test="${productDetail.id == attachedImage.productdetail_id}">
                                                                        <input type="hidden" name="attachedImageID_${countImg}" value="${attachedImage.id}"/>
                                                                        <input type="hidden" name="oldImageAttached_${countImg}" value="${attachedImage.image}"/>
                                                                        <div class="col-md-3">
                                                                            <div style="border: groove; padding: 0">
                                                                                <img id="imagePreview_${attachedImage.id}" src="image/product/${attachedImage.image}" alt="${attachedImage.image}" style="max-width: 100%">
                                                                            </div>
                                                                            <div style="margin-bottom: 5px; display: flex; justify-content: center">
                                                                                <input type="file" name="imageProductDetail_${countImg}" id="imageProductDetailInput_${attachedImage.id}" style="width: 80px; margin-top: 5px" onchange="previewImage(event, 'imagePreview_${attachedImage.id}')" accept="image/*"/>
                                                                            </div>
                                                                            <c:set var="countImg" value="${countImg + 1}" scope="page"/>
                                                                        </div>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </div><hr>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div style="display: flex; justify-content: center; margin-top: 20px">
                                                <div>
                                                    <button id="cancel-button" type="button" class="btn btn-warning" style="margin-right: 50px">Hủy</button>
                                                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                                                </div>

                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <div id="product-container" style="display: block">
                                <div class="row" style="margin-left: 0px">
                                    
                                    <div class="col-md-6 row">
                                        <div class="col-md-6" style="border: groove; padding: 0">
                                            <img src="image/product/${product.image}" alt="" style="max-width: 100%">
                                        </div>
                                        <div class="col-md-6">
                                            <b>Mã sản phẩm:</b> #${product.id}<br/>
                                            <b>Tên sản phẩm:</b> ${product.name}<br/>
                                            <b>Danh mục:</b> 
                                            <c:forEach items="${requestScope.categoryList}" var="category">
                                                <c:if test="${product.category_id == category.id}">${category.category}</c:if>
                                            </c:forEach><br/>
                                            <b>Thương hiệu:</b> 
                                            <c:forEach items="${requestScope.brandList}" var="brand">
                                                <c:if test="${product.brand_id == brand.id}">${brand.brandname}</c:if>
                                            </c:forEach><br/>
                                            <b>Điểm đánh giá:</b> ${product.staravg} sao<br/>
                                            <b>Giá:</b><fmt:formatNumber value="${product.price}" type="number" minFractionDigits="0" maxFractionDigits="0" groupingUsed="true" />₫<br/>
                                            <b>Phòng:</b> 
                                            <c:forEach items="${requestScope.roomList}" var="room">
                                                <c:if test="${product.room_id == room.id}">${room.roomname}</c:if>
                                            </c:forEach><br/>
                                            <b>Số lượng:</b> ${product.quantity}<br/>
                                            <b>Trạng thái:</b> ${product.status}<br/>
                                            <c:forEach items="${requestScope.productDetailList}" var="productDetail">
                                                <c:if test="${productDetail.product_id == product.id}">
                                                    <c:forEach items="${requestScope.colorList}" var="color">
                                                        <c:if test="${productDetail.color_id == color.id}">
                                                            <b>Màu sắc:</b> ${color.colorname}<br/>
                                                        </c:if>
                                                    </c:forEach>
                                                    <b>Số lượng:</b> ${productDetail.quantity}<br/>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                        <div class="col-md-12">
                                            <b>Mô tả:</b><br/>
                                            ${product.description}
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <c:forEach items="${requestScope.productDetailList}" var="productDetail">
                                            <c:if test="${productDetail.product_id == product.id}">
                                                <c:forEach items="${requestScope.colorList}" var="color">
                                                    <c:if test="${productDetail.color_id == color.id}">
                                                        <b>Màu sắc:</b> ${color.colorname}<br/><br/>
                                                    </c:if>
                                                </c:forEach>
                                                <div class="row">
                                                    <c:forEach items="${requestScope.attachedImageList}" var="attachedImage">
                                                        <c:if test="${productDetail.id == attachedImage.productdetail_id}">
                                                            <div class="col-md-3" style="border: groove; padding: 0">
                                                                <img src="image/product/${attachedImage.image}" alt="${attachedImage.image}" style="max-width: 100%;">
                                                            </div>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>



        <!--Modal Delete--> 
        <div class="modal fade" id="deleteProductModal" tabindex="-1" aria-labelledby="deleteProductModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteProductModalLabel">Xác nhận xóa</h5>
                        <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc chắn muốn xóa sản phẩm này ?</p>
                        <form id="deleteProductForm" action="ManageProductServlet" method="post">
                            <input type="hidden" name="action" value="deleteProduct">
                            <input type="hidden" name="deleteProductId" id="deleteProductId" value="${product.id}">
                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-danger mx-2" style="width: 70px;">Có</button>
                                <button type="button" class="btn btn-secondary mx-2" data-dismiss="modal" style="width: 70px;">Không</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="DashboardFooter.jsp" %>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
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
        <script>
            
            $(document).ready(function () {
                $('#cancel-button').click(function () {
                    $('#edit-container').hide();
                    $('#product-container').show();
                });

                $('#edit-button').click(function () {
                    $('#product-container').hide();
                    $('#edit-container').show();
                });
            });
        </script>
    </body>
</html>