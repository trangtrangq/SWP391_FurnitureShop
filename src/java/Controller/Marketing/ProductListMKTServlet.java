/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Marketing;

import DAL.AttachedImageDAO;
import DAL.BrandDAO;
import DAL.CategoryDAO;
import DAL.ColorDAO;
import DAL.FeedbackDAO;
import DAL.OrderDetailDAO;
import DAL.ProductDAO;
import DAL.ProductDetailDAO;
import DAL.RoomDAO;
import DAL.SaleOffDAO;
import Helper.ComparatorHelper;
import Helper.ConfigReaderHelper;
import Helper.FileUploadHelper;
import Helper.PaginationHelper;
import Models.AttachedImage;
import Models.Brand;
import Models.Category;
import Models.Color;
import Models.Feedback;
import Models.OrderDetail;
import Models.Product;
import Models.ProductDetail;
import Models.Room;
import Models.SaleOff;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author HELLO
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)

public class ProductListMKTServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "image/product";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void UpdateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int editProductId = tryParseInt(request.getParameter("editProductId"), 0);
        String productName = request.getParameter("productName");
        int categoryId = tryParseInt(request.getParameter("categoryID"), 0);
        int brandId = tryParseInt(request.getParameter("brandID"), 0);
        int roomId = tryParseInt(request.getParameter("roomID"), 0);
        double priceProduct = tryParseDouble(request.getParameter("priceProduct"), 0);
        int quantityProduct = tryParseInt(request.getParameter("quantityProduct"), 0);
        String statusProduct = request.getParameter("statusProduct");
        String desciptionProduct = request.getParameter("desciptionProduct");

        String image = "imageProduct";
        FileUploadHelper fileUpload = new FileUploadHelper();
        String imageProduct;
        Part filePart = request.getPart(image);
        if (filePart != null && filePart.getSize() > 0) {
            imageProduct = fileUpload.uploadFileAndReturnFileName(request, response, image, UPLOAD_DIRECTORY);
        } else {
            imageProduct = request.getParameter("oldImageProduct");
        }

        Product editProduct = new Product();
        editProduct.setId(editProductId);
        editProduct.setName(productName);
        editProduct.setCategory_id(categoryId);
        editProduct.setBrand_id(brandId);
        editProduct.setRoom_id(roomId);
        editProduct.setPrice(priceProduct);
        editProduct.setQuantity(quantityProduct);
        editProduct.setStatus(statusProduct);
        editProduct.setImage(imageProduct);
        editProduct.setDescription(desciptionProduct);

        PrintWriter out = response.getWriter();
        out.println("ImageProduct: " + editProduct.getImage());
        ProductDAO productDAO = new ProductDAO();
        productDAO.updateProduct(editProduct);

        int count = 1;
        while (true) {
            String productDetailIDStr = request.getParameter("productDetailID_" + count);
            out.println("Product Detail: " + productDetailIDStr);
            if (productDetailIDStr == null) {
                break;
            }
            int productDetailId = tryParseInt(productDetailIDStr, 0);
            out.println("Product Detail:" + productDetailId);
            int colorId = tryParseInt(request.getParameter("colorID_" + count), 0);
            out.println("Color: " + colorId);
            int quantityProductDetail = tryParseInt(request.getParameter("quantityProductDetail_" + count), 0);
            out.println("Quantity Product Detail: " + quantityProductDetail);
            String statusProductDetail = request.getParameter("statusProductDetail_" + count);
            out.println("Status Product Detail: " + statusProductDetail);

            ProductDetail editProductDetail = new ProductDetail();
            editProductDetail.setId(productDetailId);
            editProductDetail.setProduct_id(editProductId);
            editProductDetail.setColor_id(colorId);
            editProductDetail.setQuantity(quantityProductDetail);
            editProductDetail.setStatus(statusProductDetail);

            ProductDetailDAO productDetailDAO = new ProductDetailDAO();
            out.println(productDetailDAO.updateProductDetail(editProductDetail));

            int start = (count - 1) * 4 + 1;
            int end = start + 3;

            for (int i = start; i <= end; i++) {
                int attachedImageId = tryParseInt(request.getParameter("attachedImageID_" + i), 0);

                image = "imageProductDetail_" + i;
                String imageAttachedImage;
                filePart = request.getPart(image);
                if (filePart != null && filePart.getSize() > 0) {
                    imageAttachedImage = fileUpload.uploadFileAndReturnFileName(request, response, image, UPLOAD_DIRECTORY);
                } else {
                    imageAttachedImage = request.getParameter("oldImageAttached_" + i);
                }

                AttachedImage editAttachedImage = new AttachedImage();
                editAttachedImage.setId(attachedImageId);
                editAttachedImage.setProductdetail_id(productDetailId);
                editAttachedImage.setImage(imageAttachedImage);
                out.println("AttachedImage: " + editAttachedImage.getId() + "   " + editAttachedImage.getProductdetail_id() + "  " + editAttachedImage.getImage());
                AttachedImageDAO attachedImageDAO = new AttachedImageDAO();
                attachedImageDAO.updateAttachedImage(editAttachedImage);
            }
            count++;
        }

    }

    private void DeleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int deleteProductId = tryParseInt(request.getParameter("deleteProductId"), 0);
        ProductDAO productDAO = new ProductDAO();
        productDAO.deleteProduct(deleteProductId);

    }

    private void InsertProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("productName");
        int category_id = tryParseInt(request.getParameter("categoryId"), 0);
        int brand_id = tryParseInt(request.getParameter("brandId"), 0);
        int room_id = tryParseInt(request.getParameter("roomId"), 0);
        double price = tryParseDouble(request.getParameter("priceProduct"), 0);
        int quantity = tryParseInt(request.getParameter("quantityProduct"), 0);

        FileUploadHelper fileUpload = new FileUploadHelper();
        String image = "imageProduct";
        String fileName = fileUpload.uploadFileAndReturnFileName(request, response, image, UPLOAD_DIRECTORY);

        String description = request.getParameter("descriptionProduct");

        Product product = new Product(category_id, brand_id, room_id, name, description, fileName, price, quantity);
        ProductDAO productDAO = new ProductDAO();
        productDAO.insertProduct(product);
    }

    private void InsertProductDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = tryParseInt(request.getParameter("productId"), 0);
        int color_id = tryParseInt(request.getParameter("colorId"), 0);
        int quantityProductDetail = tryParseInt(request.getParameter("quantityProductDetail"), 0);

        FileUploadHelper fileUpload = new FileUploadHelper();
        String image = "imageProductDetail1";
        String fileName1 = fileUpload.uploadFileAndReturnFileName(request, response, image, UPLOAD_DIRECTORY);
        image = "imageProductDetail2";
        String fileName2 = fileUpload.uploadFileAndReturnFileName(request, response, image, UPLOAD_DIRECTORY);
        image = "imageProductDetail3";
        String fileName3 = fileUpload.uploadFileAndReturnFileName(request, response, image, UPLOAD_DIRECTORY);
        image = "imageProductDetail4";
        String fileName4 = fileUpload.uploadFileAndReturnFileName(request, response, image, UPLOAD_DIRECTORY);

        ProductDetail productDetail = new ProductDetail(productId, color_id, quantityProductDetail);
        ProductDetailDAO productDetailDAO = new ProductDetailDAO();
        productDetailDAO.addProductDetail(productDetail);

        productDetail = productDetailDAO.getProductDetailLastest();

        AttachedImage attachedImage1 = new AttachedImage(productDetail.getId(), fileName1);
        AttachedImage attachedImage2 = new AttachedImage(productDetail.getId(), fileName2);
        AttachedImage attachedImage3 = new AttachedImage(productDetail.getId(), fileName3);
        AttachedImage attachedImage4 = new AttachedImage(productDetail.getId(), fileName4);

        AttachedImageDAO attachedImageDAO = new AttachedImageDAO();
        attachedImageDAO.addAttachedImage(attachedImage1);
        attachedImageDAO.addAttachedImage(attachedImage2);
        attachedImageDAO.addAttachedImage(attachedImage3);
        attachedImageDAO.addAttachedImage(attachedImage4);
    }

    private int tryParseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private double tryParseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private ArrayList<Product> sortProduct(HttpServletRequest request, ArrayList<Product> productList) {
        String sortby = request.getParameter("sortby");
        ComparatorHelper comparatorHelper = new ComparatorHelper();
        productList = comparatorHelper.sortProductList(productList, sortby);
        HttpSession session = request.getSession();
        session.setAttribute("productList", productList);
        return productList;
    }

    private ArrayList<Product> filterProduct(HttpServletRequest request, ArrayList<Product> productList) {
        ProductDAO productDAO = new ProductDAO();
        String[] brandIDStr = request.getParameterValues("brand-filter");
        String[] roomSIDtr = request.getParameterValues("room-filter");
        String[] categoryIDStr = request.getParameterValues("category-filter");
        String[] priceIDStr = request.getParameterValues("price-filter");
        String[] statusIDStr = request.getParameterValues("status-filter");

        List<Integer> selectedBrandList = brandIDStr != null ? Arrays.stream(brandIDStr).map(Integer::parseInt).collect(Collectors.toList()) : null;
        List<Integer> selectedRoomList = roomSIDtr != null ? Arrays.stream(roomSIDtr).map(Integer::parseInt).collect(Collectors.toList()) : null;
        List<Integer> selectedCategoryList = categoryIDStr != null ? Arrays.stream(categoryIDStr).map(Integer::parseInt).collect(Collectors.toList()) : null;
        List<String> selectedPriceList = priceIDStr != null ? Arrays.asList(priceIDStr) : null;
        List<String> selectedStatusList = statusIDStr != null ? Arrays.asList(statusIDStr) : null;

        // Đặt các thuộc tính này vào request scope
        request.setAttribute("selectedBrandList", selectedBrandList);
        request.setAttribute("selectedRoomList", selectedRoomList);
        request.setAttribute("selectedCategoryList", selectedCategoryList);
        request.setAttribute("selectedPriceList", selectedPriceList);
        request.setAttribute("selectedColorList", selectedStatusList);

        productList = productDAO.filterProductListMKT(brandIDStr, roomSIDtr, categoryIDStr, statusIDStr, priceIDStr);
        HttpSession session = request.getSession();
        session.setAttribute("productList", productList);
        return productList;
    }

    private String listProduct(HttpServletRequest request, ArrayList<Product> productList) {
        SaleOffDAO saleOffDAO = new SaleOffDAO();
        ArrayList<SaleOff> saleOffList = saleOffDAO.getSaleOffList();

        BrandDAO brandDao = new BrandDAO();
        ArrayList<Brand> brandList = brandDao.getBrandList();

        RoomDAO roomDAO = new RoomDAO();
        ArrayList<Room> roomList = roomDAO.getRoomList();
        request.setAttribute("roomList", roomList);

        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getCategoryList();

        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Color> colorList = colorDAO.getColorList();

        ProductDetailDAO pddao = new ProductDetailDAO();
        ArrayList<ProductDetail> productDetailList = pddao.getAllProductDetails();

        AttachedImageDAO attachedImageDAO = new AttachedImageDAO();
        ArrayList<AttachedImage> attachedImageList = attachedImageDAO.getAllAttachedImages();

        PaginationHelper paginationHelper = new PaginationHelper();
        ServletContext context = getServletContext();
        String itemsPerPage = "itemsPerProductListPage";
        productList = paginationHelper.PaginationList(request, productList, context, itemsPerPage);

        StringBuilder htmlResponse = new StringBuilder();
        if (productList.isEmpty()) {
            htmlResponse.append("");
        } else {

        }
        for (Product product : productList) {
            htmlResponse.append("<tr>");
            htmlResponse.append("<td>");
            htmlResponse.append("<div>");
            htmlResponse.append("<p class=\"text-sm\">").append(product.getId()).append("</p>");
            htmlResponse.append("</div>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<div class=\"image\">");
            htmlResponse.append("<img src=\"image/product/").append(product.getImage()).append("\" alt=\"").append(product.getName()).append("\" />");
            htmlResponse.append("</div>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<div class=\"text-sm\">");
            htmlResponse.append("<p class=\"text-sm\">").append(product.getName()).append("</p>");
            htmlResponse.append("</div>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<p class=\"text-sm\">");
            for (Category category : categoryList) {
                if (product.getCategory_id() == category.getId()) {
                    htmlResponse.append(category.getCategory());
                    break;
                }
            }
            htmlResponse.append("</p>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<p class=\"text-sm\">");
            for (Brand brand : brandList) {
                if (product.getBrand_id() == brand.getId()) {
                    htmlResponse.append(brand.getBrandname());
                    break;
                }
            }
            htmlResponse.append("</p>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<p class=\"text-sm\">");
            for (Room room : roomList) {
                if (product.getRoom_id() == room.getId()) {
                    htmlResponse.append(room.getRoomname());
                    break;
                }
            }
            htmlResponse.append("</p>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<p class=\"text-sm\">");
            double discountedPrice = product.getPrice();
            for (SaleOff saleOff : saleOffList) {
                if (product.getId() == saleOff.getProduct_id()) {
                    discountedPrice = product.getPrice() - (saleOff.getSaleoffvalue() * product.getPrice() / 100);
                    htmlResponse.append("<span class=\"text-danger\">").append(discountedPrice).append(" VND</span>");
                    break;
                }
            }
            htmlResponse.append("<del>").append(product.getPrice()).append(" VND</del>");
            htmlResponse.append("</p>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<p class=\"text-sm text-danger\">");
            for (SaleOff saleOff : saleOffList) {
                if (product.getId() == saleOff.getProduct_id()) {
                    htmlResponse.append("<span class=\"text-sm\">-</span>");
                    htmlResponse.append("<span class=\"text-sm\">").append(saleOff.getSaleoffvalue()).append("</span>");
                    htmlResponse.append("<span class=\"text-sm\">%</span>");
                    break;
                }
            }
            htmlResponse.append("</p>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<p class=\"text-sm\">").append(product.getQuantity()).append("</p>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td>");
            htmlResponse.append("<p class=\"text-sm\">").append(product.getStatus()).append("</p>");
            htmlResponse.append("</td>");
            htmlResponse.append("<td class=\"actions\">");
            htmlResponse.append("<p>");
            htmlResponse.append("<a href=\"#\" title=\"View\" data-toggle=\"modal\" data-target=\"#viewProductModal_").append(product.getId()).append("\"><i class=\"fas fa-eye\"></i></a>");
            htmlResponse.append("<a href=\"#\" title=\"Edit\" data-toggle=\"modal\" data-target=\"#editProductModal_").append(product.getId()).append("\"><i class=\"fas fa-edit\"></i></a>");
            htmlResponse.append("<a href=\"#\" title=\"Delete\" data-toggle=\"modal\" data-target=\"#deleteProductModal_").append(product.getId()).append("\"><i class=\"fas fa-trash-alt\"></i></a>");
            htmlResponse.append("</p>");
            htmlResponse.append("</td>");
            htmlResponse.append("</tr>");
            htmlResponse.append("<div class=\"modal fade\" id=\"viewProductModal_").append(product.getId()).append("\" tabindex=\"-1\" aria-labelledby=\"viewProductModalLabel_").append(product.getId()).append("\" aria-hidden=\"true\">");
            htmlResponse.append("<div class=\"modal-dialog modal-fullscreen\">");
            htmlResponse.append("<div class=\"modal-content\">");
            htmlResponse.append("<div class=\"modal-header\">");
            htmlResponse.append("<h5 class=\"modal-title\" id=\"viewProductModalLabel\">Product Details</h5>");
            htmlResponse.append("<button type=\"button\" class=\"close custom-close-btn btn btn-danger\" data-dismiss=\"modal\" aria-label=\"Close\">");
            htmlResponse.append("<span aria-hidden=\"true\" style=\"width: 40px; height: 20px\">X</span>");
            htmlResponse.append("</button>");
            htmlResponse.append("</div>");
            htmlResponse.append("<div class=\"modal-body\" id=\"viewModal-container\">");
            htmlResponse.append("</div>"); // Close modal-body
            htmlResponse.append("</div>"); // Close modal-content
            htmlResponse.append("</div>"); // Close modal-dialog
            htmlResponse.append("</div>"); // Close modal fade

        }

        return htmlResponse.toString();
    }

    private String pagePagination(HttpServletRequest request, ArrayList<Product> productList) {
        ServletContext context = request.getServletContext();
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        String itemsPerPage = "itemsPerProductListPage";
        int pageSize = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, itemsPerPage);

        PaginationHelper paginationHelper = new PaginationHelper(productList, pageSize);
        int numberOfPage = paginationHelper.getTotalPages();
        StringBuilder pagePaginationHtml = new StringBuilder();

        for (int i = 1; i <= numberOfPage; i++) {
            pagePaginationHtml.append("<input type=\"radio\" name=\"page\" id=\"page")
                    .append(i)
                    .append("\" value=\"")
                    .append(i)
                    .append("\" style=\"display: none;\">")
                    .append("<label for=\"page")
                    .append(i)
                    .append("\" style=\"width: 25px; border: groove;\" class=\"page-node\" aria-label=\"Trang ")
                    .append(i)
                    .append("\" onclick=\"submitFormWithPage(")
                    .append(i)
                    .append(")\">")
                    .append(i)
                    .append("</label>");
        }
        pagePaginationHtml.append("<span class=\"page-node\">&hellip;</span>");

        return pagePaginationHtml.toString();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BrandDAO brandDao = new BrandDAO();
        ArrayList<Brand> brandList = brandDao.getBrandList();
        request.setAttribute("brandList", brandList);

        RoomDAO roomDAO = new RoomDAO();
        ArrayList<Room> roomList = roomDAO.getRoomList();
        request.setAttribute("roomList", roomList);

        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getCategoryList();
        request.setAttribute("categoryList", categoryList);

        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Color> colorList = colorDAO.getColorList();
        request.setAttribute("colorList", colorList);

        ProductDetailDAO pddao = new ProductDetailDAO();
        ArrayList<ProductDetail> productDetailList = pddao.getAllProductDetails();
        request.setAttribute("productDetailList", productDetailList);

        AttachedImageDAO attachedImageDAO = new AttachedImageDAO();
        ArrayList<AttachedImage> attachedImageList = attachedImageDAO.getAllAttachedImages();
        request.setAttribute("attachedImageList", attachedImageList);

        SaleOffDAO saleOffDAO = new SaleOffDAO();
        ArrayList<SaleOff> saleOffList = saleOffDAO.getSaleOffList();
        request.setAttribute("saleOffList", saleOffList);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        ArrayList<Product> productList = productDAO.getProductList();

        HttpSession session = request.getSession();
        session.setAttribute("productList", productList);

        String htmlResponse = listProduct(request, productList);
        request.setAttribute("htmlResponse", htmlResponse);

        processRequest(request, response);
        PrintWriter out = response.getWriter();
        out.print(htmlResponse);
        request.getRequestDispatcher("Views/ProductListMKT.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        ProductDAO productDAO = new ProductDAO();
        HttpSession session = request.getSession();
        ArrayList<Product> productList = productDAO.getProductList();

        if ("sort".equals(action)) {
            productList = (ArrayList<Product>) session.getAttribute("productList");

            productList = sortProduct(request, productList);
            String htmlResponse = listProduct(request, productList);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(htmlResponse);

        } else if ("filter".equals(action)) {
            productList = filterProduct(request, productList);

            String htmlResponse = listProduct(request, productList);
            String pagePagination = pagePagination(request, productList);

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(htmlResponse + "|" + pagePagination);

        } else if ("pagination".equals(action)) {
            productList = (ArrayList<Product>) session.getAttribute("productList");

            String htmlResponse = listProduct(request, productList);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(htmlResponse);
        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
