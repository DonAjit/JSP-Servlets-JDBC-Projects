package com.ToDoApp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ToDoController
 */
@WebServlet("/ToDoController")
public class ToDoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<Task> taskList;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Update
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		ToDoService service = new ToDoService();
		String name, priority, date;
		@SuppressWarnings("unchecked")
		List<Task> taskList = (List<Task>) session.getAttribute("taskList");
		if (taskList == null) {
			taskList = new ArrayList<>();
			request.getServletContext().setAttribute("taskList", taskList);
//			session.setAttribute("taskList", taskList);
		}
		name = request.getParameter("name");
		priority = request.getParameter("priority");
		date = request.getParameter("date");
		Task task = new Task(name, priority, date);
		if (service.isProper(task)) {
			// Write write.logic() here
			try {
				if (service.toDatabase(task)) {
					taskList = service.fromDatabase(); // get List
					request.getServletContext().setAttribute("taskList", taskList);
					//session.setAttribute("taskList", taskList);
					RequestDispatcher dispatcher = request.getRequestDispatcher("ToDoHomepage.jsp");
					dispatcher.forward(request, response);
				} else {
					taskList = service.fromDatabase(); // get List
					request.getServletContext().setAttribute("taskList", taskList);
					//session.setAttribute("taskList", taskList);
					RequestDispatcher dispatcher = request.getRequestDispatcher("ToDoHomepage.jsp");
					dispatcher.forward(request, response);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

}