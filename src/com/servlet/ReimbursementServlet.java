package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.service.Service;

public class ReimbursementServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account = req.getParameter("account");
		String time = req.getParameter("time");
		time = new String(time.getBytes("ISO-8859-1"), "UTF-8");
		String departCode = req.getParameter("departCode");
		departCode = new String(departCode.getBytes("ISO-8859-1"), "UTF-8");
		String departname = req.getParameter("departname");
		departname = new String(departname.getBytes("ISO-8859-1"), "UTF-8");
		String applyFeeTypeString = req.getParameter("applyFeeTypeString");
		applyFeeTypeString = new String(
				applyFeeTypeString.getBytes("ISO-8859-1"), "UTF-8");
		String amount_string = req.getParameter("amount_string");
		amount_string = new String(amount_string.getBytes("ISO-8859-1"),
				"UTF-8");
		String title = req.getParameter("title");
		title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
		String content = req.getParameter("content");
		content = new String(content.getBytes("ISO-8859-1"), "UTF-8");
		Service serv = new Service();
		String result = serv.makeReimbursement(account, time, departname,
				departCode, applyFeeTypeString, amount_string, title, content,"");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String msg;
		if (result.equals("fail")) {
			msg = "fail";
		} else {
			msg = result;
		}
		System.out.println(msg);
		out.print(msg);
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/**
		 * 接收附件
		 * 
		 */
		StringBuilder sb = new StringBuilder();
		req.setCharacterEncoding("utf-8");
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件上传需要保存的路径，upload文件夹需存在
//		String path = req.getSession().getServletContext().getRealPath("/")
//				+ "applyfee_files";
		 String path="E:\\files\\reimbursement_files";
		System.out.println(path);
		// 设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
		factory.setRepository(new File(path));
		// 设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
		factory.setSizeThreshold(1024 * 1024);
		// 上传处理工具类（高水平API上传处理？）
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> list = null;
		try {
			// 调用 parseRequest（request）方法 获得上传文件 FileItem 的集合list 可实现多文件上传。
			list = (List<FileItem>) upload.parseRequest(req);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (FileItem item : list) {
			// 获取表单属性名字
			String name = item.getFieldName();
			// 如果获取的表单信息是普通的文本信息，即通过页面表单形式传递来的字符串
			if (item.isFormField()) {
				// 获取用户具体输入的字符串
				String value = item.getString();
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				req.setAttribute(name, value);
				System.out.println(name + ":" + value);
			}
			// 如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
			else {
				// 获取路径名
				String value = item.getName();
				// 取到最后一个反斜杠
				int start = value.lastIndexOf("\\");
				// 截取上传文件的字符串名字。+1是去掉反斜杠
				String filename = value.substring(start + 1);
				req.setAttribute(name, filename);
				System.out.println(name + ":" + filename);
				long times = System.currentTimeMillis();
				filename = times + ".jpg";
				sb.append("reimbursement_files/" + filename + ",");
				try {
					/*
					 * 第三方提供的方法直接写到文件中
					 */
					item.write(new File(path, filename));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		String fileString = sb.toString();
		if (!(fileString == null) || !(fileString.equals(""))) {
			int len = fileString.length();
			fileString = fileString.substring(0, len - 1);
			System.out.println(fileString);
		}
		String account = (String) req.getAttribute("account");
		String departcode = (String) req.getAttribute("departCode");
		String departname = (String) req.getAttribute("departname");
		String applyType = (String) req.getAttribute("applyFeeTypeString");
		String time = (String) req.getAttribute("time");
		String amount_string = (String) req.getAttribute("amount_string");
		String title = (String) req.getAttribute("title");
		String content = (String) req.getAttribute("content");
	

		Service serv = new Service();
		String result = serv.makeReimbursement(account, time, departname, departcode, applyType, amount_string, title, content,fileString);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		System.out.println("用款申请：" + result);
		out.print(result);
		out.flush();
		out.close();

	}
}
