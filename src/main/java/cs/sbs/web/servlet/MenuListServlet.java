package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuListServlet extends HttpServlet {

    private static List<MenuItem> menu = new ArrayList<>();

    static {
        menu.add(new MenuItem("Fried Rice", 8));
        menu.add(new MenuItem("Fried Noodles", 9));
        menu.add(new MenuItem("Burger", 10));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String searchName = req.getParameter("name");

        List<MenuItem> filteredMenu = menu;
        if (searchName != null && !searchName.trim().isEmpty()) {
            filteredMenu = menu.stream()
                    .filter(item -> item.getName().toLowerCase().contains(searchName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (filteredMenu.isEmpty()) {
            out.println("No menu items found");
            return;
        }

        out.println("Menu List:");
        out.println();
        int index = 1;
        for (MenuItem item : filteredMenu) {
            out.println(index + ". " + item.getName() + " - $" + item.getPrice());
            index++;
        }
    }
}
