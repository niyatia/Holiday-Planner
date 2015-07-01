package holiday.servlet;

import holiday.dal.AdminDao;
import holiday.dal.PersonDao;
import holiday.dal.UserDao;
import holiday.model.Admin;
import holiday.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomePage
 */
@WebServlet("/HomePage")
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("failure", "");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username == null || username.trim().equals("")) {
			request.setAttribute("failure", "Invalid Username");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}		
	
		PersonDao personDao = PersonDao.getInstance();
		
		try {
			// Authenticate user
			if(!personDao.isAuthenticated(username, password)) {
				
				request.setAttribute("failure", "Username, password does not exist");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			
			User user = null;
			Admin admin = null;
			
			HttpSession session = request.getSession();
			UserDao userDao = UserDao.getInstance();
			user = userDao.getUserByUserName(username);
			if(user == null) {
				admin = AdminDao.getInstance().getAdminByUserName(username);
				session.setAttribute("admin", admin);
				session.setAttribute("user", user);
				request.getRequestDispatcher("/AdminHome.jsp").forward(request, response);
			}
			session.setAttribute("user", user);
			session.setAttribute("admin", admin);
			request.getRequestDispatcher("/UserHome.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
