package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            out.println("Error: order ID is missing");
            resp.setStatus(400);
            return;
        }

        // Extract order ID from path (remove leading slash)
        String idStr = pathInfo.substring(1);
        int orderId;
        try {
            orderId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            out.println("Error: invalid order ID");
            resp.setStatus(400);
            return;
        }

        // Find order
        Order foundOrder = null;
        for (Order order : OrderCreateServlet.getOrders()) {
            if (order.getId() == orderId) {
                foundOrder = order;
                break;
            }
        }

        if (foundOrder == null) {
            out.println("Error: Order not found");
            resp.setStatus(404);
            return;
        }

        out.println("Order Detail");
        out.println();
        out.println("Order ID: " + foundOrder.getId());
        out.println("Customer: " + foundOrder.getCustomer());
        out.println("Food: " + foundOrder.getFood());
        out.println("Quantity: " + foundOrder.getQuantity());
    }
}
