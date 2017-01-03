package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class ContractInputServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String applyBill= req.getParameter("applyBill");
		String account=req.getParameter("account");
		String time=req.getParameter("time");
		String audit_type= req.getParameter("audit_type");
		String title=req.getParameter("title");
		String content=req.getParameter("content");
		String check_man=req.getParameter("check_man");
		String msg = null;
		Service serv= new Service();
		Boolean result=serv.makeContractInput(applyBill,account,time,audit_type,title,content,check_man);
		if(result){
			msg="success";
		}else{
			msg="fail";
		}
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out =resp.getWriter();
		out.print(msg);
		out.flush();
		out.close();
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	

}
