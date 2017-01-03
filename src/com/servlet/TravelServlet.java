package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class TravelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account=req.getParameter("account");
		String departcode=req.getParameter("departcode");
		String time=req.getParameter("time");
		time=new String(time.getBytes("ISO-8859-1"), "UTF-8");
		String departname=req.getParameter("departname");
		departname=new String(departname.getBytes("ISO-8859-1"), "UTF-8");
		String travelType=req.getParameter("travelType");
		travelType=new String(travelType.getBytes("ISO-8859-1"), "UTF-8");
		String title=req.getParameter("title");
		title=new String(title.getBytes("ISO-8859-1"), "UTF-8");
		String content=req.getParameter("content");
		content=new String(content.getBytes("ISO-8859-1"), "UTF-8");
		String travel_date=req.getParameter("travel_date");
		travel_date=new String(travel_date.getBytes("ISO-8859-1"), "UTF-8");
		String travelDays=req.getParameter("travelDays");
		travelDays=new String(travelDays.getBytes("ISO-8859-1"), "UTF-8");
		Service serv = new Service();
		String info=serv.makeTravel(account,departname,departcode,time,travelType,title,content,
				travel_date,travelDays);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String msg;
		if (info.equals("fail")) {
			msg="fail";
		}else{
			msg=info;
		}
		System.out.println("出差申请："+msg);
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
