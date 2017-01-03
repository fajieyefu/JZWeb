package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class ContractPaichanSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
//		String method =req.getParameter("method").toString();
		String jsonData=getData(req);
		Service serv = new Service();
		String info =serv.makePaichanInput(jsonData);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.print(info);
		out.flush();
		out.close();
	}

	private String getData(HttpServletRequest req) {
		String result = null;
        try {
            //包装request的输入流
            BufferedReader br = new BufferedReader(  
              new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
            //缓冲字符
            StringBuffer sb=new StringBuffer("");
            String line;
            while((line=br.readLine())!=null){
                sb.append(line);
            }
            br.close();//关闭缓冲流
            result=sb.toString();//转换成字符
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }  
            return result;
	}

}
