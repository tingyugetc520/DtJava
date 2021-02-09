package com.github.tingyugetc520.ali.dingtalk.demo;

import com.github.tingyugetc520.ali.dingtalk.config.impl.DtDefaultConfigImpl;
import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.stream.JsonReader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class DtDemoConfigStorage extends DtDefaultConfigImpl {
    private static final long serialVersionUID = 4554681793802089123L;
    private String userId;

    public static DtDemoConfigStorage fromXml(InputStream is) {
        JsonReader reader = new JsonReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return DtGsonBuilder.create().fromJson(reader, DtDemoConfigStorage.class);
    }

}
