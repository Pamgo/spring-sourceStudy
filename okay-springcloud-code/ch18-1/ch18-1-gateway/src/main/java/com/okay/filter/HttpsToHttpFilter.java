package com.okay.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 在LoadBalancerClientFilter执行之前将Https修改为http
 * Created by OKali on 2019/3/2.
 */
@Component
public class HttpsToHttpFilter implements GlobalFilter, Ordered{

    private static final int HTTPS_TO_HTTP_ORDER = 10099;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI originallUri = exchange.getRequest().getURI();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        String forwardedUri = request.getURI().toString();
        if (forwardedUri != null && forwardedUri.startsWith("https")) {
            try {
                URI mutateUri = new URI("http",
                        originallUri.getUserInfo(),
                        originallUri.getHost(),
                        originallUri.getPort(),
                        originallUri.getPath(),
                        originallUri.getQuery(),
                        originallUri.getFragment());
                mutate.uri(mutateUri);
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build());
    }

    /**
     * 由于 LoadBalancerClientFilter的order是10100
     * 要在 LoadBalancerClientFilter执行之前将Https修改为Http,需要设置order为10099
     * @return
     */
    @Override
    public int getOrder() {
        return HTTPS_TO_HTTP_ORDER;
    }
}
