# DtJava

## 介绍

DtJava(DingTalk Java SDK-钉钉SDK) 封装了钉钉凭证、通讯录管理、消息通知等服务端接口，让开发者可以使用简单的配置，提供简洁的 API 以供方便快速地调用钉钉接口。

注意：目前SDK主要是以企业内建应用为主，ISV应用后面会陆续支持。

## 封装进度(企业内建应用)
- [x] 获取凭证
- [x] 身份验证
- [x] 通讯录管理（只读）
- [x] 消息通知-工作消息通知

## 环境要求

- Java8+

## 安装
* 通过Maven方式安装使用
```xml
<dependency>
    <groupId>com.github.tingyugetc520</groupId>
    <artifactId>dt-java</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
目前仅发布了0.0.1-SNAPSHOT版本。

由于尚处于初期开发阶段，所以未发布到Maven中央仓库，在项目中使用时需要在项目中添加如下的仓库配置。
```xml
<repositories>
    <repository>
        <id>github-dt-java-repo</id>
        <name>The Maven Repository on Github</name>
        <url>https://github.com/tingyugetc520/DtJava/maven-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

* 直接下载源码使用
```git
git clone https://github.com/tingyugetc520/DtJava.git
```
源码下载完成后，放置在您的项目中使用，同时请注意相关依赖的问题。

## 使用

```java
DtDefaultConfigImpl config = new DtDefaultConfigImpl();
config.setCorpId("corpId");
config.setAgentId(agentId);
config.setAppKey("appKey");
config.setAppSecret("appSecret");

// 代理设置
// 反向代理
config.setHttpProxyType(HttpProxyType.REVERSE.getCode());
config.setHttpProxyServer("proxyServer");
// 正向代理
// config.setHttpProxyType(DtDefaultConfigImpl.HttpProxyType.FORWARD.getCode());
// config.setHttpProxyHost("proxyHost");
// config.setHttpProxyPort(proxyPort);


DtServiceImpl dtService = new DtServiceImpl();
dtService.setDtConfigStorage(config);

DtAgentAuthScope authScope = dtService.getAgentService().getAuthScope();
log.info("auth scope:{}", authScope);

DtUser user = dtService.getUserService().getById(userId);
log.info("dt user:{}", user);

DtCorpConversationMessage message = DtCorpConversationMessage.builder()
			.agentId(config.getAgentId())
			.userIds(Lists.newArrayList("userId"))
			.msg(DtMessage.TEXT().content("this is content").build())
			.build();
dtService.getCorpConversationMsgService().send(message);
```

## 文档
后续补充

