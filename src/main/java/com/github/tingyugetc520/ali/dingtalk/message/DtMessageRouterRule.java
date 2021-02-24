package com.github.tingyugetc520.ali.dingtalk.message;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * The message router rule
 */
@Data
public class DtMessageRouterRule {
    private final DtMessageRouter routerBuilder;

    private boolean async = true;

    private String corpId;

    private String eventType;

    private String eventTypeRegex;

    private DtMessageMatcher matcher;

    private boolean reEnter = false;


    private List<DtMessageHandler> handlers = new ArrayList<>();

    private List<DtMessageInterceptor> interceptors = new ArrayList<>();

    protected DtMessageRouterRule(DtMessageRouter routerBuilder) {
        this.routerBuilder = routerBuilder;
    }

    /**
     * 设置是否异步执行，默认是true
     *
     * @param async the async
     * @return the message router rule
     */
    public DtMessageRouterRule async(boolean async) {
        this.async = async;
        return this;
    }

    /**
     * 如果corpId匹配
     *
     * @param corpId the corp id
     * @return the message router rule
     */
    public DtMessageRouterRule corpId(String corpId) {
        this.corpId = corpId;
        return this;
    }

    /**
     * 如果eventType等于某值
     *
     * @param eventType the event type
     * @return the message router rule
     */
    public DtMessageRouterRule eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    /**
     * 如果eventKey匹配该正则表达式
     *
     * @param regex the regex
     * @return the message router rule
     */
    public DtMessageRouterRule eventTypeRegex(String regex) {
        this.eventTypeRegex = regex;
        return this;
    }

    /**
     * 如果消息匹配某个matcher，用在用户需要自定义更复杂的匹配规则的时候
     *
     * @param matcher the matcher
     * @return the message router rule
     */
    public DtMessageRouterRule matcher(DtMessageMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    /**
     * 设置消息拦截器
     *
     * @param interceptor the interceptor
     * @return the message router rule
     */
    public DtMessageRouterRule interceptor(DtMessageInterceptor interceptor) {
        return interceptor(interceptor, (DtMessageInterceptor[]) null);
    }

    /**
     * 设置消息拦截器
     *
     * @param interceptor       the interceptor
     * @param otherInterceptors the other interceptors
     * @return the message router rule
     */
    public DtMessageRouterRule interceptor(DtMessageInterceptor interceptor, DtMessageInterceptor... otherInterceptors) {
        this.interceptors.add(interceptor);
        if (otherInterceptors != null && otherInterceptors.length > 0) {
            Collections.addAll(this.interceptors, otherInterceptors);
        }
        return this;
    }

    /**
     * 设置消息处理器
     *
     * @param handler the handler
     * @return the message router rule
     */
    public DtMessageRouterRule handler(DtMessageHandler handler) {
        return handler(handler, (DtMessageHandler[]) null);
    }

    /**
     * 设置消息处理器
     *
     * @param handler       the handler
     * @param otherHandlers the other handlers
     * @return the message router rule
     */
    public DtMessageRouterRule handler(DtMessageHandler handler, DtMessageHandler... otherHandlers) {
        this.handlers.add(handler);
        if (otherHandlers != null && otherHandlers.length > 0) {
            Collections.addAll(this.handlers, otherHandlers);
        }
        return this;
    }

    /**
     * 规则结束，代表如果一个消息匹配该规则，那么它将不再会进入其他规则
     *
     * @return the message router
     */
    public DtMessageRouter end() {
        this.routerBuilder.getRules().add(this);
        return this.routerBuilder;
    }

    /**
     * 规则结束，但是消息还会进入其他规则
     *
     * @return the message router
     */
    public DtMessageRouter next() {
        this.reEnter = true;
        return end();
    }

    /**
     * Test boolean.
     *
     * @param message the ding talk event message
     * @return the boolean
     */
    protected boolean test(DtEventMessage message) {
        return (this.corpId == null || this.corpId.equals(message.getCorpId()))
                &&
                (this.eventType == null || this.eventType.equalsIgnoreCase(message.getEventType()))
                &&
                (this.eventTypeRegex == null || Pattern.matches(this.eventTypeRegex, StringUtils.trimToEmpty(message.getEventType())))
                &&
                (this.matcher == null || this.matcher.match(message))
                ;
    }

    /**
     * 处理推送过来的消息
     *
     * @param message        the dt message
     * @param context        the context
     * @param dtService      the service
     * @param exceptionHandler the exception handler
     * @return true 代表消息回调成功，false 代表消息回调失败
     */
    protected Boolean service(DtEventMessage message,
                                        Map<String, Object> context,
                                        DtService dtService,
                                        DtErrorExceptionHandler exceptionHandler) {

        if (context == null) {
            context = new HashMap<>();
        }

        try {
            // 如果拦截器不通过
            for (DtMessageInterceptor interceptor : this.interceptors) {
                if (!interceptor.intercept(message, context, dtService)) {
                    return false;
                }
            }

            // 交给handler处理
            Boolean res = null;
            for (DtMessageHandler handler : this.handlers) {
                // 返回最后handler的结果
                res = handler.handle(message, context, dtService);
            }
            return res;
        } catch (DtErrorException e) {
            exceptionHandler.handle(e);
            return false;
        }
    }


}
