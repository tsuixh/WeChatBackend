package org.tsui.process;

import org.tsui.entity.ReceiveXmlEntity;

/**
 * 微信xml消息处理流程逻辑
 * @author TsuiXh
 */
public class WechatProcess {
	public String processWechatMsg(String xml) {
		
		//解析xml数据
		//TODO:创建ReceiveXmlEntity类以及ReceiveXmlProcess类
		ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);
		
		/*
		 * 根据发送来的消息，获取需要回复的内容
		 * 调用自动回复机器人接口
		 * TODO：创建自动回复机器人类RobotProcess
		 */
		String result = "";
		
		//响应文字消息
		if ("text".endsWith(xmlEntity.getMsgType())) {
			result = new RobotProcess().getReplyMsg(xmlEntity.getContent(), xmlEntity.getFromUserName(), xmlEntity.getToUserName());
		}
		//响应微信事件
		if ("event".endsWith(xmlEntity.getMsgType())) {
			result = new RobotProcess().processEvent(xmlEntity.getEvent(), xmlEntity.getFromUserName(), xmlEntity.getToUserName());
		}
		
		return result;
	}
}
