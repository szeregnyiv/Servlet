package hu.neuron.simpleform;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String password = request.getParameter("password");

		ServletContext servletContext = request.getServletContext();
		ArrayList<RegisterForm> list = (ArrayList<RegisterForm>) servletContext.getAttribute("list");
		RegisterForm registerForm = new RegisterForm(name, password);

		if (name == null || name.equals("")) {

			request.setAttribute("error", "Nem adott meg felhasználó nevet!");
			request.getRequestDispatcher("/register.jsp").forward(request, response);

		} else if (password == null || password.equals("")) {

			request.setAttribute("error", "Nem adott meg jelszót!");
			request.getRequestDispatcher("/register.jsp").forward(request, response);

		} else if (password.length() < 6) {
			request.setAttribute("error", "A jelszó minimum 6 karakter!");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} else if (name.length() < 6) {
			request.setAttribute("error", "A felhasználónév minimum 6 karakter!");
			request.getSession().setAttribute("name", name);
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} else if (list != null) {
			System.err.println("itt1");
			boolean itis = false;
			for (RegisterForm registerForm2 : list) {
				System.err.println("itt2");
				if (registerForm2.getName().equals(name)) {

					request.setAttribute("error", "Foglalt felhasználónév!");
					request.getRequestDispatcher("/register.jsp").forward(request, response);
					itis = true;
				}
			}
			if (itis == false) {
				list.add(registerForm);
				servletContext.setAttribute("list", list);
				request.setAttribute("error", "Sikeres regisztráció!");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		} else {

			list = new ArrayList();
			list.add(registerForm);
			servletContext.setAttribute("list", list);
			request.setAttribute("error", "Sikeres regisztráció!");
			request.getRequestDispatcher("/register.jsp").forward(request, response);

		}

	}
}
