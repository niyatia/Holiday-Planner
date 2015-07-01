package holiday.servlet;

import holiday.dal.UserDao;
import holiday.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserCreate")
public class UserCreate extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String userName = req.getParameter("username");
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		Date dob = null;
		UserDao userDao = UserDao.getInstance();
		try {
			dob = sdf.parse(req.getParameter("dob"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
        	User possibleUser = null;
			try {
				possibleUser = userDao.getUserByUserName(userName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (possibleUser != null) {
        		messages.put("success", "Username already exists!!");
        	} else {
        		User user = new User(userName, firstName, lastName, email, password, phone, dob);
        		try {
        			userDao.create(user);
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
