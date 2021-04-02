package com.github.tingyugetc520.ali.dingtalk.util.json;

import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUser;
import com.github.tingyugetc520.ali.dingtalk.error.DtError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 *
 */
public class DtGsonBuilder {
    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(Date.class, new DtDateAdapter());
        INSTANCE.registerTypeAdapter(DtError.class, new DtErrorAdapter());
        INSTANCE.registerTypeAdapter(DtUser.class, new DtUserAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }

}
