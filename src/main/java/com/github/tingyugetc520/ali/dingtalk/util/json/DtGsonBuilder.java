package com.github.tingyugetc520.ali.dingtalk.util.json;

import com.github.tingyugetc520.ali.dingtalk.error.DtError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 */
public class DtGsonBuilder {
  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(DtError.class, new DtErrorAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
