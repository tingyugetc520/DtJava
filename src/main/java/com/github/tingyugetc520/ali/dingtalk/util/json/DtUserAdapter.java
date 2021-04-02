package com.github.tingyugetc520.ali.dingtalk.util.json;

import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUser;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

/**
 * dt user adapter
 */
public class DtUserAdapter implements JsonDeserializer<DtUser>, JsonSerializer<DtUser> {

  @Override
  public DtUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject o = json.getAsJsonObject();
    DtUser user = new DtUser();

    if (o.get("department") != null) {
      JsonArray departJsonArray = o.get("department").getAsJsonArray();
      List<Long> departIds = new ArrayList<>(departJsonArray.size());
      for (JsonElement jsonElement : departJsonArray) {
        departIds.add(jsonElement.getAsLong());
      }
      user.setDepartIds(departIds);
    }

    if (o.get("orderInDepts") != null) {
      String orderString = o.get("orderInDepts").getAsString();
      JsonObject departJsonObject = GsonParser.parse(orderString);
      Map<Long, Long> departOrders = new HashMap<>(departJsonObject.size());
      for (Map.Entry<String, JsonElement> entry : departJsonObject.entrySet()) {
        departOrders.put(Long.parseLong(entry.getKey()), entry.getValue().getAsLong());
      }
      user.setDepartOrders(departOrders);
    }

    if (o.get("isLeaderInDepts") != null) {
      String leaderString = o.get("isLeaderInDepts").getAsString();
      JsonObject leaderJsonObject = GsonParser.parse(leaderString);
      Map<Long, Boolean> leaderInDeparts = new HashMap<>(leaderJsonObject.size());
      for (Map.Entry<String, JsonElement> entry : leaderJsonObject.entrySet()) {
        leaderInDeparts.put(Long.parseLong(entry.getKey()), entry.getValue().getAsBoolean());
      }
      user.setIsLeaderInDeparts(leaderInDeparts);
    }

    if (o.get("extattr") != null) {
      JsonObject extJsonObject = o.get("extattr").getAsJsonObject();
      Map<String, String> extAttr = new HashMap<>(extJsonObject.size());
      for (Map.Entry<String, JsonElement> entry : extJsonObject.entrySet()) {
        extAttr.put(entry.getKey(), entry.getValue().getAsString());
      }
      user.setExtAttr(extAttr);
    }

    if (o.get("roles") != null) {
      JsonArray roleJsonElements = o.get("roles").getAsJsonArray();
      List<DtUser.Role> roles = new ArrayList<>(roleJsonElements.size());
      for (JsonElement roleJsonElement : roleJsonElements) {
        JsonObject jsonObject = roleJsonElement.getAsJsonObject();
        roles.add(DtUser.Role.builder()
                .id(GsonHelper.getLong(jsonObject, "id"))
                .name(GsonHelper.getString(jsonObject, "name"))
                .type(GsonHelper.getInteger(jsonObject, "type"))
                .groupName(GsonHelper.getString(jsonObject, "groupName"))
                .build());
      }
      user.setRoles(roles);
    }

    user.setUserId(GsonHelper.getString(o, "userid"));
    user.setUnionId(GsonHelper.getString(o, "unionid"));

    user.setManagerUserId(GsonHelper.getString(o, "managerUserId"));
    user.setHiredDate(new Date(GsonHelper.getLong(o, "hiredDate")));
    user.setTel(GsonHelper.getString(o, "tel"));

    user.setRemark(GsonHelper.getString(o, "remark"));
    user.setWorkPlace(GsonHelper.getString(o, "workPlace"));

    user.setName(GsonHelper.getString(o, "name"));
    user.setPosition(GsonHelper.getString(o, "position"));
    user.setMobile(GsonHelper.getString(o, "mobile"));
    user.setStateCode(GsonHelper.getString(o, "stateCode"));
    user.setEmail(GsonHelper.getString(o, "email"));
    user.setOrgEmail(GsonHelper.getString(o, "orgEmail"));

    user.setIsSenior(GsonHelper.getBoolean(o, "isSenior"));
    user.setJobNumber(GsonHelper.getString(o, "jobnumber"));
    user.setActive(GsonHelper.getBoolean(o, "active"));
    user.setAvatar(GsonHelper.getString(o, "avatar"));

