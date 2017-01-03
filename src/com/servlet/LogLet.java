package com.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.service.Service;
/*
 * ���ڴ���ͻ��˵ĵ�½����
 */
public class LogLet extends HttpServlet {

    private static final long serialVersionUID = 369840050351775312L;
    											

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ���տͻ�����Ϣ
        String username = request.getParameter("username");
        username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
        String password = request.getParameter("password");
        System.out.println(username + "--" + password);
        String msg=null;

        // �½��������
        Service serv = new Service();
        

        // ��֤����
        String loged = serv.login(username, password);
        if (loged!=null) {
            //System.out.print("Succss");
        	msg=loged;
            request.getSession().setAttribute("username", username);
            // response.sendRedirect("welcome.jsp");
        } else {
            msg="fail";
        }

        // ������Ϣ���ͻ���
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
//        out.print("�û�����" + username);
//        out.print("���룺" + password);
        System.out.println(msg);
        out.print(msg);
        out.flush();
        out.close();

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
