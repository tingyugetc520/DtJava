package com.github.tingyugetc520.ali.dingtalk.demo;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.api.impl.DtServiceImpl;
import com.github.tingyugetc520.ali.dingtalk.bean.agent.DtAgentAuthScope;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUser;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
public class DtDemoApp {

	private static DtDemoConfigStorage dtConfigStorage;
	private static DtService dtService;

	public static void main(String[] args) throws DtErrorException {
		System.out.println("application start");
		initDt();

		DtAgentAuthScope authScope = dtService.getAgentService().getAuthScope();
		log.info("auth scope:{}", authScope);

		DtUser user = dtService.getUserService().getById(dtConfigStorage.getUserId());
		log.info("dt user:{}", user);

		DtCorpConversationMessage message = DtCorpConversationMessage.builder()
				.agentId(dtConfigStorage.getAgentId())
				.userIds(Lists.newArrayList("manager6666"))
				.msg(DtMessage.TEXT().content("this is content").build())
				.build();
		dtService.getCorpConversationMsgService().send(message);

		System.out.println("application end");
	}

	private static void initDt() {
		InputStream inputStream = ClassLoader.getSystemResourceAsStream("test-config.json");
		dtConfigStorage = DtDemoConfigStorage.fromXml(inputStream);

		dtService = new DtServiceImpl();
		dtService.setDtConfigStorage(dtConfigStorage);
	}

}