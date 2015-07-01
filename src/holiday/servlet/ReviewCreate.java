package holiday.servlet;

import holiday.dal.CityDao;
import holiday.dal.CompanionRatingDao;
import holiday.dal.InterestRatingDao;
import holiday.dal.ReviewDao;
import holiday.model.City;
import holiday.model.CompanionRating;
import holiday.model.Enumerations;
import holiday.model.Enumerations.Companion;
import holiday.model.Enumerations.Interest;
import holiday.model.InterestRating;
import holiday.model.Review;
import holiday.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ReviewCreate
 */
@WebServlet("/ReviewCreate")
public class ReviewCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Object mayBeUser = session.getAttribute("user");
		
		if(mayBeUser == null) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		request.setAttribute("interests", Enumerations.Interest.values());
		request.setAttribute("companions", Enumerations.Companion.values());
		request.setAttribute("success", "");
		request.getRequestDispatcher("/ReviewCreate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object mayBeUser = session.getAttribute("user");
		if(mayBeUser == null) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		User user = (User) mayBeUser;
		String cityName = request.getParameter("cityName");
		String review = request.getParameter("review");
		ReviewDao reviewDao = ReviewDao.getInstance();
		CityDao cityDao = CityDao.getInstance();
		
		
		Interest[] interests = Interest.values();
		Map<String, Integer> interestRatings = new HashMap<String, Integer>();
		for(Interest interest : interests) {
			String rating = request.getParameter(interest.toString());
			if(!rating.equals("Select"))
				interestRatings.put(interest.toString(), Integer.parseInt(rating));
		}
		
		Companion[] companions = Companion.values();
		Map<String, Integer> companionRatings = new HashMap<String, Integer>();
		for(Companion companion : companions) {
			String rating = request.getParameter(companion.toString());
			if(!rating.equals("Select"))
				companionRatings.put(companion.toString(), Integer.parseInt(rating));
		}
				
		City city = null;
		try {
			city = cityDao.getCityByCityName(cityName);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (city == null) {
			String success = "Please enter a valid city";
			request.setAttribute("success", success);
			request.setAttribute("interests", Enumerations.Interest.values());
			request.setAttribute("companions", Enumerations.Companion.values());
			request.getRequestDispatcher("/ReviewCreate.jsp").forward(request, response);
		}
		

		InterestRatingDao interestRatingDao = InterestRatingDao.getInstance();
		Iterator iterator = interestRatings.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) iterator.next();
			try {
				interestRatingDao.create(new InterestRating(pair.getValue(), Interest.valueOf(pair.getKey()), user, city));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		CompanionRatingDao companionRatingDao = CompanionRatingDao.getInstance();
		Iterator it = companionRatings.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
			try {
				companionRatingDao.create(new CompanionRating(pair.getValue(), Companion.valueOf(pair.getKey()), user, city));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		
		
		try {
			if(!review.trim().isEmpty()) {
				Review newReview = new Review(review, city, user);
				reviewDao.create(newReview);
			}											
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String success = "We appreciate for your ratings and review";
		request.setAttribute("success", success);
		request.setAttribute("interests", Enumerations.Interest.values());
		request.setAttribute("companions", Enumerations.Companion.values());			
		request.getRequestDispatcher("/ReviewCreate.jsp").forward(request, response);
	}

}
