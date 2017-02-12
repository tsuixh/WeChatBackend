package prg.tsui.test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.tsui.entity.Article;
import org.tsui.util.DaoHelper;

public class UnitTest {

	public static void main(String[] args) {
		try {
			ArrayList<Article> articles = DaoHelper.queryArticles4Subscrible();
			System.out.println(articles.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
