package holiday.servlet;

import holiday.dal.UserDao;
import holiday.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserUpdate")
public class UserUpdate extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object mayBeUser = session.getAttribute("user");
		if(mayBeUser == null) {
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
		User user = (User) mayBeUser;		
		req.setAttribute("username", user.getUserName());
		req.getRequestDispatcher("/UserUpdate.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object mayBeUser = session.getAttribute("user");
		if(mayBeUser == null) {
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
		User user = (User) mayBeUser;
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		String userName = req.getParameter("username");
		String newFirstName = req.getParameter("firstname");
		String newLastName = req.getParameter("lastname");
		String newEmail = req.getParameter("email");
		String newPhone = req.getParameter("phone");
		
		UserDao userDao = UserDao.getInstance();
		
		try {
//			User user = userDao.getUserByUserName(userName);	
//			if(user == null) {
//				messages.put("success", "Username " + userName + " does not exists!");
//				req.getRequestDispatcher("/UserUpdate.jsp").forward(req, resp);
//			}
			user = userDao.updateUser(user, newFirstName, newLastName, newEmail, newPhone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		messages.put("success", userName + " updated");
		
		req.getRequestDispatcher("/UserUpdate.jsp").forward(req, resp);
	}
}
