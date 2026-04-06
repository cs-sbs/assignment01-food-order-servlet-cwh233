package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderCreateServlet extends HttpServlet {

    private static List<Order> orders = new ArrayList<>();
    private static AtomicInteger nextId = new AtomicInteger(1001);

    public static List<Order> getOrders() {
        return orders;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String customer = req.getParameter("customer");
        String food = req.getParameter("food");
        String quantityStr = req.getParameter("quantity");

        // Check missing parameters
        if (customer == null || customer.trim().isEmpty()) {
            out.println("Error: customer name is missing");
            resp.setStatus(400);
            return;
        }
        if (food == null || food.trim().isEmpty()) {
            out.println("Error: food name is missing");
            resp.setStatus(400);
            return;
        }
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            out.println("Error: quantity is missing");
            resp.setStatus(400);
            return;
        }

        // Validate quantity is a number
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr.trim());
            if (quantity <= 0) {
                out.println("Error: quantity must be a positive number");
                resp.setStatus(400);
                return;
            }
        } catch (NumberFormatException e) {
            out.println("Error: quantity must be a valid number");
            resp.setStatus(400);
            return;
        }

        int orderId = nextId.getAndIncrement();
        Order order = new Order(orderId, customer, food, quantity);
        orders.add(order);

        out.println("Order Created: " + orderId);
    }
}

