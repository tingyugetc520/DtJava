package com.github.tingyugetc520.ali.dingtalk;

import com.github.tingyugetc520.ali.dingtalk.api.impl.DtServiceImpl;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUser;
import com.github.tingyugetc520.ali.dingtalk.bean.agent.DtAgentAuthScope;
import com.github.tingyugetc520.ali.dingtalk.config.impl.DtDefaultConfigImpl;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.util.http.HttpProxyType;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

	public static void main(String[] args) throws DtErrorException {
		System.out.println("application start");

		DtDefaultConfigImpl config = new DtDefaultConfigImpl();
		config.setCorpId("dingc48f4a815cbc5cb3bc961a6cb783455b");
		config.setAgentId(1083328243L);
		config.setAppKey("dingqozzvdj7fv3vxnn7");
		config.setAppSecret("e91S4UfoV4I5KbCstxtCuqmo38MMH67bxn7fvzUQUhm_QqDVngu1xBoyimkeaNkt");

		// 代理设置
		config.setHttpProxyType(HttpProxyType.REVERSE.getCode());
		config.setHttpProxyServer("http://119.3.225.111/dt");

//		config.setHttpProxyType(DtDefaultConfigImpl.HttpProxyType.FORWARD.getCode());
//		config.setHttpProxyHost("192.168.120.145");
//		config.setHttpProxyPort(80);

		DtServiceImpl dtService = new DtServiceImpl();
		dtService.setDtConfigStorage(config);


		DtAgentAuthScope authScope = dtService.getAgentService().getAuthScope();
		log.info("auth scope:{}", authScope);

//		String userId = "manager6666";
//		DtUser user = dtService.getUserService().getById(userId);
//		log.info("dt user:{}", user);

		DtCorpConversationMessage message = DtCorpConversationMessage.builder()
				.agentId(config.getAgentId())
				.userIds(Lists.newArrayList("manager6666"))
				.msg(DtMessage.TEXT().content("this is content").build())
				.build();
		dtService.getCorpConversationMsgService().send(message);

		System.out.println("application end");
	}

}