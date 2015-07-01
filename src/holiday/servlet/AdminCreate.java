package holiday.servlet;

import holiday.dal.AdminDao;
import holiday.model.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminCreate")
public class AdminCreate extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		req.getRequestDispatcher("/AdminCreate.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		String userName = req.getParameter("username");
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		AdminDao adminDao = AdminDao.getInstance();
		
		if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
        	Admin possibleAdmin = null;
			try {
				possibleAdmin = adminDao.getAdminByUserName(userName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (possibleAdmin != null) {
        		messages.put("success", "Username already exists!!");
        	} else {
        		Admin user = new Admin(userName, firstName, lastName, email, password, null);
        		try {
        			adminDao.create(user);
        		} catch (SQLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();        			
        		}	
        		messages.put("success", userName + " added successfully");
        	}
        }			
		
		req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
	}
}
