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
    JsonObject wxErrorJsonObject = json.getAsJsonObject();

    if (wxErrorJsonObject.get("errcode") != null && !wxErrorJsonObject.get("errcode").isJsonNull()) {
      errorBuilder.errorCode(GsonHelper.getAsPrimitiveInt(wxErrorJsonObject.get("errcode")));
    }
    if (wxErrorJsonObject.get("errmsg") != null && !wxErrorJsonObject.get("errmsg").isJsonNull()) {
      errorBuilder.errorMsg(GsonHelper.getAsString(wxErrorJsonObject.get("errmsg")));
    }

    errorBuilder.json(json.toString());

    return errorBuilder.build();
  }

}
