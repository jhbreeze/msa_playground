package com.sparta.msa_exam.gateway.filter;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomHeaderFilter implements GlobalFilter, Ordered {

	private final LoadBalancerClient loadBalancerClient;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		// Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR); // 현재 요청이 어떤 라우트를 통해 처리되고 있는지 정보 추출
		//
		// if (route != null) {
		// 	String serviceId = route.getUri().getHost();
		// 	try {
		// 		ServiceInstance instance = loadBalancerClient.choose(serviceId);
		// 		if (instance != null) {
		// 			exchange.getResponse().getHeaders().add("Server-Port", String.valueOf(instance.getPort()));
		// 		}
		// 	} catch (Exception e) {
		// 		log.error("Error choosing service instance", e);
		// 	}
		// }

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE; // 가장 낮은 순위
	}
}
