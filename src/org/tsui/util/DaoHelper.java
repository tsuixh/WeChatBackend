package org.tsui.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.tsui.entity.Article;
import org.tsui.entity.Keyword;


/**
 * 数据访问层，查询封装
 * @author TsuiXh
 */
public class DaoHelper {
	
	private static Connection conn;
	/**
	 * 通过关键词查询出回复类型以及回复信息的具体信息
	 * @param key		关键词
	 * @return			通过关键词查询出的结果对象
	 * @throws SQLException
	 */
	public static Keyword queryByKeyword(String key) throws SQLException {
		Keyword keyword = null;
		conn = DatabaseUtil.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT reply_type,article_id,text_id FROM KEYWORD WHERE KEYWORD = ?");
		ps.setString(0, key);
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			String reply_type = rs.getString("reply_type");
			if ("article".endsWith(reply_type)) {
				int article_id = rs.getInt("article_id");
				keyword = new Keyword();
				keyword.setReply_type(reply_type);
				keyword.setArticle_id(article_id);
			} else if ("text".endsWith(reply_type)) {
				int text_id = rs.getInt("text_id");
				keyword = new Keyword();
				keyword.setReply_type(reply_type);
				keyword.setText_id(text_id);
			}
		}
		return keyword;
	}

	/**
	 * 通过id查询文章
	 * @param article_id
	 * @return
	 * @throws SQLException 
	 */
	public static Article queryArticleById(int article_id) throws SQLException {
		Article article = null;
		conn = DatabaseUtil.getConn();
		PreparedStatement ps = conn.prepareStatement("SELECT title,description,pic_url,ad_url FROM article WHERE article_id = ?");
		ps.setInt(0, article_id);
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			String title = rs.getString("title");
			String description = rs.getString("description");
			String pic_url = rs.getString("pic_url");
			String url = rs.getString("url");
			article = new Article(title, description, pic_url, url);
		}
		return article;
	}
	
	/**
	 * 查询广告
	 * @return
	 * @throws SQLException
	 */
	public static Article queryCurrentAdvertisement() throws SQLException {
		Article article = null;
		conn = DatabaseUtil.getConn();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT title,description,pic_url,ad_url FROM ad WHERE is_active = 1");
		
		String title = rs.getString("title");
		String description = rs.getString("description");
		String pic_url = rs.getString("pic_url");
		String ad_url = rs.getString("ad_url");
		//广告
		article = new Article(title, description, pic_url, ad_url);
		
		return article;
	}
	
	/**
	 * 通过id查询文字回复消息
	 * @param id	文字回复id
	 * @return		文字消息
	 * @throws SQLException
	 */
	public static String queryTextReplyById(int id) throws SQLException {
		String reply_text = "";
		conn = DatabaseUtil.getConn();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT content FROM text_reply WHERE text_id = " + String.valueOf(id));
		reply_text = rs.getString("content");
		return reply_text;
	}

	/**
	 * 获取关注事件回复的图文
	 * @return ArrayList
	 * @throws SQLException 
	 */
	public static ArrayList<Article> queryArticles4Subscrible() throws SQLException {
		ArrayList<Article> articles = new ArrayList<>();
		conn = DatabaseUtil.getConn();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT title,description,pic_url,ad_url FROM article WHERE is4_sub = 1");
		if (rs != null) {
			while (rs.next()) {
				String title = rs.getString("title");
				String description = rs.getString("description");
				String pic_url = rs.getString("pic_url");
				String url = rs.getString("url");
				Article article = new Article(title, description, pic_url, url);
				articles.add(article);
			}
		}
		return articles;
	}
}
