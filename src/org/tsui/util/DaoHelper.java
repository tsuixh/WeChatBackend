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
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		PreparedStatement ps = conn.prepareStatement("SELECT reply_type,article_id,text_id FROM KEYWORD WHERE KEYWORD = ?");
		ps.setString(1, key);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
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
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		PreparedStatement ps = conn.prepareStatement("SELECT title,description,pic_url,ad_url FROM article WHERE article_id = ?");
		ps.setInt(1, article_id);
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
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT title,description,pic_url,ad_url FROM ad order by ad_id desc limit 1");
		
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
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
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
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
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
	
	/**
	 * 查询账号密码
	 * @param account
	 * @param password
	 * @return	状态码	登陆成功：1；账号不存在或密码错误：0；
	 * @throws SQLException 
	 */
	public static int qureyAdminister(String account, String password) throws SQLException {
		int stateCode = 0;
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		PreparedStatement ps = conn.prepareStatement("SELECT id FROM admin WHERE account = ? AND password = ?");
		ps.setString(1, account);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			stateCode = 1;
		} else {
			stateCode = 0;
		}
		return stateCode;
	}
	
	/**
	 * 添加文章并返回文章自增长id
	 * @param article		文章对象
	 * @return				添加文章后的自增长id
	 * @throws SQLException 
	 */
	public static int addArticle(Article article) throws SQLException {
		int auto_incrementID = 0;
		//获取连接
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		PreparedStatement ps = conn.prepareStatement("insert into article (title,description,pic_url,url,is4_sub) values (?,?,?,?,?)");
		ps.setString(1, article.getTitle());
		ps.setString(2, article.getDescription());
		ps.setString(3, article.getPicUrl());
		ps.setString(4, article.getUrl());
		ps.setInt(5, 0);
		//获取插入是否成功
		int effectedRow = ps.executeUpdate();
		if (effectedRow > 0) {
			//查询自增长id
			ps = conn.prepareStatement("select LAST_INSERT_ID()");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				auto_incrementID = rs.getInt(1);
			}
		}
		return auto_incrementID;
	}

	/**
	 * 添加关键词，根据回复类型的不同，来形成对照表
	 * @param id	回复资源id（articleID|textID）
	 * @param tempKeywords	关键词数组
	 * @param replyType	（"article"|"text"）回复类型
	 * @return	添加成功与否
	 * @throws SQLException 
	 */
	public static boolean addKeywords(int id, String[] tempKeywords, String replyType) throws SQLException {
		boolean isSuccess = false;
		//获取连接
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		PreparedStatement ps = null;
		int effectedRows = 0;
		//根据回复类型的不同做不同处理
		if ("article".equals(replyType)) {
			//回复类型为文章
			ps = conn.prepareStatement("insert into keyword (keyword,reply_type,article_id) values (?,?,?)");
			for (int i = 0; i < tempKeywords.length; ++i) {
				ps.setString(1, tempKeywords[i]);
				ps.setString(2, replyType);
				ps.setInt(3, id);
				effectedRows += ps.executeUpdate();
			}
		} else if ("text".equals(replyType)) {
			//回复类型为文字
			ps = conn.prepareStatement("insert into keyword (keyword,reply_type,text_id) values (?,?,?)");
			for (int i = 0; i < tempKeywords.length; ++i) {
				ps.setString(1, tempKeywords[i]);
				ps.setString(2, replyType);
				ps.setInt(3, id);
				effectedRows += ps.executeUpdate();
			}
		}
		
		//判断是否都添加成功
		if (effectedRows == tempKeywords.length) {
			isSuccess = true;
		}
		return isSuccess;
	}
	
	/**
	 * 添加广告
	 * @param advertisement	文章对象
	 * @return	true if success 
	 * @throws SQLException 
	 */
	public static boolean addAdvertisement(Article advertisement) throws SQLException {
		
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		
		PreparedStatement ps = conn.prepareStatement("insert into ad (title,description,pic_url,ad_url) values (?,?,?,?)");
		ps.setString(1, advertisement.getTitle());
		ps.setString(2, advertisement.getDescription());
		ps.setString(3, advertisement.getPicUrl());
		ps.setString(4, advertisement.getUrl());
		
		int effectedRow = ps.executeUpdate();
		
		return effectedRow > 0? true : false;
	}

	/**
	 * 添加文字回复返回自增长id
	 * @param content	文字消息内容
	 * @return	自增长id
	 * @throws SQLException 
	 */
	public static int addTextReply(String content) throws SQLException {
		int auto_incrementID = 0;
		
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		
		PreparedStatement ps = conn.prepareStatement("insert into text_reply (content) values (?)");
		ps.setString(1, content);
		int effectedRow = ps.executeUpdate();
		
		if (effectedRow > 0) {
			//查询自增长id
			ps = conn.prepareStatement("select LAST_INSERT_ID()");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				auto_incrementID = rs.getInt(1);
			}
		}
		
		return auto_incrementID;
	}

	/**
	 * 更新文字回复内容
	 * @param content	文字回复内容
	 * @param textID	文字回复对应的id
	 * @return	true if success
	 * @throws SQLException 
	 */
	public static boolean updateTextReply(String content, int textID) throws SQLException {
		
		if (conn == null) {
			conn = DatabaseUtil.getConn();
		}
		
		PreparedStatement ps = conn.prepareStatement("update text_reply set content = ? where text_id = ?");
		ps.setString(1, content);
		ps.setInt(2, textID);
		
		int effectedRow = ps.executeUpdate();
		return effectedRow > 0? true : false;
	}
	
	
}
