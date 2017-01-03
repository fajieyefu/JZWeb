package com.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadFileServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String fileName = req.getParameter("fileName");
		String file_path = "file:///E:/files/" + fileName;
		URL url = new URL(file_path);
		URLConnection conn = url.openConnection();
		int filesize = conn.getContentLength();// 数据长度
		BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		// 文件名称转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		response.reset();
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("utf-8"), "iso8859-1"));
		response.addHeader("Content-Length", "" + filesize);
		response.setContentType("application/octet-stream");
		BufferedOutputStream os = new BufferedOutputStream(
				response.getOutputStream());
		// 从输入流中读入字节流，然后写到文件中
		byte[] buffer = new byte[1024];
		int nRead;
		while ((nRead = bis.read(buffer, 0, 1024)) > 0) { // bis为网络输入流
			os.write(buffer, 0, nRead);
		}
		bis.close();
		os.flush();
		os.close();

	}

}
