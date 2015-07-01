package holiday.servlet;

import holiday.dal.PlaceDao;
import holiday.dal.ReviewDao;
import holiday.model.City;
import holiday.model.Place;
import holiday.model.Review;
import holiday.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CityPlaces
 */
@WebServlet("/CityPlaces")
public class CityPlaces extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected PlaceDao placeDao;
	
	@Override
	public void init() throws ServletException {
		placeDao = PlaceDao.getInstance();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
		// Retrieve and validate cityname.
        String cityname = req.getParameter("cityname");
        if (cityname == null || cityname.trim().isEmpty()) {
            messages.put("title", "Invalid cityname.");
        } else {
        	messages.put("title", "Places to visit in " + cityname);
        }
        
        // Retrieve Place, and store in the request.
        List<Place> places = new ArrayList<Place>();
        try {        	
        	places = placeDao.getPlaceList(cityname);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("places", places);
        req.getRequestDispatcher("/CityPlaces.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
