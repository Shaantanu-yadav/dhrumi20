package com.gulbrandsen.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gulbrandsen.Productmaster;
import com.gulbrandsen.cloudsql.CloudSQL;

@WebServlet("/UpdateData")
public class UpdateData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateData() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productgroup = request.getParameter("productGroup");
		String productcode = request.getParameter("productCode");
		String productname = request.getParameter("productName");
		String sapproductcode = request.getParameter("sap");
		String status = request.getParameter("status");
		if (status != null && !status.isEmpty() && status.equalsIgnoreCase("on")) {
			status = "Active";
		} else {
			status = "Inactive";
		}
		int id = Integer.parseInt(request.getParameter("id"));
          Connection conn=null;
		try {
			conn = CloudSQL.getPrdConnection( request );
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(
					"update fms.product_master set product_group=?, product_abbr=?, product_name=?, Sap=? , status=?, update_date_time=? Where product_id=?");
			ps.setString(1, productgroup);
			ps.setString(2, productcode);
			ps.setString(3, productname);
			ps.setString(4, sapproductcode);
			ps.setString(5, status);
			ps.setDate(6, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			ps.setInt(7, id);
			ps.executeUpdate();

			int x = ps.executeUpdate();
			if (x > 0) {

				/*
				 * RequestDispatcher rd = request.getRequestDispatcher("productmaster.jsp");
				 * rd.forward(request, response);
				 */
				response.sendRedirect("productmaster.jsp");
			} else {
				PrintWriter out = response.getWriter();
				out.print("Not Done");
			}
			ps.close();
		}

		catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.print(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

}