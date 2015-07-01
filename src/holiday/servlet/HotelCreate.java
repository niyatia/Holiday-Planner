package holiday.servlet;

import holiday.dal.CityDao;
import holiday.dal.HotelDao;
import holiday.model.Admin;
import holiday.model.City;
import holiday.model.Hotel;

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

@WebServlet("/HotelCreate")
public class HotelCreate extends HttpServlet {
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
		req.getRequestDispatcher("/HotelCreate.jsp").forward(req, resp);
		
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
		String hotel = req.getParameter("hotel");
		String address = req.getParameter("address");
		String zipCode = req.getParameter("zipCode");
		String phone = req.getParameter("phone");
		String website = req.getParameter("website");
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
			messages.put("success", "Invalid City name");
			req.getRequestDispatcher("/HotelCreate.jsp").forward(req, resp);
		}
		HotelDao hotelDao = HotelDao.getInstance();
	
		if (hotel == null || hotel.trim().isEmpty()) {
            messages.put("success", "Invalid Hotel name");
        } else {
        		Hotel newHotel = new Hotel(hotel, address, zipCode, phone, website, city, admin);
        		try {
        			hotelDao.create(newHotel);
        		} catch (SQLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();  
        			messages.put("success", "Exception occured");
        			req.getRequestDispatcher("/HotelCreate.jsp").forward(req, resp);
        		}	
        		messages.put("success", hotel + " added successfully");
        	}
        			
		
		req.getRequestDispatcher("/HotelCreate.jsp").forward(req, resp);
	}
}


