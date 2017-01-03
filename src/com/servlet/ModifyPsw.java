package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class ModifyPsw extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account=req.getParameter("account");
		String original_psw=req.getParameter("original_psw");
		String new_psw=req.getParameter("new_psw");
		Service serv = new Service();
		String info =serv.modifyPsw(account,original_psw,new_psw); 
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.print(info);
		out.flush();
		out.close();

	}

}
