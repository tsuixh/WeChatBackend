package org.tsui.process;

import java.sql.SQLException;
import java.util.ArrayList;

import org.tsui.entity.Article;
import org.tsui.entity.Keyword;
import org.tsui.util.DaoHelper;

/**
 * 自动回复接口
 * @author TsuiXh
 */
public class RobotProcess {
	
	/**
	 * 自动回复消息
	 * @param content
	 * @param to
	 * @param from
	 * @return
	 */
	public String getReplyMsg(String content, String to, String from) {

		String result = "";
		try {
			//查询关键词
			Keyword keyword = DaoHelper.queryByKeyword(content);
			
			//如果没有对应关键词
			if (keyword == null) {
				//回复默认信息（只能有一条默认信息）
				content = "default";
				//必须在关键词表中添加默认回复
				keyword = DaoHelper.queryByKeyword(content);
				result = getReplyByKeyword(keyword, to, from);
			}else if (keyword != null) {
				//根据关键词获取回复，回复图文或者文字消息
				result = getReplyByKeyword(keyword, to, from);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 处理微信事件，如用户关注（回复图文消息）
	 * @param event
	 * @param to	发送到
	 * @param from	发送方
	 * @return
	 */
	public String processEvent(String event, String to, String from) {
		String result = "";
		ArrayList<Article> articles = null;
		if ("subscribe".endsWith(event)) {
			try {
				articles = DaoHelper.queryArticles4Subscrible();
				//debug
				System.out.println("查询出的文章数：" + articles.size());
				//FormatXml
				if (articles.size() > 0) {
					result = new FormatXmlProcess().formatArticleAnswer(to, from, articles);
					//debug
					System.out.println("准备发送的消息："+ result);
				} else {
					//如果数据库中没有设置图文回复，则回复默认文字消息
					String content = "欢迎来到武理解析，回复科目查看更多信息";
					result = new FormatXmlProcess().formatTextAnswer(to, from, content);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("unsubscribe".equals(event)) {
			//TODO：日后统计数据
			System.out.println("一位用户取消了关注");
		}
		
		
		return result;
	}
	
	/**
	 * 用来根据关键词来生成回复的xml格式的消息（图文，文字）
	 * @param keyword	关键词对象
	 * @param to				发送到
	 * @param from			发送方
	 * @return					包装成xml格式的回复消息（图文，文字）
	 * @throws SQLException 
	 */
	String getReplyByKeyword(Keyword keyword, String to, String from) throws SQLException {
		String result  = "";
		String reply_type = keyword.getReply_type();
		//回复图文
		if ("article".equals(reply_type)) {
			//获取图文回复
			Article article = DaoHelper.queryArticleById(keyword.getArticle_id());
			//获取广告
			Article advertisement = DaoHelper.queryCurrentAdvertisement();
			//组装图文
			ArrayList<Article> articles = new ArrayList<>();
			articles.add(article);
			if (advertisement != null) {
				articles.add(advertisement);
			}
			//封装xml信息
			result = new FormatXmlProcess().formatArticleAnswer(to, from, articles);
		} else if ("text".equals(reply_type)) {
			//获取文字回复
			int text_id = keyword.getText_id();
			String reply_content = DaoHelper.queryTextReplyById(text_id);
			
			//如果回复消息中含有换行的话，对换行字符进行处理
			if (reply_content.contains("\\n")) {
				reply_content = processEnter(reply_content);
			}
			
			result = new FormatXmlProcess().formatTextAnswer(to, from, reply_content);
		}
		
		return result;
	}

	/**
	 * 替换字符串中的\n为换行
	 * @param reply_content	需要处理的字符串
	 * @return	替换后的字符串
	 */
	private String processEnter(String reply_content) {
		return reply_content.replaceAll("\\\\n", "\n");
	}
}
