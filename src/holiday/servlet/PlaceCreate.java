package holiday.servlet;

import holiday.dal.CityDao;
import holiday.dal.PlaceDao;
import holiday.model.Admin;
import holiday.model.City;
import holiday.model.Place;

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

@WebServlet("/PlaceCreate")
public class PlaceCreate extends HttpServlet {
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
		req.getRequestDispatcher("/PlaceCreate.jsp").forward(req, resp);
		
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
		String place = req.getParameter("place");
		String address = req.getParameter("address");
		String zipCode = req.getParameter("zipCode");
		String description = req.getParameter("description");
		String photo = req.getParameter("photo");
		String cityName = req.getParameter("city");
		CityDao cityDao = CityDao.getInstance();
		City city = null;
		try {
			city = cityDao.getCityByCityName(cityName);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (city == null){
			messages.put("success", "Invalid CityName");
			req.getRequestDispatcher("/PlaceCreate.jsp").forward(req, resp);
		}
		PlaceDao placeDao = PlaceDao.getInstance();
	
		if (place == null || place.trim().isEmpty()) {
            messages.put("success", "Invalid PlaceName");
        } else {
        		Place newPlace = new Place(place, address, zipCode, description, photo, city, admin);
        		try {
        			placeDao.create(newPlace);
        		} catch (SQLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace(); 
        			messages.put("success", "Exception occured");
        			req.getRequestDispatcher("/PlaceCreate.jsp").forward(req, resp);
        		}	
        		messages.put("success", place + " added successfully");
        	}
        			
		
		req.getRequestDispatcher("/PlaceCreate.jsp").forward(req, resp);
	}
}


