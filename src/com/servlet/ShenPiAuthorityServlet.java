package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class ShenPiAuthorityServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String account = request.getParameter("account");
		System.out.println(account);
		 Service serv = new Service();
		 String msg=serv.getShenPiAuthorityable(account);
		 response.setCharacterEncoding("UTF-8");
	     response.setContentType("text/html");
	     PrintWriter out = response.getWriter();
	     System.out.println(msg);
	     out.print(msg);
	     out.flush();
	     out.close();
	}

}