    user.setIsAdmin(GsonHelper.getBoolean(o, "isAdmin"));

    user.setIsHide(GsonHelper.getBoolean(o, "isHide"));
    user.setIsBoss(GsonHelper.getBoolean(o, "isBoss"));
    user.setRealAuthed(GsonHelper.getBoolean(o, "realAuthed"));
    return user;
  }

  @Override
  public JsonElement serialize(DtUser user, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject o = new JsonObject();
    if (user.getUserId() != null) {
      o.addProperty("userid", user.getUserId());
    }
    if (user.getUnionId() != null) {
      o.addProperty("unionid", user.getUnionId());
    }

    if (user.getManagerUserId() != null) {
      o.addProperty("managerUserId", user.getManagerUserId());
    }
    if (user.getHiredDate() != null) {
      o.addProperty("hiredDate", user.getHiredDate().getTime());
    }
    if (user.getTel() != null) {
      o.addProperty("tel", user.getTel());
    }

    if (user.getRemark() != null) {
      o.addProperty("remark", user.getRemark());
    }
    if (user.getWorkPlace() != null) {
      o.addProperty("workPlace", user.getWorkPlace());
    }

    if (user.getName() != null) {
      o.addProperty("name", user.getName());
    }
    if (user.getPosition() != null) {
      o.addProperty("position", user.getPosition());
    }
    if (user.getMobile() != null) {
      o.addProperty("mobile", user.getMobile());
    }
    if (user.getStateCode() != null) {
      o.addProperty("stateCode", user.getStateCode());
    }
    if (user.getEmail() != null) {
      o.addProperty("email", user.getEmail());
    }
    if (user.getOrgEmail() != null) {
      o.addProperty("orgEmail", user.getOrgEmail());
    }
    if (user.getIsSenior() != null) {
      o.addProperty("isSenior", user.getIsSenior());
    }
    if (user.getJobNumber() != null) {
      o.addProperty("jobnumber", user.getJobNumber());
    }
    if (user.getActive() != null) {
      o.addProperty("active", user.getActive());
    }
    if (user.getAvatar() != null) {
      o.addProperty("avatar", user.getAvatar());
    }
    if (user.getExtAttr() != null) {
      JsonObject jsonObject = new JsonObject();
      for (Map.Entry<String, String> entry : user.getExtAttr().entrySet()) {
        jsonObject.addProperty(entry.getKey(), entry.getValue());
      }
      o.add("extattr", jsonObject);
    }
    if (user.getRoles() != null) {
      JsonArray jsonArray = new JsonArray();
      for (DtUser.Role role : user.getRoles()) {
        JsonObject jsonObject = GsonHelper.buildJsonObject(
                "id", role.getId(),
                "name", role.getName(),
                "type", role.getType(),
                "groupName", role.getGroupName()
        );
        jsonArray.add(jsonObject);
      }
      o.add("roles", jsonArray);
    }
    if (user.getDepartIds() != null) {
      JsonArray jsonArray = new JsonArray();
      for (Long departId : user.getDepartIds()) {
        jsonArray.add(new JsonPrimitive(departId));
      }
      o.add("department", jsonArray);
    }
    if (user.getDepartOrders() != null) {
      JsonObject jsonObject = new JsonObject();
      for (Map.Entry<Long, Long> entry : user.getDepartOrders().entrySet()) {
        jsonObject.addProperty(entry.getKey().toString(), entry.getValue());
      }
      o.addProperty("orderInDepts", jsonObject.toString());
    }
    if (user.getIsAdmin() != null) {
      o.addProperty("isAdmin", user.getIsAdmin());
    }
    if (user.getIsLeaderInDeparts() != null) {
      JsonObject jsonObject = new JsonObject();
      for (Map.Entry<Long, Boolean> entry : user.getIsLeaderInDeparts().entrySet()) {
        jsonObject.addProperty(entry.getKey().toString(), entry.getValue());
      }
      o.addProperty("isLeaderInDepts", new Gson().toJson(jsonObject));
    }
    if (user.getIsHide() != null) {
      o.addProperty("isHide", user.getIsHide());
    }
    if (user.getIsBoss() != null) {
      o.addProperty("isBoss", user.getIsBoss());
    }
    if (user.getRealAuthed() != null) {
      o.addProperty("realAuthed", user.getRealAuthed());
    }

    return o;
  }

}
