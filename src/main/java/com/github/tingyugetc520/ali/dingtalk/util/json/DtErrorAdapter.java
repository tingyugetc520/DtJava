package com.github.tingyugetc520.ali.dingtalk.util.json;

import com.github.tingyugetc520.ali.dingtalk.error.DtError;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 *
 */
public class DtErrorAdapter implements JsonDeserializer<DtError> {

  @Override
  public DtError deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    DtError.DtErrorBuilder errorBuilder = DtError.builder();
    JsonObject jsonObject = json.getAsJsonObject();

    if (jsonObject.get("errcode") != null && !jsonObject.get("errcode").isJsonNull()) {
      errorBuilder.errorCode(GsonHelper.getAsPrimitiveInt(jsonObject.get("errcode")));
    }
    if (jsonObject.get("errmsg") != null && !jsonObject.get("errmsg").isJsonNull()) {
      errorBuilder.errorMsg(GsonHelper.getAsString(jsonObject.get("errmsg")));
    }

    errorBuilder.json(json.toString());

    return errorBuilder.build();
  }

}
