package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class ContractApply extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Service serv = new Service();
		String info =serv.getContractDetailList();
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out= resp.getWriter();
		out.print(info);
		out.flush();
		out.close();
	}

}
