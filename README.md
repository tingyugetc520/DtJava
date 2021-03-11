# DtJava

## 介绍
DtJava(DingTalk Java SDK-钉钉SDK) 封装了钉钉凭证、通讯录管理、消息通知等服务端接口，让开发者可以使用简单的配置，提供简洁的 API 以供方便快速地调用钉钉接口。

注意：目前SDK主要是以企业内建应用为主，ISV应用后面会陆续支持。

## 文档
查看[wiki首页](https://github.com/tingyugetc520/DtJava/wiki)

## 安装
* 通过Maven方式安装使用
```xml
<dependency>
    <groupId>com.github.tingyugetc520</groupId>
    <artifactId>dt-java</artifactId>
    <version>0.1.2</version>
</dependency>
```
目前已发布0.1.2版本

DtJava已经发布到中央仓库，以下方法不推荐使用，后续将会逐步移除托管在`Github的Maven仓库`，并在2021-03-12完全移除。

~~由于尚处于初期开发阶段，所以未发布到Maven中央仓库，在项目中使用时需要在项目中添加Github仓库配置。~~

* 直接下载源码使用
```git
git clone https://github.com/tingyugetc520/DtJava.git
```
源码下载完成后，放置在您的项目中使用，同时请注意相关依赖的问题。

## 使用
可前往[查看DEMO项目](https://github.com/tingyugetc520/dtjava-demo)
```java
// 钉钉应用配置
DtDefaultConfigImpl config = new DtDefaultConfigImpl();
config.setCorpId("corpId");
config.setAgentId(agentId);
config.setAppKey("appKey");
config.setAppSecret("appSecret");

// DtService为SDK使用入口，后续接口使用均需要DtService
DtServiceImpl dtService = new DtServiceImpl();
dtService.setDtConfigStorage(config);

// 查询用户
DtUser user = dtService.getUserService().getById(userId);
log.info("dt user:{}", user);

// 发送工作消息通知
DtCorpConversationMessage message = DtCorpConversationMessage.builder()
			.agentId(config.getAgentId())
			.userIds(Lists.newArrayList("userId"))
			.msg(DtMessage.TEXT().content("this is content").build())
			.build();
dtService.getCorpConversationMsgService().send(message);
```
更多的示例可 [查看DEMO项目](https://github.com/tingyugetc520/dtjava-demo) ，包括事件消息回调处理等等。

## 封装进度(企业内建应用)
- [x] HTTP代理，支持正向代理与反向代理
- [x] 获取凭证
- [x] 身份验证
- [x] 通讯录管理（只读）
- [x] 消息通知-工作消息通知
- [x] HTTP事件回调

