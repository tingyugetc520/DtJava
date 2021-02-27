package com.github.tingyugetc520.ali.dingtalk.util.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * json date
 */
public class DtDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        return new JsonPrimitive(src.getTime());
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null) {
            return null;
        }
        return new Date(json.getAsJsonPrimitive().getAsLong());
    }

}
