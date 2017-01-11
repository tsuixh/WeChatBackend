package org.tsui.entity;

/**
 * ¹Ø¼ü´ÊÄ£ÐÍ
 * @author TsuiXh
 */
public class Keyword {
	
	private int key_id;
	private String keyword = "";
	private String reply_type = "";
	private int article_id;
	private int text_id;
	public int getKey_id() {
		return key_id;
	}
	public void setKey_id(int key_id) {
		this.key_id = key_id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getReply_type() {
		return reply_type;
	}
	public void setReply_type(String reply_type) {
		this.reply_type = reply_type;
	}
	public int getArticle_id() {
		return article_id;
	}
	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}
	public int getText_id() {
		return text_id;
	}
	public void setText_id(int text_id) {
		this.text_id = text_id;
	}
	
}
