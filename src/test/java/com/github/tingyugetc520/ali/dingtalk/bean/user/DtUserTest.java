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
                "    \"unionid\":\"xZwiPB8TBz9XbB7nzYpM62giEiE\"," +
                "    \"openId\":\"xZwiPB8TBz9XbB7nzYpM62giEiE\"," +
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
                "    \"email\":\"tingyugetc@163.com.cn\"," +
                "    \"orderInDepts\":\"{1:176305901987690512}\"," +
                "    \"mobile\":\"18351925597\"," +
                "    \"errmsg\":\"ok\"," +
                "    \"active\":true," +
                "    \"avatar\":\"\"," +
                "    \"isAdmin\":true," +
                "    \"tags\":{}," +
                "    \"isHide\":false," +
                "    \"jobnumber\":\"\"," +
                "    \"name\":\"卞弈博\"," +
                "    \"extattr\":{" +
                "        \"爱好\":\"写代码\"" +
                "    }," +
                "    \"stateCode\":\"86\"," +
                "    \"position\":\"\"," +
                "    \"realAuthed\":true" +
                "}";
        DtUser dtUser = DtUser.fromJson(json);
        log.info("user: {}", dtUser);
    }

    public void testToJson() {
        DtUser dtUser = DtUser.builder().hiredDate(new Date()).build();
        log.info("json: {}", dtUser.toJson());
    }

}
