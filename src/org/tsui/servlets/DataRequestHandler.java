package org.tsui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tsui.entity.Article;
import org.tsui.entity.Keyword;
import org.tsui.entity.PageAttr;
import org.tsui.entity.StateReporter;
import org.tsui.util.DaoHelper;
import org.tsui.util.DaoHelper.CallBack;

import com.google.gson.Gson;

/**
 * 处理数据请求以及异步请求等
 */
@WebServlet("/RequestHandler")
public class DataRequestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//获取请求类型
		String type = request.getParameter("type");
		
		//请求处理
		if ("keyword".equals(type)) {
			int currentPage = Integer.parseInt(request.getParameter("page"));
			
			//配置分页查询属性
			PageAttr pa = new PageAttr();
			pa.setColumns(" key_id,keyword,reply_type");
			pa.setTableName("keyword");
			pa.setPageSize(20);
			pa.setCurrentPage(currentPage);
			
			//执行分页查询获取list数据
			try {
				PageAttr pageAttr = DaoHelper.findByPage(pa, new CallBack() {
					
					@Override
					public List<Keyword> getData(ResultSet rs) throws SQLException {
						List<Keyword> tempData = new ArrayList<>();
						while (rs.next()) {
							int key_id = rs.getInt("key_id");
							String keyword = rs.getString("keyword");
							String reply_type = rs.getString("reply_type");
							tempData.add(new Keyword(key_id, keyword, reply_type));
						}
						return tempData;
					}
				});
				
				//生成Json数据返回
				Gson gson = new Gson();
				String responseText = gson.toJson(pageAttr);
				
				//debug
				//System.out.println(responseText);
				
				PrintWriter pw = response.getWriter();
				pw.write(responseText);
				pw.flush();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else if ("article".equals(type)) {
			//查询文章分页
			int currentPage = Integer.parseInt(request.getParameter("page"));
			
			//配置分页查询属性
			PageAttr pa = new PageAttr();
			pa.setColumns(" article_id,title,pic_url");
			pa.setTableName("article");
			pa.setPageSize(15);
			pa.setCurrentPage(currentPage);
			
			try {
				PageAttr pageAttr = DaoHelper.findByPage(pa, new CallBack() {
					
					@Override
					public List<Article> getData(ResultSet rs) throws SQLException {
						List<Article> tempList = new ArrayList<>();
						while (rs.next()) {
							int article_id = rs.getInt("article_id");
							String title = rs.getString("title");
							String pic_url = rs.getString("pic_url");
							Article article = new Article();
							article.setArticle_id(article_id);
							article.setTitle(title);
							article.setPicUrl(pic_url);
							tempList.add(article);
						}
						return tempList;
					}
				});
				
				Gson gson = new Gson();
				String responseText = gson.toJson(pageAttr);
				PrintWriter pw = response.getWriter();
				pw.write(responseText);
				pw.flush();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else if ("deleteKey".equals(type)) {
			int key_id = Integer.parseInt(request.getParameter("key_id"));
			try {
				boolean isSuccess = DaoHelper.deleteKeywordById(key_id);
				if (isSuccess) {
					//返回数据
					Gson gson = new Gson();
					String responseText = gson.toJson(new StateReporter("success"));
					PrintWriter pw = response.getWriter();
					pw.write(responseText);
					pw.flush();
				} else {
					//返回数据
					Gson gson = new Gson();
					String responseText = gson.toJson(new StateReporter("failed"));
					PrintWriter pw = response.getWriter();
					pw.write(responseText);
					pw.flush();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("choose4Sub".equals(type)) {
			int article_id = Integer.parseInt(request.getParameter("article_id"));
			
			try {
				boolean isSuccess = DaoHelper.setSubArticle(article_id);
				if (isSuccess) {
					//返回数据
					Gson gson = new Gson();
					String responseText = gson.toJson(new StateReporter("success"));
					PrintWriter pw = response.getWriter();
					pw.write(responseText);
					pw.flush();
				} else {
					//返回数据
					Gson gson = new Gson();
					String responseText = gson.toJson(new StateReporter("failed"));
					PrintWriter pw = response.getWriter();
					pw.write(responseText);
					pw.flush();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
