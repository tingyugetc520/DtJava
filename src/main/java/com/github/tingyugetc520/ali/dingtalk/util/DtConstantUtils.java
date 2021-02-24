package com.github.tingyugetc520.ali.dingtalk.util;

import com.google.common.collect.Lists;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DtConstantUtils {

    @SuppressWarnings("unchecked")
    public static <T, E> List<E> getEventTypeGroup(Class<T> clazz) {
        return Lists.newArrayList(clazz.getDeclaredFields())
                .stream()
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field -> {
                    try {
                        return (E) field.get(null);
                    } catch (IllegalAccessException|IllegalArgumentException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
