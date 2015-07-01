package holiday.servlet;

import holiday.dal.CityDao;
import holiday.model.Admin;
import holiday.model.City;
import holiday.model.Enumerations;

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

@WebServlet("/CityCreate")
public class CityCreate extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {	
		HttpSession session = req.getSession();
		Object mayBeAdmin = session.getAttribute("admin");
		if(mayBeAdmin == null) {
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
		req.setAttribute("regions", Enumerations.Region.values());
		req.setAttribute("selectedRegion", "NoRegion");
		req.getRequestDispatcher("/CityCreate.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object mayBeAdmin = session.getAttribute("admin");
		if(mayBeAdmin == null) {
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
		Admin admin = (Admin) mayBeAdmin;
		
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);		
		String city = req.getParameter("city");
		String description = req.getParameter("description");
		String photo = req.getParameter("photo");
		String region = req.getParameter("region");
		Enumerations.Region selectedRegion = Enumerations.Region.valueOf(region);
		CityDao cityDao = CityDao.getInstance();
	
		if (city == null || city.trim().isEmpty()) {
            messages.put("success", "Invalid CityName");
        } else {
        	City possibleCity = null;
			try {
				possibleCity = cityDao.getCityByCityName(city);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (possibleCity != null) {
        		messages.put("success", "Cityname already exists!!");
        	} else {
        		City newCity = new City(city, description, photo, selectedRegion, admin);
        		try {
        			cityDao.create(newCity);
        		} catch (SQLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();        			
        			messages.put("success", "Exception occured");
        			req.setAttribute("regions", Enumerations.Region.values());
        			req.setAttribute("selectedRegion", "NoRegion");
        			req.getRequestDispatcher("/CityCreate.jsp").forward(req, resp);
        		}	
        		messages.put("success", city + " added successfully");
        	}
        }			
		
		req.setAttribute("regions", Enumerations.Region.values());
		req.setAttribute("selectedRegion", "NoRegion");
		req.getRequestDispatcher("/CityCreate.jsp").forward(req, resp);
	}
}
