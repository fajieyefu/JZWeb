package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class AuditServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loan_code= req.getParameter("loan_code");
		String audit_code= req.getParameter("audit_code");
		String flag_num= req.getParameter("flag_num");
		Service serv = new Service();
		String info = serv.changeFlagType(loan_code,audit_code,flag_num);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		System.out.println("审批结果"+info);
		out.print(info);
		out.flush();
		out.close();

				
	}

}
