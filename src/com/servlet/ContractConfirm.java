package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class ContractConfirm extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loan_code = req.getParameter("loan_code");
		String flag = req.getParameter("flag");
		String confirm_text =req.getParameter("confirm_text");
		Service serv = new Service();
		String info = serv.changeContractFlag(loan_code,flag,confirm_text);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		System.out.println(info);
		out.print(info);
		out.flush();
		out.close();
	}
}
