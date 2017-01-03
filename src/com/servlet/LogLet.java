package com.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.service.Service;
/*
 * 用于处理客户端的登陆请求
 */
public class LogLet extends HttpServlet {

    private static final long serialVersionUID = 369840050351775312L;
    											

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 接收客户端信息
        String username = request.getParameter("username");
        username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
        String password = request.getParameter("password");
        System.out.println(username + "--" + password);
        String msg=null;

        // 新建服务对象
        Service serv = new Service();
        

        // 验证处理
        String loged = serv.login(username, password);
        if (loged!=null) {
            //System.out.print("Succss");
        	msg=loged;
            request.getSession().setAttribute("username", username);
            // response.sendRedirect("welcome.jsp");
        } else {
            msg="fail";
        }

        // 返回信息到客户端
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
//        out.print("用户名：" + username);
//        out.print("密码：" + password);
        System.out.println(msg);
        out.print(msg);
        out.flush();
        out.close();

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
