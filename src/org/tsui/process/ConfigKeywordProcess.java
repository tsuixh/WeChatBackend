package org.tsui.process;

import java.sql.SQLException;

import org.tsui.entity.Article;
import org.tsui.entity.Keyword;
import org.tsui.util.DaoHelper;

/**
 * @author TsuiXh
 *	负责添加关键词对应回复的类
 */
public class ConfigKeywordProcess {
	
	/**
	 * 将图文和关键词建立关联
	 * @param article			图文
	 * @param keywords	关键字长串
	 * @return					添加是否成功
	 * @throws SQLException 
	 */
	public boolean addArticleReplyProcess(Article article, String keywords) throws SQLException {
		boolean isSuccess = false;
		/*
		 * 添加图文并将其与关键词形成关联
		 */
		//1.处理关键词字符串
		String[] tempKeywords = processKeywords(keywords);
		
		//2.添加文章并获取文章自增长id
		int articleID = DaoHelper.addArticle(article);
		//3.根据获得的文章id处理关键词
		isSuccess = DaoHelper.addKeywords(articleID, tempKeywords, "article");
		
		return isSuccess;
	}
	
	/**
	 * 处理关键词长串
	 * @param keywords
	 * @return	关键词数组
	 */
	private String[] processKeywords(String keywords) {
		return keywords.split("，");
	}

	/**
	 * 将文字消息与关键词进行关联
	 * @param content		文字内容
	 * @param keyword	关键词串
	 * @return	true if success
	 * @throws SQLException 
	 */
	public boolean addTextReplyProcess(String content, String keyword) throws SQLException {
		
		boolean isSuccess = false;
		
		if ("default".equals(keyword)) {
			//1.查询数据库中是否有"default"记录
			Keyword key = DaoHelper.queryByKeyword("default");
			
			if (key != null) {
				//如果存在默认回复，则进行修改
				
				//1.获取关键词对应的文字回复的text_id
				Keyword defaultKey = DaoHelper.queryByKeyword("default");
				int textID = defaultKey.getText_id();
				
				//2.修改text_id所对应的内容
				isSuccess = DaoHelper.updateTextReply(content, textID);
			} else {
				//如果不存在则进行添加默认文字回复
				
				//1.处理关键词串
				String[] tempKeywords = new String[]{"default"};
				
				//2.添加默认文字回复并获取子增长id
				int textID = DaoHelper.addTextReply(content);
				
				//3.根据获取到的文字id处理关键词
				isSuccess = DaoHelper.addKeywords(textID, tempKeywords, "text");
			}
		} else {
			//1.处理关键词串
			String[] tempKeywords = processKeywords(keyword);
			
			//2.添加文字回复并获取自增长id
			int textID = DaoHelper.addTextReply(content);
			
			//3.根据获取到的文字id处理关键词
			isSuccess = DaoHelper.addKeywords(textID, tempKeywords, "text");
		}
		
		return isSuccess;
	}
}
