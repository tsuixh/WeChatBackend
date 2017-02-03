package org.tsui.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tsui.entity.Keyword;
import org.tsui.entity.PageAttr;
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
		//获取请求类型
		String type = request.getParameter("type");
		
		//请求处理
		if ("keyword".equals(type)) {
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			
			//配置分页查询属性
			PageAttr pa = new PageAttr();
			pa.setColumns(" key_id,keyword,reply_type");
			pa.setTableName("keyword");
			pa.setPageSize(pageSize);
			pa.setCurrentPage(currentPage);
			
			//执行分页查询获取list数据
			try {
				@SuppressWarnings("unchecked")
				List<Keyword> keywordList = DaoHelper.findByPage(pa, new CallBack() {
					
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
				}).getData();
				
				//生成json数据返回
				Gson gson = new Gson();
				String responseText = gson.toJson(keywordList);
				response.getWriter().write(responseText);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else if ("article".equals(type)) {
			//TODO:查询文章分页
			
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
