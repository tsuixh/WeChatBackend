package org.tsui.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tsui.util.DaoHelper;
import org.tsui.util.EncryptUtil;

@WebServlet(description = "登陆处理", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//登陆逻辑
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String flag = request.getParameter("flag");
		
		//查询数据库
		try {
			int stateCode = DaoHelper.qureyAdminister(account, password);
			if (stateCode == 1) {
				//登陆成功
				//是否记住密码
				if (flag != null) {
					Cookie cookie = new Cookie("ACCOUNT_WE", EncryptUtil.MD5EncryptData(account));
					cookie.setMaxAge(7*24*60*60);
					response.addCookie(cookie);
				}
				//页面跳转到管理页面
				HttpSession session = request.getSession();
				session.setAttribute("user", account);
				request.getRequestDispatcher("itech.jsp").forward(request, response);
			} else {
				//账号或密码错误
				response.sendRedirect("loginError.html");
			}
		} catch (SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
