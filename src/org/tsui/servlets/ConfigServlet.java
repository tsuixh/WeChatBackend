package org.tsui.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tsui.entity.Article;

/**
 * @author TsuiXh
 *	用来配置关键词回复
 */
@WebServlet("/ConfigServlet")
public class ConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取回复类型
		String reply_type = request.getParameter("reply_type");
		
		if ("article".equals(reply_type)) {
			//添加图文回复
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String picUrl = request.getParameter("pic_url");
			String url = request.getParameter("url");
			String keyword = request.getParameter("keyword");
			Article article = new Article(title, description, picUrl, url);
			
		} else if ("text".equals(reply_type)) {
			//添加文字回复
		} else if ("default".equals(reply_type)) {
			//设置默认回复
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
