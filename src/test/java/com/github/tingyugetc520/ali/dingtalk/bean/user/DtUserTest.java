package com.github.tingyugetc520.ali.dingtalk.bean.user;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Date;

@Test
@Slf4j
public class DtUserTest {

    public void testFromJson() {
        String json = "{" +
                "    \"errcode\":0," +
                "    \"unionEmpExt\":{}," +
                "    \"unionid\":\"unionid\"," +
                "    \"openId\":\"openid\"," +
                "    \"roles\":[" +
                "        {" +
                "            \"groupName\":\"默认\"," +
                "            \"name\":\"主管理员\"," +
                "            \"id\":1834797999," +
                "            \"type\":101" +
                "        }" +
                "    ]," +
                "    \"remark\":\"\"," +
                "    \"userid\":\"manager6666\"," +
                "    \"isLeaderInDepts\":\"{1:false}\"," +
                "    \"isBoss\":false," +
                "    \"hiredDate\":1613577600000," +
                "    \"isSenior\":false," +
                "    \"tel\":\"\"," +
                "    \"department\":[" +
                "        1" +
                "    ]," +
                "    \"workPlace\":\"\"," +
                "    \"email\":\"emial@dtjava.com\"," +
                "    \"orderInDepts\":\"{1:176305901987690512}\"," +
                "    \"mobile\":\"1234567890\"," +
                "    \"errmsg\":\"ok\"," +
                "    \"active\":true," +
                "    \"avatar\":\"\"," +
                "    \"isAdmin\":true," +
                "    \"tags\":{}," +
                "    \"isHide\":false," +
                "    \"jobnumber\":\"\"," +
                "    \"name\":\"DtJava\"," +
                "    \"extattr\":{" +
                "       \"爱好\":\"写代码\"," +
                "       \"职级\":\"架构师\"," +
                "       \"花名\":\"花的名字\"" +
                "    }," +
                "    \"stateCode\":\"86\"," +
                "    \"position\":\"\"," +
                "    \"realAuthed\":true" +
                "}";
        DtUser dtUser = DtUser.fromJson(json);
        log.info("user: {}", dtUser);
    }

    public void testToJson() {
        DtUser dtUser = DtUser.builder()
                .hiredDate(new Date(1613577600000L))
                .build();
        log.info("json: {}", dtUser.toJson());
    }

}
