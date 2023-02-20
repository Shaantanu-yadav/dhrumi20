package com.gulbrandsen.login;

import java.io.IOException; 
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.gulbrandsen.login.*;


@WebServlet("/GoogleLogin")
public class GoogleLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = UserServiceFactory.getUserService();
		
		if (!userService.isUserLoggedIn() || userService.getCurrentUser() == null) {
			response.sendRedirect(userService.createLoginURL("/GoogleLogin","gulbrandsen.com"));
		} else {
				
			String email = userService.getCurrentUser().getEmail();
			Connection conn = null;
			User loginUser = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fms", "root", "1234");
				loginUser = User.getByEmail(conn, email);
				if (loginUser == null) {
					System.out.println("loginUser == null");
				} else {
					System.out.println("loginUser.getEmail(): " + loginUser.getEmail());
					System.out.println("loginUser.getLoginName(): " + loginUser.getLoginName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			request.getSession().setAttribute("LOGIN_USER", loginUser.getLoginName());
			// response.sendRedirect( "/" );

			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/header.jsp");
			rd.include(request, response);
		}

	}

}
