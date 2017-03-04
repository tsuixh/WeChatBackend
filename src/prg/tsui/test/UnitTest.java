package prg.tsui.test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.tsui.entity.Article;
import org.tsui.process.RobotProcess;
import org.tsui.util.DaoHelper;

public class UnitTest {

	public static void main(String[] args) {
		try {
			String defaultTextReply = DaoHelper.queryTextReplyById(36);
			System.out.println(defaultTextReply.contains("\\n"));
			System.out.println(defaultTextReply.indexOf("\\n"));
			defaultTextReply = defaultTextReply.replaceAll("\\\\n", "\n");
			System.out.println(defaultTextReply);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		StringBuffer sb = new StringBuffer();
//		sb.append("你好，这里是武理解析");
//		sb.append("\n");
//		sb.append("回复科目查看更多信息");
//		String result = sb.toString();
//		System.out.println(result);
	}

}
