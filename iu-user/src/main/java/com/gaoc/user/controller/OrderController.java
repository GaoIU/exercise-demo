package com.gaoc.user.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.gaoc.common.constant.IndexConstant;
import com.gaoc.core.model.R;
import com.gaoc.user.constant.UserConstant;
import com.gaoc.user.es.model.SendSmsCodeVO;
import com.gaoc.user.rabbit.UserRabbitProcessor;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/order")
@RequiredArgsConstructor
@RestController
public class OrderController {

	private final UserRabbitProcessor userRabbitProcessor;

	private final RestHighLevelClient restHighLevelClient;

	@PostMapping
	public R placeOrder(String productName, Long productId, Integer payType, BigDecimal amount) {
		log.info("商品详情开始下单，商品名称：{}，商品ID：{}，支付方式：{}，商品金额：{}", productName, productId, payType, amount);

		Dict dict = new Dict(3);
		dict.put("code", UserConstant.ORDER_EXPIRE_TIP);
		dict.put("status", UserConstant.TO_PAY);
		dict.put("message", "订单创建完成，设置订单到期前10分钟倒计时提醒");

		log.info("==================设置订单超时前提示队列=====================");
		Message<Dict> message = MessageBuilder.withPayload(dict).setHeader(AmqpHeaders.DELAY, 30000).build();
		userRabbitProcessor.delayOutput().send(message);

		log.info("==================设置订单超时自动关闭队列=====================");
		dict.replace("code", UserConstant.ORDER_CLOSE);
		dict.replace("message", "订单超时，自动关闭");
		Message<Dict> orderCloseMessage = MessageBuilder.withPayload(dict).setHeader(AmqpHeaders.DELAY, 60000).build();
		userRabbitProcessor.delayOutput().send(orderCloseMessage);

		return R.ok();
	}

	@GetMapping
	public R search(String keyword, @RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size) {
		SearchRequest searchRequest = new SearchRequest(IndexConstant.SEND_SMS_CODE);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		// 分页
		sourceBuilder.from((current - 1) * size);
		sourceBuilder.size(size);
		sourceBuilder.sort("createTime", SortOrder.DESC);
		// 查询条件
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		if (StrUtil.isNotBlank(keyword)) {
			keyword = QueryParser.escape(keyword).replace(" ", "");
			BoolQueryBuilder query = QueryBuilders.boolQuery();
//			query.should(QueryBuilders.wildcardQuery("mobile", "*" + keyword + "*"));
//			query.should(QueryBuilders.wildcardQuery("content", "*" + keyword + "*"));
//			query.should(QueryBuilders.fuzzyQuery("mobile", keyword));
			query.should(QueryBuilders.matchQuery("mobile", keyword));

			boolQuery.must(query);
		}

		sourceBuilder.query(boolQuery);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("===========ES查询异常===========");
			return R.fail("查询失败");
		}

		SearchHit[] hits = searchResponse.getHits().getHits();
		List<SendSmsCodeVO> list = Stream.of(hits)
				.map(h -> JSON.parseObject(h.getSourceAsString(), SendSmsCodeVO.class)).collect(Collectors.toList());
		return R.ok(list);
	}

}
