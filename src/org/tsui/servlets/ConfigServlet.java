package org.tsui.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tsui.entity.Article;
import org.tsui.process.ConfigKeywordProcess;
import org.tsui.util.DaoHelper;

/**
 * @author TsuiXh
 *	用来配置关键词回复
 */
@WebServlet("/ConfigServlet")
public class ConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//获取回复类型
		String reply_type = request.getParameter("reply_type");
		boolean isSuccess = false;
		
		
		if ("article".equals(reply_type)) {
			//添加图文回复
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String picUrl = request.getParameter("pic_url");
			String url = request.getParameter("url");
			String keyword = request.getParameter("keyword");
			Article article = new Article(title, description, picUrl, url);
			try {
				isSuccess = new ConfigKeywordProcess().addArticleReplyProcess(article, keyword);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("text".equals(reply_type)) {
			//添加文字回复
			String content = request.getParameter("content");
			String keyword = request.getParameter("keyword");
			
			try {
				isSuccess = new ConfigKeywordProcess().addTextReplyProcess(content, keyword);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("default".equals(reply_type)) {
			//设置默认回复
			String defaultReplyText = request.getParameter("content");
			//debug
			System.out.println(defaultReplyText);
			try {
				isSuccess = new ConfigKeywordProcess().addTextReplyProcess(defaultReplyText, "default");
				//debug
				System.out.println("添加是否成功：" + isSuccess);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("ad".equals(reply_type)) {
			//设置最新的广告
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String picUrl = request.getParameter("pic_url");
			String url = request.getParameter("url");
			Article advertisement = new Article(title, description, picUrl, url);
			try {
				isSuccess = DaoHelper.addAdvertisement(advertisement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (isSuccess) {
			//添加成功，返回主页
			request.setAttribute("isSuccess", true);
			request.getRequestDispatcher("itech.jsp").forward(request, response);
		} else {
			//添加失败的话则返回服务器错误
			response.sendError(500);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
