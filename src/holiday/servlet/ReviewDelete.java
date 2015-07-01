package holiday.servlet;

import holiday.dal.ReviewDao;
import holiday.model.Review;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteReview
 */
@WebServlet("/ReviewDelete")
public class ReviewDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Provide a title and render the JSP.
		messages.put("title", "Delete Review");
		String reviewId = req.getParameter("reviewId");
		req.setAttribute("reviewId", reviewId);
		req.getRequestDispatcher("/ReviewDelete.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		int reviewId = Integer.parseInt(req.getParameter("reviewId"));

		ReviewDao reviewDao = ReviewDao.getInstance();

		try {
			Review review = reviewDao.delete(new Review(reviewId));
			// Update the message.
			if (review == null) {
				messages.put("title", "Successfully deleted " + reviewId);
				messages.put("disableSubmit", "true");
			} else {
				messages.put("title", "Failed to delete " + reviewId);
				messages.put("disableSubmit", "false");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		req.getRequestDispatcher("/ReviewDelete.jsp").forward(req, resp);
	}

}
