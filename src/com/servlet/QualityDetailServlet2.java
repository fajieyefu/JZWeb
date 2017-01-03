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

import sun.rmi.runtime.Log;

import com.service.Service;

public class QualityDetailServlet2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String product_no=req.getParameter("product_no");
//		String process_code=req.getParameter("process_code");
//		String paixu=req.getParameter("paixu");
//		String remark=req.getParameter("remark");
//		remark = new String(remark.getBytes("ISO-8859-1"), "UTF-8"); 
//		String item_result=req.getParameter("item_result");
//		item_result = new String(item_result.getBytes("ISO-8859-1"), "UTF-8");
		req.setCharacterEncoding("utf-8");
		String jsonData =getData(req);
		jsonData=new String(jsonData.getBytes("ISO-8859-1"),"utf-8");
		System.out.println(jsonData);
		Service serv = new Service();
		String info = serv.makeQualityDetail(jsonData);
//		String info = serv.makeQualityDetail(product_no,process_code,paixu,remark,item_result);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		System.out.println(info);
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
