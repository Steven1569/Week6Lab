package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Steven
 */
public class ShoppingListServlet extends HttpServlet {
    @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String username = (String) session.getAttribute("username");
        
        if (action != null && action.equals("logout")) {
            session.invalidate();
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
        
        if (username != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        ArrayList<String> items;
       
        if (session.getAttribute("items") == null) {
            items = new ArrayList<String>();
        } else {
            items = (ArrayList<String>) session.getAttribute("items");
        }
        
        if (action.equals("register")) {
            session.setAttribute("username", username);
            session.setAttribute("items", items);
        } else if (action.equals("add")) {
            String item = request.getParameter("item");
            items.add(item);
            session.setAttribute("items", items);
        } else if (action.equals("delete")) {
            String item = request.getParameter("item");
            items.remove(item);
            session.setAttribute("items", items);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
        
    }

}
