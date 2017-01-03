package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class QualityServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account= req.getParameter("account");
		String userName=req.getParameter("userName"); 
		userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
		String bom_model=req.getParameter("bom_model"); 
		bom_model = new String(bom_model.getBytes("ISO-8859-1"), "UTF-8");
		String check_gongxu=req.getParameter("check_gongxu");
		check_gongxu = new String(check_gongxu.getBytes("ISO-8859-1"), "UTF-8");
		String monitor=req.getParameter("monitor");
		monitor = new String(monitor.getBytes("ISO-8859-1"), "UTF-8");
		String inspector=req.getParameter("inspector");
		inspector = new String(inspector.getBytes("ISO-8859-1"), "UTF-8");
		String off_date=req.getParameter("off_date");
		off_date = new String(off_date.getBytes("ISO-8859-1"), "UTF-8");
		String check_date=req.getParameter("check_date");
		check_date = new String(check_date.getBytes("ISO-8859-1"), "UTF-8");
		String produce_date=req.getParameter("produce_date");
		produce_date = new String(produce_date.getBytes("ISO-8859-1"), "UTF-8");
		String production_no=req.getParameter("production_no");
		production_no = new String(production_no.getBytes("ISO-8859-1"), "UTF-8");
		String check_gongxu_name=req.getParameter("check_gongxu_name");
		check_gongxu_name = new String(check_gongxu_name.getBytes("ISO-8859-1"), "UTF-8");
		String monitor_name=req.getParameter("monitor_name");
		monitor_name = new String(monitor_name.getBytes("ISO-8859-1"), "UTF-8");
		String inspector_name=req.getParameter("inspector_name");
		inspector_name = new String(inspector_name.getBytes("ISO-8859-1"), "UTF-8");
		Map<String,String> map = new HashMap<String, String>();
		map.put("bom_model",bom_model);
		map.put("check_gongxu",check_gongxu.split(":")[0]);
		map.put("monitor",monitor.split(":")[0]);
		map.put("inspector",inspector.split(":")[0]);
		map.put("off_date",off_date);
		map.put("check_date",check_date);
		map.put("produce_date",produce_date);
		map.put("production_no",production_no);
		map.put("check_gongxu_name",check_gongxu_name);
		map.put("monitor_name",monitor_name);
		map.put("inspector_name",inspector_name);
		map.put("account",account);
		map.put("userName",userName);
		Service serv = new Service();
		String info=serv.makeQualityInfo(map);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		System.out.println(info);
		out.print(info);
		out.flush();
		out.close();
	}

}
