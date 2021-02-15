package com.github.tingyugetc520.ali.dingtalk.demo.servlet;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventOutMessage;
import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeException;
import com.github.tingyugetc520.ali.dingtalk.message.DtMessageRouter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@Slf4j
public class DtEndpointServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  protected DtConfigStorage configStorage;
  protected DtService dtService;
  protected DtMessageRouter dtMessageRouter;

  public DtEndpointServlet(DtConfigStorage configStorage, DtService dtService,
                           DtMessageRouter dtMessageRouter) {
    this.configStorage = configStorage;
    this.dtService = dtService;
    this.dtMessageRouter = dtMessageRouter;
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    String signature = request.getParameter("signature");
    String nonce = request.getParameter("nonce");
    String timestamp = request.getParameter("timestamp");
    DtEventMessage message = null;
    try {
      message = DtEventMessage.fromEncryptedJson(request.getInputStream(), configStorage, timestamp, nonce, signature);
    } catch (DtRuntimeException e) {
      log.error("error", e);
    }
    if (message == null) {
      response.getWriter().println("非法请求");
      return;
    }

    DtEventOutMessage outMessage = this.dtMessageRouter.route(message);
    if (outMessage != null) {
      response.getWriter().println("合法请求");
      outMessage.toEncryptedJson(configStorage);
//      response.getWriter().write(outMessage.toEncryptedXml(this.configStorage));
    }
  }

}
