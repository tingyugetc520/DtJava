package com.github.tingyugetc520.ali.dingtalk.message;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.constant.DtConstant;
import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeException;
import com.github.tingyugetc520.ali.dingtalk.message.processor.DtCheckUrlMessageHandler;
import com.github.tingyugetc520.ali.dingtalk.message.processor.DtLogExceptionHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * <pre>
 * 消息路由器，通过代码化的配置，把来自钉钉的消息交给handler处理
 *
 * 说明：
 * 1. 配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
 * 2. 默认情况下消息只会被处理一次，除非使用 {@link DtMessageRouterRule#next()}
 * 3. 规则的结束必须用{@link DtMessageRouterRule#end()}或者{@link DtMessageRouterRule#next()}，否则不会生效
 *
 * 使用方法：
 * DtMessageRouter router = new DtMessageRouter();
 * router
 *   .rule()
 *       .eventType("eventType")
 *       .interceptor(interceptor, ...).handler(handler, ...)
 *   .end()
 *   .rule()
 *       // 另外一个匹配规则
 *   .end()
 * ;
 *
 * // 将DtMessage交给消息路由器
 * router.route(message);
 *
 * </pre>
 */
@Slf4j
public class DtMessageRouter {
    private static final int DEFAULT_THREAD_POOL_SIZE = 100;
    private final List<DtMessageRouterRule> rules = new ArrayList<>();

    private DtService dtService;
    private ExecutorService executorService;
    private DtErrorExceptionHandler exceptionHandler;

    /**
     * 构造方法.
     */
    public DtMessageRouter() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("dtMessageRouter-pool-%d").build();
        this.executorService = new ThreadPoolExecutor(DEFAULT_THREAD_POOL_SIZE, DEFAULT_THREAD_POOL_SIZE,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), namedThreadFactory);

        this.exceptionHandler = new DtLogExceptionHandler();
        this.rules.add(new DtMessageRouterRule(this).async(false).eventType(DtConstant.EventType.CHECK_URL).handler(new DtCheckUrlMessageHandler()));
    }

    /**
     * 构造方法.
     */
    public DtMessageRouter(DtService dtService) {
        this();
        this.dtService = dtService;
    }

    /**
     * <pre>
     * 设置自定义的 {@link ExecutorService}
     * 如果不调用该方法，默认使用 Executors.newFixedThreadPool(100)
     * </pre>
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * <pre>
     * 设置自定义的{@link DtErrorExceptionHandler}
     * 如果不调用该方法，默认使用 {@link DtLogExceptionHandler}
     * </pre>
     */
    public void setExceptionHandler(DtErrorExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    List<DtMessageRouterRule> getRules() {
        return this.rules;
    }

    /**
     * 开始一个新的Route规则.
     */
    public DtMessageRouterRule rule() {
        return new DtMessageRouterRule(this);
    }

    /**
     * 处理消息.
     */
    protected Boolean doRoute(final DtService dtService, final DtEventMessage message, final Map<String, Object> context) {
        // 消息为空，则说明回调有问题
        if (Objects.isNull(message)) {
            throw new DtRuntimeException("回调消息为空");
        }

        final List<DtMessageRouterRule> matchRules = new ArrayList<>();
        // 收集匹配的规则
        for (final DtMessageRouterRule rule : this.rules) {
            if (rule.test(message)) {
                matchRules.add(rule);
                if (!rule.isReEnter()) {
                    break;
                }
            }
        }

        // 没有处理器
        if (matchRules.size() == 0) {
            return null;
        }

        // todo 当存在多条匹配规则时，应该判断返回全部规则是否都正确处理
        Boolean res = null;
        final List<Future<Boolean>> futures = new ArrayList<>();
        for (final DtMessageRouterRule rule : matchRules) {
            // 返回最后一个非异步的rule的执行结果
            if (rule.isAsync()) {
                futures.add(
                        this.executorService.submit(() ->
                                rule.service(message, context, dtService, DtMessageRouter.this.exceptionHandler)
                        )
                );
            } else {
                res = rule.service(message, context, dtService, this.exceptionHandler);
            }
        }

        if (futures.size() > 0) {
            this.executorService.submit(() -> {
                for (Future<Boolean> future : futures) {
                    try {
                        future.get();
                    } catch (InterruptedException e) {
                        log.error("Error happened when wait task finish", e);
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e) {
                        log.error("Error happened when wait task finish", e);
                    }
                }
            });
        }
        return res;
    }

    /**
     * 处理消息.
     */
    public Boolean route(final DtService dtService, final DtEventMessage message, final Map<String, Object> context) {
        return this.doRoute(dtService, message, context);
    }

    /**
     * 处理消息.
     */
    public Boolean route(final DtEventMessage message) {
        return this.route(this.dtService, message, new HashMap<>(2));
    }

    /**
     * 指定由dtService来处理消息
     */
    public Boolean route(final DtService dtService, final DtEventMessage message) {
        return this.route(dtService, message, new HashMap<>(2));
    }

}
