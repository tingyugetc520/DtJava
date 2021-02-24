package com.github.tingyugetc520.ali.dingtalk.demo;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.api.impl.DtServiceImpl;
import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUser;
import com.github.tingyugetc520.ali.dingtalk.demo.servlet.DtEndpointServlet;
import com.github.tingyugetc520.ali.dingtalk.message.DtMessageRouter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.InputStream;

@Slf4j
public class DtDemoApp {

	private static DtDemoConfigStorage dtConfigStorage;
	private static DtService dtService;
	private static DtMessageRouter dtMessageRouter;

	public static void main(String[] args) throws Exception {
		log.info("application start");

		initDt();

		initServer();

		DtUser user = dtService.getUserService().getById(dtConfigStorage.getUserId());
		log.info("dt user:{}", user);
	}

	private static void initDt() {
		InputStream inputStream = ClassLoader.getSystemResourceAsStream("test-config.json");
		dtConfigStorage = DtDemoConfigStorage.fromJson(inputStream);

		dtService = new DtServiceImpl();
		dtService.setDtConfigStorage(dtConfigStorage);

		dtMessageRouter = new DtMessageRouter(dtService);
		dtMessageRouter
				.rule().async(false).eventType("").handler(((message, context, dtService1) -> {
					log.info("收到消息");
					return true;
				})).end();
	}

	private static void initServer() throws Exception {
		Server server = new Server(8080);

		ServletHandler servletHandler = new ServletHandler();
		server.setHandler(servletHandler);

		ServletHolder endpointServletHolder = new ServletHolder(new DtEndpointServlet(dtConfigStorage, dtService, dtMessageRouter));
		servletHandler.addServletWithMapping(endpointServletHolder, "/*");

		server.start();
		server.join();
	}

}