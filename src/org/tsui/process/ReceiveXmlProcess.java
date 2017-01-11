package org.tsui.process;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tsui.entity.ReceiveXmlEntity;

/**
 * 解析微信xml消息，返回消息对象
 * @author TsuiXh
 */
public class ReceiveXmlProcess {
	/**
	 * 解析xml消息
	 * @param strXml
	 * @return
	 */
	public ReceiveXmlEntity getMsgEntity(String strXml) {
		ReceiveXmlEntity msg = null;
		
		if (strXml.length() <= 0 || strXml == null) {
			return null;
		} 
		
		//将字符串转化为xml文档对象
		Document document;
		try {
			document = DocumentHelper.parseText(strXml);
			//获取文档的根节点
			Element root = document.getRootElement();
			//遍历根节点下的所有子节点
			Iterator<?> iterator = root.elementIterator();
			
			//遍历所有节点
			msg = new ReceiveXmlEntity();
			//利用反射机制，调用set方法
			//获取该实体的元类型
			Class<?> c = Class.forName("org.tsui.entity.ReceiveXmlEntity");
			//创建实体对象
			msg = (ReceiveXmlEntity)c.newInstance();
			
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				//获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(element.getName());
				//获取set方法，field.getType()获取它的参数数据类型
				Method method = c.getDeclaredMethod("set" + element.getName(), field.getType());
				//调用set方法
				method.invoke(msg, element.getText());
			}
			
		} catch (Exception e) {
			System.out.println("xml格式异常" + strXml);
			e.printStackTrace();
		}
		return msg;
	}
}
