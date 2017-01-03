package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class NewsServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pages = request.getParameter("pages");
       int pages_num=Integer.parseInt(pages);
       
        System.out.println("pages=" + pages);
        String msg=null;

        // 新建服务对象
        Service serv = new Service();
        
        //获取消息信息
        String info = serv.getNewsInfo(pages_num);
        if (info!=null) {
            msg=info;
        } else {
            msg="fail";
        }

        // 返回信息到客户端
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        System.out.println(msg);
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
