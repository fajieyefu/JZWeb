package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class CheckInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account = req.getParameter("account");
		String departcode = req.getParameter("departcode");
		String count = req.getParameter("count");
		String type = req.getParameter("type");
		type = new String (type.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(account+":"+type);
		Service serv = new Service ();
		String info = serv.getCheckInfo(account,departcode,count,type);
		String msg="";
		if(info!=null){
			msg=info;
		}else{
			msg="fail";
		}
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		System.out.println(msg);
		out.print(msg);
		out.flush();
		out.close();
	}

}
