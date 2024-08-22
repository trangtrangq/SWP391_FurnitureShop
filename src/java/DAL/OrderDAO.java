/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Order;
import Models.OrderSaleManager;
import Models.User;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 *
 * @author HELLO
 */
public class OrderDAO extends DBContext {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(OrderDAO.class.getName());

    public Order getOrder(int order_id) {
        Order od = new Order();
        String sql = "SELECT * FROM furniture.order WHERE id = ?"; // Đổi "order" thành "orders" nếu bảng có tên là "orders".

        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, order_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    od.setId(rs.getInt("id"));
                    od.setCustomer_id(rs.getInt("customer_id"));
                    od.setSale_id(rs.getInt("sale_id"));
                    od.setAddress_id(rs.getInt("address_id"));
                    od.setTotalcost(rs.getDouble("totalcost"));

                    // Chuyển đổi từ Timestamp sang LocalDateTime
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        od.setOrderDate(timestamp.toLocalDateTime());
                    }

                    od.setStatus(rs.getString("status"));
                    return od;
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order.", ex);
        }
        return null;
    }

    public ArrayList<Order> getOrderList() {
        ArrayList<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM furniture.order"; // Sửa lại "order" thành "orders"

        try (PreparedStatement pstmt = connect.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Order od = new Order();
                od.setId(rs.getInt("id"));
                od.setCustomer_id(rs.getInt("customer_id"));
                od.setSale_id(rs.getInt("sale_id"));
                od.setAddress_id(rs.getInt("address_id"));
                od.setTotalcost(rs.getDouble("totalcost"));

                // Chuyển đổi từ Timestamp sang LocalDateTime
                java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                if (timestamp != null) {
                    od.setOrderDate(timestamp.toLocalDateTime());
                }

                od.setStatus(rs.getString("status"));

                orderList.add(od);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list", ex);
        }
        return orderList;
    }

    public ArrayList<Order> myOrder(int customer_id) {
        ArrayList<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM furniture.order WHERE customer_id = ? ORDER BY id DESC";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, customer_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order od = new Order();
                    od.setId(rs.getInt("id"));
                    od.setCustomer_id(rs.getInt("customer_id"));
                    od.setSale_id(rs.getInt("sale_id"));
                    od.setAddress_id(rs.getInt("address_id"));
                    od.setTotalcost(rs.getDouble("totalcost"));
                    // Chuyển đổi từ Timestamp sang LocalDateTime
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        od.setOrderDate(timestamp.toLocalDateTime());
                    }
                    od.setStatus(rs.getString("status"));
                    orderList.add(od);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list", ex);
        }
        return orderList;
    }

    public Order getMyOrder(int order_id) {
        Order od = new Order();
        String sql = "SELECT * FROM furniture.order WHERE id = ? "; // Đổi "order" thành "orders" nếu bảng có tên là "orders".
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, order_id); // Sử dụng setInt thay vì setString cho giá trị int.
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    od.setId(rs.getInt("id"));
                    od.setCustomer_id(rs.getInt("customer_id"));
                    od.setSale_id(rs.getInt("sale_id"));
                    od.setAddress_id(rs.getInt("address_id"));
                    od.setPaymentMethod_id(rs.getInt("paymentmethod_id"));
                    od.setTotalcost(rs.getDouble("totalcost"));
                    // Chuyển đổi từ Timestamp sang LocalDateTime
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        od.setOrderDate(timestamp.toLocalDateTime());
                    }
                    od.setStatus(rs.getString("status"));
                    return od;
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order.", ex);
        }
        return null;
    }

    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE `furniture`.`order`\n"
                + "SET\n"
                + "`status` = ?\n"
                + "WHERE `id` = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving order.", e);
        }
    }

    public ArrayList<Order> OrderListOfSale(int sale_id) {
        ArrayList<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM furniture.order WHERE sale_id = ? ORDER BY id DESC";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, sale_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order od = new Order();
                    od.setId(rs.getInt("id"));
                    od.setCustomer_id(rs.getInt("customer_id"));
                    od.setSale_id(rs.getInt("sale_id"));
                    od.setAddress_id(rs.getInt("address_id"));
                    od.setTotalcost(rs.getDouble("totalcost"));
                    // Chuyển đổi từ Timestamp sang LocalDateTime
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        od.setOrderDate(timestamp.toLocalDateTime());
                    }
                    od.setStatus(rs.getString("status"));
                    orderList.add(od);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list", ex);
        }
        return orderList;
    }

    public ArrayList<Order> OrderListOfShipper(int id) {
        ArrayList<Order> orderList = new ArrayList<>();
        // Câu lệnh SQL đã sửa để bao gồm điều kiện cho trạng thái 'Deliveryfailed' và 'Delivered'
        String sql = "SELECT * FROM `order` WHERE shipper_id = ? AND ("
                + "status IN ('Confirmed', 'Delivering') OR "
                + "(status = 'Deliveryfailed' AND orderdate >= NOW() - INTERVAL 7 DAY) OR "
                + "(status = 'Delivered' AND orderdate >= NOW() - INTERVAL 7 DAY))";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order od = new Order();
                    od.setId(rs.getInt("id"));
                    od.setCustomer_id(rs.getInt("customer_id"));
                    od.setSale_id(rs.getInt("sale_id"));
                    od.setAddress_id(rs.getInt("address_id"));
                    od.setTotalcost(rs.getDouble("totalcost"));
                    // Chuyển đổi từ Timestamp sang LocalDateTime
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        od.setOrderDate(timestamp.toLocalDateTime());
                    }
                    od.setStatus(rs.getString("status"));
                    orderList.add(od);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list", ex);
        }
        return orderList;
    }

    private String parseStatus(String[] statusStrs) {
        if (statusStrs == null || statusStrs.length == 0) {
            return "";
        }
        List<String> conditions = new ArrayList<>();
        for (String statusStr : statusStrs) {
            switch (statusStr) {
                case "Order":
                    conditions.add("status = 'Order'");
                    break;
                case "Done":
                    conditions.add("status = 'Done'");
                    break;
                case "Confirmed":
                    conditions.add("status = 'Confirmed'");
                    break;
                case "Cancelled":
                    conditions.add("status = 'Cancelled'"); // Sửa từ "Cancled" thành "Cancelled"
                    break;
                case "Delivering":
                    conditions.add("status = 'Delivering'");
                    break;
                case "Delivered":
                    conditions.add("status = 'Delivered'");
                    break;
                case "Deliveryfailed":
                    conditions.add("status = 'Deliveryfailed'"); // Sửa từ "Deliveyfailed" thành "Deliveryfailed"
                    break;
            }
        }
        return String.join(" OR ", conditions);
    }

    public ArrayList<Order> filterOrderListByManagerSale(String fromDateStr, String toDateStr, String[] status) {
        ArrayList<Order> orderList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT 
                id, customer_id, sale_id, address_id, totalcost, orderdate, status
            FROM 
                `Order`
            WHERE 1 = 1
        """);

        List<Object> parameters = new ArrayList<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (fromDateStr != null && !fromDateStr.isEmpty()) {
            try {
                LocalDate fromDate = LocalDate.parse(fromDateStr, dateFormatter);
                LocalDateTime fromDateTime = fromDate.atStartOfDay(); // Set to start of the day
                sql.append(" AND orderdate >= ?");
                parameters.add(Timestamp.valueOf(fromDateTime));
                LOGGER.info("From date: " + fromDateTime);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Invalid fromDate format.", e);
            }
        }

        if (toDateStr != null && !toDateStr.isEmpty()) {
            try {
                LocalDate toDate = LocalDate.parse(toDateStr, dateFormatter);
                LocalDateTime toDateTime = toDate.atTime(23, 59, 59); // Set to end of the day
                sql.append(" AND orderdate <= ?");
                parameters.add(Timestamp.valueOf(toDateTime));
                LOGGER.info("To date: " + toDateTime);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Invalid toDate format.", e);
            }
        }
        String statusCondition = parseStatus(status);
        if (!statusCondition.isEmpty()) {
            sql.append(" AND (").append(statusCondition).append(")");
        }

        LOGGER.info("SQL Query: " + sql.toString());
        LOGGER.info("Parameters: " + parameters);

        try (PreparedStatement pstmt = connect.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomer_id(rs.getInt("customer_id"));
                    order.setSale_id(rs.getInt("sale_id"));
                    order.setAddress_id(rs.getInt("address_id"));
                    order.setTotalcost(rs.getDouble("totalcost"));
                    order.setOrderDate(rs.getTimestamp("orderdate").toLocalDateTime());
                    order.setStatus(rs.getString("status"));

                    orderList.add(order);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list.", ex);
        }
        return orderList;
    }

    public ArrayList<Order> filterOrderList(String fromDateStr, String toDateStr, String[] status, int sale_id) {
        ArrayList<Order> orderList = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("id, customer_id, sale_id, address_id, totalcost, orderdate, status ")
                .append("FROM ")
                .append("`Order` ")
                .append("WHERE ")
                .append("sale_id = " + sale_id + " ");

        List<Object> parameters = new ArrayList<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (fromDateStr != null && !fromDateStr.isEmpty()) {
            try {
                LocalDate fromDate = LocalDate.parse(fromDateStr, dateFormatter);
                LocalDateTime fromDateTime = fromDate.atStartOfDay(); // Set to start of the day
                sql.append(" AND orderdate >= ?");
                parameters.add(Timestamp.valueOf(fromDateTime));
                LOGGER.info("From date: " + fromDateTime);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Invalid fromDate format.", e);
            }
        }

        if (toDateStr != null && !toDateStr.isEmpty()) {
            try {
                LocalDate toDate = LocalDate.parse(toDateStr, dateFormatter);
                LocalDateTime toDateTime = toDate.atTime(23, 59, 59); // Set to end of the day
                sql.append(" AND orderdate <= ?");
                parameters.add(Timestamp.valueOf(toDateTime));
                LOGGER.info("To date: " + toDateTime);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Invalid toDate format.", e);
            }
        }
        String statusCondition = parseStatus(status);
        if (!statusCondition.isEmpty()) {
            sql.append(" AND (").append(statusCondition).append(")");
        }

        LOGGER.info("SQL Query: " + sql.toString());
        LOGGER.info("Parameters: " + parameters);

        try (PreparedStatement pstmt = connect.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomer_id(rs.getInt("customer_id"));
                    order.setSale_id(rs.getInt("sale_id"));
                    order.setAddress_id(rs.getInt("address_id"));
                    order.setTotalcost(rs.getDouble("totalcost"));
                    order.setOrderDate(rs.getTimestamp("orderdate").toLocalDateTime());
                    order.setStatus(rs.getString("status"));

                    orderList.add(order);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list.", ex);
        }
        return orderList;
    }

    public ArrayList<Order> filterShippingOrderList(String fromDateStr, String toDateStr, String[] status, int shipper_id) {
        ArrayList<Order> orderList = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append("id, customer_id, sale_id, address_id, totalcost, orderdate, status ")
                .append("FROM `Order` ")
                .append("WHERE shipper_id = ? AND status IN ('Confirmed', 'Delivering', 'Delivered', 'Deliveryfailed')");

        List<Object> parameters = new ArrayList<>();
        parameters.add(shipper_id);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (fromDateStr != null && !fromDateStr.isEmpty()) {
            try {
                LocalDate fromDate = LocalDate.parse(fromDateStr, dateFormatter);
                LocalDateTime fromDateTime = fromDate.atStartOfDay(); // Set to start of the day
                sql.append(" AND orderdate >= ?");
                parameters.add(Timestamp.valueOf(fromDateTime));
                LOGGER.info("From date: " + fromDateTime);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Invalid fromDate format.", e);
            }
        }

        if (toDateStr != null && !toDateStr.isEmpty()) {
            try {
                LocalDate toDate = LocalDate.parse(toDateStr, dateFormatter);
                LocalDateTime toDateTime = toDate.atTime(23, 59, 59); // Set to end of the day
                sql.append(" AND orderdate <= ?");
                parameters.add(Timestamp.valueOf(toDateTime));
                LOGGER.info("To date: " + toDateTime);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Invalid toDate format.", e);
            }
        }

        String statusCondition = parseStatus(status);
        if (!statusCondition.isEmpty()) {
            sql.append(" AND (").append(statusCondition).append(")");
        }

        LOGGER.info("SQL Query: " + sql.toString());
        LOGGER.info("Parameters: " + parameters);

        try (PreparedStatement pstmt = connect.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomer_id(rs.getInt("customer_id"));
                    order.setSale_id(rs.getInt("sale_id"));
                    order.setAddress_id(rs.getInt("address_id"));
                    order.setTotalcost(rs.getDouble("totalcost"));
                    order.setOrderDate(rs.getTimestamp("orderdate").toLocalDateTime());
                    order.setStatus(rs.getString("status"));

                    orderList.add(order);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list.", ex);
        }
        return orderList;
    }

    public ArrayList<Order> searchOrderByName(String name, int sale_id) {
        ArrayList<Order> orderList = new ArrayList<>();
        String sql = "SELECT `Order`.*, User.fullname \n"
                + "                FROM `Order` \n"
                + "                JOIN User ON `Order`.customer_id = User.id\n"
                + "                WHERE User.fullname LIKE ? AND sale_id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            pstmt.setInt(2, sale_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order od = new Order();
                    od.setId(rs.getInt("id"));
                    od.setCustomer_id(rs.getInt("customer_id"));
                    od.setSale_id(rs.getInt("sale_id"));
                    od.setAddress_id(rs.getInt("address_id"));
                    od.setTotalcost(rs.getDouble("totalcost"));
                    // Chuyển đổi từ Timestamp sang LocalDateTime
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        od.setOrderDate(timestamp.toLocalDateTime());
                    }
                    od.setStatus(rs.getString("status"));
                    orderList.add(od);
                }
            } catch (Exception e) {
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list", ex);
        }
        return orderList;
    }

    public ArrayList<Order> searchShippingOrderByName(String name, int shipper_id) {
        ArrayList<Order> orderList = new ArrayList<>();
        String sql = "SELECT `Order`.*, User.fullname \n"
                + "                FROM `Order` \n"
                + "                JOIN User ON `Order`.customer_id = User.id\n"
                + "                WHERE User.fullname LIKE ? AND shipper_id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            pstmt.setInt(2, shipper_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order od = new Order();
                    od.setId(rs.getInt("id"));
                    od.setCustomer_id(rs.getInt("customer_id"));
                    od.setSale_id(rs.getInt("sale_id"));
                    od.setAddress_id(rs.getInt("address_id"));
                    od.setTotalcost(rs.getDouble("totalcost"));
                    // Chuyển đổi từ Timestamp sang LocalDateTime
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        od.setOrderDate(timestamp.toLocalDateTime());
                    }
                    od.setStatus(rs.getString("status"));
                    orderList.add(od);
                }
            } catch (Exception e) {
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving order list", ex);
        }
        return orderList;
    }

    public ArrayList<Order> searchOrderById(int search, int sale_id) {
        ArrayList<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM furniture.order "
                + "WHERE id = ? AND sale_id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, search);
            pstmt.setInt(2, sale_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(); // Khởi tạo đối tượng Order mới trong vòng lặp
                    order.setId(rs.getInt("id"));
                    order.setCustomer_id(rs.getInt("customer_id"));
                    order.setSale_id(rs.getInt("sale_id"));
                    order.setAddress_id(rs.getInt("address_id"));
                    order.setTotalcost(rs.getDouble("totalcost"));
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        order.setOrderDate(timestamp.toLocalDateTime());
                    }
                    order.setStatus(rs.getString("status"));
                    orderList.add(order);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing result set", e);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error preparing statement", ex);
        }
        return orderList;
    }

    public ArrayList<Order> searchShippingOrderById(int search, int shipper_id) {
        ArrayList<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM furniture.order "
                + "WHERE id = ? AND shipper_id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, search);
            pstmt.setInt(2, shipper_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(); // Khởi tạo đối tượng Order mới trong vòng lặp
                    order.setId(rs.getInt("id"));
                    order.setCustomer_id(rs.getInt("customer_id"));
                    order.setSale_id(rs.getInt("sale_id"));
                    order.setAddress_id(rs.getInt("address_id"));
                    order.setTotalcost(rs.getDouble("totalcost"));
                    java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                    if (timestamp != null) {
                        order.setOrderDate(timestamp.toLocalDateTime());
                    }
                    order.setStatus(rs.getString("status"));
                    orderList.add(order);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing result set", e);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error preparing statement", ex);
        }
        return orderList;
    }

    //lấy danh sách các đơn hàng theo trạng thái, theo sale id trên tổng đơn trong khoảng thời gian
    public List<Map<String, Object>> getOrderStats(java.sql.Date startDate, java.sql.Date endDate, String saleid, String status) {
        StringBuilder sqlBuilder = new StringBuilder("WITH RECURSIVE DateRange AS (\n"
                + "    SELECT ? AS Date -- Thay bằng startdate\n"
                + "    UNION ALL\n"
                + "    SELECT Date + INTERVAL 1 DAY\n"
                + "    FROM DateRange\n"
                + "    WHERE Date + INTERVAL 1 DAY <= ? -- Thay bằng enddate\n"
                + ")\n"
                + "SELECT\n"
                + "    d.Date,\n"
                + "    IFNULL(\n"
                + "        (COUNT(CASE WHEN o.status = ? THEN 1 END) / NULLIF(COUNT(o.id), 0)) * 100,0)\n"
                + "  AS rate \n"
                + "FROM\n"
                + "	DateRange d\n"
                + "left join\n"
                + "    `furniture`.`order` o on DATE(o.orderdate) = d.Date \n");

        if (saleid != null && !saleid.isEmpty()) {
            sqlBuilder.append(" and o.sale_id=?");
        }
        sqlBuilder.append("GROUP BY d.Date ORDER BY d.Date");

        List<Map<String, Object>> orderStats = new ArrayList<>();

        // Xây dựng câu lệnh SQL và danh sách tham số dựa trên các tham số đầu vào
        try (PreparedStatement statement = connect.prepareStatement(sqlBuilder.toString())) {
            // Đặt các tham số vào câu lệnh SQL
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            statement.setString(3, status);
            if (saleid != null && !saleid.isEmpty()) {
                statement.setString(4, saleid);
            }
            sqlBuilder.append("GROUP BY d.Date ORDER BY d.Date");

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("date", rs.getDate("Date"));
                    row.put("rate", rs.getDouble("rate"));
                    orderStats.add(row);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving post list", ex);
        }

        return orderStats;
    }

    //lấy danh sách doanh thu của sale theo ngày
    public List<Map<String, Object>> getRevenueStats(java.sql.Date startDate, java.sql.Date endDate, String saleid) {
        StringBuilder sqlBuilder = new StringBuilder("WITH RECURSIVE DateRange AS (\n"
                + "    SELECT ? AS Date -- Thay bằng startdate\n"
                + "    UNION ALL\n"
                + "    SELECT Date + INTERVAL 1 DAY\n"
                + "    FROM DateRange\n"
                + "    WHERE Date + INTERVAL 1 DAY <= ? -- Thay bằng enddate\n"
                + ")\n"
                + "SELECT\n"
                + "    d.Date,\n"
                + "SUM(CASE WHEN o.status = 'Done' THEN o.totalcost ELSE 0 END) AS revenue\n"
                + "FROM\n"
                + "	DateRange d\n"
                + "left join\n"
                + "    `furniture`.`order` o on DATE(o.orderdate) = d.Date \n");

        if (saleid != null && !saleid.isEmpty()) {
            sqlBuilder.append(" and o.sale_id=?");
        }
        sqlBuilder.append("GROUP BY d.Date ORDER BY d.Date");

        List<Map<String, Object>> revenueStats = new ArrayList<>();

        // Xây dựng câu lệnh SQL và danh sách tham số dựa trên các tham số đầu vào
        try (PreparedStatement statement = connect.prepareStatement(sqlBuilder.toString())) {
            // Đặt các tham số vào câu lệnh SQL
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            if (saleid != null && !saleid.isEmpty()) {
                statement.setString(3, saleid);
            }
            sqlBuilder.append("GROUP BY d.Date ORDER BY d.Date");

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("date", rs.getDate("Date"));
                    row.put("revenue", rs.getInt("revenue"));
                    revenueStats.add(row);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving post list", ex);
        }

        return revenueStats;
    }

    //đếm số lượng các đơn hàng theo từng trạng thái
    public Map<String, Integer> getOrderCountByStatus(java.sql.Date startDate, java.sql.Date endDate) {
        Map<String, Integer> orderCountByStatus = new HashMap<>();
        String query = "SELECT status, COUNT(*) AS order_count FROM `order` WHERE orderdate BETWEEN ? AND ? GROUP BY status";

        try (
                PreparedStatement statement = connect.prepareStatement(query)) {

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String status = resultSet.getString("status");
                switch (status) {
                    case "Done":
                        status = "Thành công";
                        break;
                    case "Canceled":
                        status = "Đã huỷ";
                        break;
                    case "Confirmed":
                        status = "Đã xác nhận";
                        break;
                    case "Wait":
                        status = "Chưa thanh toán";
                        break;
                    case "Delivering":
                        status = "Đang vận chuyển";
                        break;
                    case "Delivered":
                        status = "Đã vận chuyển";
                        break;
                    case "Deliveryfailed":
                        status = "Vận chuyển thất bại";
                        break;
                    case "Order":
                        status = "Đã đặt hàng";
                        break;
                }
                int count = resultSet.getInt("order_count");
                orderCountByStatus.put(status, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderCountByStatus;
    }

    //thống kê doanh thu theo productcategory
    public Map<String, Double> getRevenueByCategory(java.sql.Date startDate, java.sql.Date endDate) {
        Map<String, Double> revenueMap = new LinkedHashMap<>();
        double totalAllCategories = 0.0; // Tổng doanh thu của tất cả các danh mục
        String sql = "SELECT \n"
                + "    c.category,\n"
                + "    SUM(od.quantity * od.price) AS total_revenue\n"
                + "FROM \n"
                + "    `order` o\n"
                + "JOIN \n"
                + "    orderdetail od ON o.id = od.order_id\n"
                + "JOIN \n"
                + "    productdetail pd ON od.productdetail_id = pd.id\n"
                + "JOIN \n"
                + "    product p ON pd.product_id = p.id\n"
                + "JOIN \n"
                + "    category c ON p.category_id = c.id\n"
                + "WHERE \n"
                + "    o.status = 'Done'\n"
                + "    AND o.orderdate BETWEEN ? AND ? -- Thay bằng startdate và enddate\n"
                + "GROUP BY \n"
                + "    c.category\n"
                + "ORDER BY \n"
                + "    total_revenue DESC;";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                double totalRevenue = rs.getDouble("total_revenue");

                revenueMap.put(category, totalRevenue);

                // Tính toán tổng doanh thu của tất cả các danh mục
                totalAllCategories += totalRevenue;
            }

            // Đưa tổng doanh thu của tất cả các danh mục vào Map với key là "total_all_categories"
            revenueMap.put("Tổng", totalAllCategories);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenueMap;
    }

    //lấy số lượng đơn hàng thành công và tổng đơn theo từng ngày
    public List<Map<String, Object>> getSuccessAndAllOrderCounts(java.sql.Date startDate, java.sql.Date endDate) {
        List<Map<String, Object>> orderStats = new ArrayList<>();
        String query = "WITH RECURSIVE DateRange AS ("
                + "    SELECT DATE(?) AS Date"
                + "    UNION ALL"
                + "    SELECT Date + INTERVAL 1 DAY"
                + "    FROM DateRange"
                + "    WHERE Date + INTERVAL 1 DAY <= DATE(?)"
                + ") "
                + "SELECT "
                + "    d.Date, "
                + "    SUM(CASE WHEN o.status = 'Done' THEN 1 ELSE 0 END) AS successOrder, "
                + "    COUNT(o.id) as allOrder "
                + "FROM "
                + "    DateRange d "
                + "LEFT JOIN "
                + "    `furniture`.`order` o on DATE(o.orderdate) = d.Date "
                + "GROUP BY d.Date "
                + "ORDER BY d.Date;";

        try (PreparedStatement statement = connect.prepareStatement(query)) {

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("date", rs.getDate("Date"));
                    row.put("successOrder", rs.getInt("successOrder"));
                    row.put("allOrder", rs.getInt("allOrder"));
                    orderStats.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderStats;
    }

    public List<OrderSaleManager> getFilteredOrders(String saleId, String searchValue, String sortBy, String fromDate, String toDate, String[] statusFilters) {
        List<OrderSaleManager> orderList = new ArrayList<>();

        String sql = "SELECT u.fullname, o.id, o.totalcost, o.orderdate, o.status ,s.fullname AS salename,s.id AS saleid ,sh.fullname AS shipname,sh.id AS shipid\n"
                + "   FROM user AS u  JOIN `order` AS o ON u.id = o.customer_id LEFT JOIN user AS s ON s.id=o.sale_id LEFT JOIN user AS sh ON sh.id=o.shipper_id\n"
                + "WHERE 1=1 ";
        if (saleId != null && saleId.matches("\\d+")) {
            sql += "AND o.sale_id = 3 ";
        } else if (saleId != null && !saleId.isEmpty()) { // Nếu searchValue không phải là số
            sql += " AND o.sale_id > 0 AND o.sale_id <> 3";
        }
        if (searchValue != null && searchValue.matches("\\d+")) { // Kiểm tra nếu searchValue là số
            sql += " AND o.id = ?";
        } else if (searchValue != null && !searchValue.isEmpty()) { // Nếu searchValue không phải là số
            sql += " AND u.fullname LIKE ?";
        }

        // Thêm điều kiện lọc theo ngày
        if (fromDate != null && !fromDate.isEmpty()) {
            sql += " AND o.orderdate >= ?";
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql += " AND o.orderdate <= ?";
        }

        // Thêm điều kiện lọc theo trạng thái nếu mảng statusFilters có giá trị hợp lệ
        if (statusFilters != null && statusFilters.length > 0) {
            List<String> validStatusFilters = new ArrayList<>();
            for (String status : statusFilters) {
                if (status != null && !status.isEmpty()) {
                    validStatusFilters.add(status);
                }
            }
            if (!validStatusFilters.isEmpty()) {
                sql += " AND o.status IN (";
                for (int i = 0; i < validStatusFilters.size(); i++) {
                    sql += "?";
                    if (i < validStatusFilters.size() - 1) {
                        sql += ", ";
                    }
                }
                sql += ")";
            }
        }

        // Thêm điều kiện sắp xếp
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "dateAsc":
                    sql += " ORDER BY o.orderdate ASC";
                    break;
                case "dateDesc":
                    sql += " ORDER BY o.orderdate DESC";
                    break;
                case "nameAsc":
                    sql += " ORDER BY u.fullname ASC";
                    break;
                case "nameDesc":
                    sql += " ORDER BY u.fullname DESC";
                    break;
                case "totalCostAsc":
                    sql += " ORDER BY o.totalcost ASC";
                    break;
                case "totalCostDesc":
                    sql += " ORDER BY o.totalcost DESC";
                    break;
                case "statusAsc":
                    sql += " ORDER BY o.status ASC";
                    break;
                case "statusDesc":
                    sql += " ORDER BY o.status DESC";
                    break;
                default:
                    sql += " ORDER BY o.id ASC";
            }
        } else {
            sql += " ORDER BY o.id ASC"; // Điều kiện mặc định nếu sortBy rỗng
        }

        try {
            PreparedStatement ps = connect.prepareStatement(sql);
            int paramIndex = 1;

            if (searchValue != null && searchValue.matches("\\d+")) { // Kiểm tra nếu searchValue là số
                ps.setString(paramIndex++, searchValue);
            } else if (searchValue != null && !searchValue.isEmpty()) { // Nếu searchValue không phải là số
                ps.setString(paramIndex++, "%" + searchValue + "%");
            }

            // Thiết lập tham số cho lọc theo ngày
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(paramIndex++, java.sql.Date.valueOf(LocalDate.parse(fromDate)));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(paramIndex++, java.sql.Date.valueOf(LocalDate.parse(toDate)));
            }

            // Thiết lập tham số cho lọc theo trạng thái
            if (statusFilters != null && statusFilters.length > 0) {
                List<String> validStatusFilters = new ArrayList<>();
                for (String status : statusFilters) {
                    if (status != null && !status.isEmpty()) {
                        validStatusFilters.add(status);
                    }
                }
                for (String status : validStatusFilters) {
                    ps.setString(paramIndex++, status);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderSaleManager od = new OrderSaleManager();
                od.setId(rs.getInt("id"));
                od.setCustomer(rs.getString("fullname"));
                od.setTotalcost(rs.getDouble("totalcost"));
                java.sql.Timestamp timestamp = rs.getTimestamp("orderdate");
                if (timestamp != null) {
                    od.setOrderDate(timestamp.toLocalDateTime());
                }
                od.setSaleid(rs.getInt("saleid"));
                od.setStatus(rs.getString("status"));
                od.setSalename(rs.getString("salename"));
                od.setShipid(rs.getInt("shipid"));
                od.setShipname(rs.getString("shipname"));
                orderList.add(od);
            }
            ps.close();
            connect.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return orderList;
    }

    public int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO `order` (customer_id , address_id,paymentmethod_id,orderdate, totalcost,status) VALUES (?,?,? , NOW(), ?,?)";
        try (PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomer_id());
            stmt.setInt(2, order.getAddress_id());
            stmt.setInt(3, order.getPaymentMethod_id());
            stmt.setDouble(4, order.getTotalcost());
            stmt.setString(5, order.getStatus());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    public void updateOrderSale(int orderId, int sale_id) {
        String sql = "UPDATE `furniture`.`order` \n"
                + "SET `sale_id` = ?, \n"
                + "    `orderassign` = NOW() \n"
                + "WHERE `id` = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setInt(1, sale_id);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving order.", e);
        }
    }//tạo order

    public ArrayList<Order> getOrdersForToday() {
        String sql = "SELECT * FROM `Order` WHERE DATE(orderdate) = CURDATE()";
        ArrayList<Order> orderList = new ArrayList<>();
        try (
                PreparedStatement statement = connect.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomer_id(rs.getInt("customer_id"));
                order.setSale_id(rs.getInt("sale_id"));
                order.setAddress_id(rs.getInt("address_id"));
                order.setPaymentMethod_id(rs.getInt("paymentmethod_id"));
                order.setTotalcost(rs.getDouble("totalcost"));
                order.setOrderDate(rs.getTimestamp("orderdate").toLocalDateTime());
                order.setStatus(rs.getString("status"));
                order.setShipper_id(rs.getInt("shipper_id"));
                order.setNote(rs.getString("note"));
                orderList.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }

    public int distributeOrder(int inputShipperId) {
        // Lấy danh sách các shipper có role_id = 6 và không có status là "On leave"
        List<User> shippers = new UserDAO().getShipperList().stream()
                .filter(shipper -> !"On leave".equals(shipper.getStatus()))
                .collect(Collectors.toList());

        // Lấy danh sách các đơn hàng trong ngày hiện tại
        List<Order> todayOrders = getOrdersForToday();

        // Loại bỏ các shipper đang có đơn hàng với trạng thái "Delivering"
        List<User> availableShippers = new ArrayList<>();
        for (User shipper : shippers) {
            if (shipper.getId() == inputShipperId) {
                continue; // Bỏ qua shipper có id là inputShipperId
            }
            boolean hasDeliveringOrder = todayOrders.stream()
                    .anyMatch(order -> order.getShipper_id() == shipper.getId() && "Delivering".equals(order.getStatus()));
            if (!hasDeliveringOrder) {
                availableShippers.add(shipper);
            }
        }

        // Nếu không còn shipper nào không có đơn Delivering, chia đều đơn hàng cho tất cả shipper không có status "On leave"
        if (availableShippers.isEmpty()) {
            availableShippers = shippers.stream()
                    .filter(shipper -> shipper.getId() != inputShipperId)
                    .collect(Collectors.toList());
        }

        // Đếm số lượng đơn hàng hiện tại của mỗi shipper
        Map<Integer, Long> shipperOrderCounts = todayOrders.stream()
                .collect(Collectors.groupingBy(Order::getShipper_id, Collectors.counting()));

        // Tìm shipper có số lượng đơn hàng ít nhất
        User chosenShipper = availableShippers.stream()
                .min(Comparator.comparing(shipper -> shipperOrderCounts.getOrDefault(shipper.getId(), 0L)))
                .orElse(null);

        return (chosenShipper != null) ? chosenShipper.getId() : 0;
    }

    public void updateShipper(int orderId, int shipper_id) {
        String sql = "UPDATE `furniture`.`order`\n"
                + "SET\n"
                + "`shipper_id` = ?\n"
                + "WHERE `id` = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setInt(1, shipper_id);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving order.", e);
        }
    }

    public boolean updateOrder(Order order) {
        String sql = "UPDATE order SET customer_id = ?, sale_id = ?, address_id = ?, paymentMethod_id = ?, totalcost = ?, orderDate = ?, status = ? WHERE id = ?";
        try (
                PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, order.getCustomer_id());
            stmt.setInt(2, order.getSale_id());
            stmt.setInt(3, order.getAddress_id());
            stmt.setInt(4, order.getPaymentMethod_id());
            stmt.setDouble(5, order.getTotalcost());

            // Convert LocalDateTime to String for SQL
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String orderDateStr = order.getOrderDate().format(formatter);
            stmt.setString(6, orderDateStr);

            stmt.setString(7, order.getStatus());
            stmt.setInt(8, order.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> cancelOverdueOrders() throws SQLException {
        String selectSql = "SELECT id FROM `order` WHERE status = 'Wait' AND orderdate < NOW() - INTERVAL 2 MINUTE";
        PreparedStatement selectStmt = connect.prepareStatement(selectSql);
        ResultSet resultSet = selectStmt.executeQuery();

        List<Integer> updatedIds = new ArrayList<>();
        while (resultSet.next()) {
            updatedIds.add(resultSet.getInt("id"));
        }

        // Step 2: Update status of orders with the retrieved IDs
        if (!updatedIds.isEmpty()) {
            String updateSql = "UPDATE `order` SET status = 'Canceled' WHERE id = ?";
            PreparedStatement updateStmt = connect.prepareStatement(updateSql);

            for (Integer id : updatedIds) {
                updateStmt.setInt(1, id);
                updateStmt.addBatch();
            }

            updateStmt.executeBatch();
        }
        return updatedIds;
    }

    public List<Integer> updateOrdersSale() throws SQLException {
        String selectSql = "SELECT id FROM `order` WHERE status = 'Order' AND sale_id <> 3 AND orderassign < NOW() - INTERVAL 5 MINUTE;";
        PreparedStatement selectStmt = connect.prepareStatement(selectSql);
        ResultSet resultSet = selectStmt.executeQuery();

        List<Integer> updatedIds = new ArrayList<>();
        while (resultSet.next()) {
            updatedIds.add(resultSet.getInt("id"));
        }
        if (!updatedIds.isEmpty()) {
            String query = "UPDATE `order` SET sale_id=3 WHERE id=?";
            PreparedStatement updateStmt = connect.prepareStatement(query);
            for (Integer id : updatedIds) {
                updateStmt.setInt(1, id);
                updateStmt.addBatch();
            }

            updateStmt.executeBatch();
        }
        return updatedIds;
    }

    public List<Integer> update() throws SQLException {
        String selectSql = "SELECT id FROM `order` WHERE status = 'Order' AND orderassign < NOW() - INTERVAL 1 MINUTE;";
        PreparedStatement selectStmt = connect.prepareStatement(selectSql);
        ResultSet resultSet = selectStmt.executeQuery();

        List<Integer> updatedIds = new ArrayList<>();
        while (resultSet.next()) {
            updatedIds.add(resultSet.getInt("id"));
        }
        if (!updatedIds.isEmpty()) {
            String query = "UPDATE `order` SET sale_id=3 WHERE id=?";
            PreparedStatement updateStmt = connect.prepareStatement(query);
            for (Integer id : updatedIds) {
                updateStmt.setInt(1, id);
                updateStmt.addBatch();
            }

            updateStmt.executeBatch();
        }
        return updatedIds;
    }

    public boolean updateOrderNote(int order_id, String note) {
        String sql = "UPDATE `order` SET note = ? WHERE id = ?";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, note);
            stmt.setInt(2, order_id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating order note.", e);
            return false;
        }
    }

    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();
        List<OrderSaleManager> orders = orderDAO.getFilteredOrders(null, null, null, null, null, null);
        for (OrderSaleManager orderSaleManager : orders) {
            System.out.println(orderSaleManager.getId());
        }
    }
}
