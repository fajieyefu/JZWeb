package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class WorkJournalServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user_man=req.getParameter("account");
		System.out.println(user_man);
		String user_man_name=req.getParameter("user_man_name");
		user_man_name=new String(user_man_name.getBytes("ISO-8859-1"),"utf-8");
		String depart_code=req.getParameter("depart_code");
		depart_code=new String(depart_code.getBytes("ISO-8859-1"),"utf-8");
		String depart_name=req.getParameter("depart_name");
		depart_name=new String(depart_name.getBytes("ISO-8859-1"),"utf-8");
		String main_work=req.getParameter("main_work");
		main_work=new String(main_work.getBytes("ISO-8859-1"),"utf-8");
		String work_detail=req.getParameter("work_detail");
		work_detail=new String(work_detail.getBytes("ISO-8859-1"),"utf-8");
		Service serv = new Service();
		String info =serv.makeWorkJournal(user_man,user_man_name,depart_code,depart_name,
				main_work,work_detail);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		System.out.println(info);
		out.print(info);
		out.flush();
		out.close();
	}

}
