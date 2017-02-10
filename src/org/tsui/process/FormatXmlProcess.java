package org.tsui.process;

import java.util.ArrayList;
import java.util.Date;

import org.tsui.entity.Article;

/**
 * 封装最终的xml格式结果
 * @author TsuiXh
 */
public class FormatXmlProcess {
	/**
	 * 封装文字类的返回消息
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public String formatTextAnswer(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]</ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}
	
	/**
	 * 封装图文类返回消息
	 * @param to		发送到
	 * @param from	发送者
	 * @param articles		文章集合
	 * @return
	 */
	public String formatArticleAnswer(String to, String from, ArrayList<Article> articles) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>");
		sb.append(articles.size());
		sb.append("</ArticleCount><Articles>");
		for (int i = 0; i < articles.size(); i ++) {
			sb.append("<item><Title><![CDATA[");
			sb.append(articles.get(i).getTitle());
			sb.append("]]></Title><Description><![CDATA[");
			sb.append(articles.get(i).getDescription());
			sb.append("]]></Description><PicUrl><![CDATA[");
			sb.append(articles.get(i).getPicUrl());
			sb.append("]]></PicUrl><Url><![CDATA[");
			sb.append(articles.get(i).getUrl());
			//添加用户的openId
			sb.append("&openId=");
			sb.append(to);
			sb.append("]]></Url></item>");
		}
		sb.append("</Articles></xml>");
		return sb.toString();
	}
}
