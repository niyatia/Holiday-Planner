package holiday.servlet;

import holiday.dal.CityDao;
import holiday.model.City;
import holiday.model.Enumerations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DestinationSearch")
public class DestinationSearch extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("regions", Enumerations.Region.values());
		req.setAttribute("interests", Enumerations.Interest.values());
		req.setAttribute("companions", Enumerations.Companion.values());
		req.setAttribute("selectedRegion", "NoRegion");
		req.getRequestDispatcher("/DestinationSearch.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String region = req.getParameter("region");
		String interest = req.getParameter("interest");
		String companion = req.getParameter("companion");
		
		CityDao cityDao = CityDao.getInstance();
		List<City> recommendedCities = new ArrayList<City>();
		try {
			recommendedCities = cityDao.getCityList(region, interest, companion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("cities", recommendedCities);
		req.setAttribute("selectedRegion", region);
		req.setAttribute("regions", Enumerations.Region.values());
		req.setAttribute("interests", Enumerations.Interest.values());
		req.setAttribute("companions", Enumerations.Companion.values());
		//City city = recommendedCities.get(0);
//		PrintWriter out = resp.getWriter();
//		for(City city : recommendedCities)
//		{		
//		out.println(city.toString());
//		}
		req.getRequestDispatcher("/DestinationSearch.jsp").forward(req, resp);
	}	
}
