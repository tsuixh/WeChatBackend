package org.tsui.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tsui.process.WechatProcess;

/**
 * 微信服务端收发消息接口
 * 
 * @author TsuiXh
 *	MailAddress:	smartcyh@hotmail.com
 */
@WebServlet(description = "微信服务端收发消息接口", urlPatterns = { "/WechatServlet" })
public class WechatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		/*
		 *读取接收到的xml消息
		 */
		StringBuffer stringBuffer = new StringBuffer();
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while ((s = br.readLine()) != null) {
			stringBuffer.append(s);
		}
		//从微信服务器接收到的xml数据
		String xml = stringBuffer.toString();
		
		//debug
		System.err.println("接收到微信服务器的消息" + xml);
		
		String result = "";
		/**
		 * 判断是否是为微信接入激活验证，只有首次验证时才会受到echostr参数，此时需要把它直接返回
		 */
		String echostr = request.getParameter("echostr");
		if (echostr != null && echostr.length() > 1) {
			result = echostr;
		} else {
			//处理微信自动回复等逻辑
			//WechatProcess类以及自动回复逻辑
			result = new WechatProcess().processWechatMsg(xml);
			
			//debug
			System.err.println("发送的回复消息" + result);
		}
		
		/*
		 * 返回给微信服务器处理数据
		 */
		
		OutputStream os = response.getOutputStream();
		os.write(result.getBytes("UTF-8"));
		os.flush();
		os.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
