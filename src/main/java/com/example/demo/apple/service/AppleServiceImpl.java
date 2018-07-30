package com.example.demo.apple.service;

import com.example.demo.apple.controller.AppleController;
import com.example.demo.apple.modle.Apple;
import com.example.demo.apple.utils.ApplePredicate;
import com.example.demo.apple.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AppleServiceImpl implements AppleService {

    private static Logger logger = LoggerFactory.getLogger(AppleController.class);

    private static String json = "{\"apples\":[{\"colour\":\"red\",\"weight\":3.2,\"name\":\"tom\"},{\"colour\":\"red\",\"weight\":2.6,\"name\":\"zhangsan\"},{\"colour\":\"green\",\"weight\":4.2,\"name\":\"lisi\"}]}";

    @Override
    public CompletableFuture<String> getApple() {

        logger.info("service");

        List<Apple> apples = JsonUtil.toList(json, new TypeReference<List<Apple>>() {
        }, x -> x.get("apples"));

        return CompletableFuture.supplyAsync(() -> {

            logger.info("service inside");

            //使用stream 实现方式
            String s = apples.stream()
                    .filter(x -> "red".equals(x.getColour()))
                    .max(Comparator.comparing(Apple::getWeight))
                    .map(x -> JsonUtil.toJson(x).orElse(""))
                    .orElse("");


            //使用外部遍历方式
            //step1:筛选红苹果
            List<Apple> apples1 = new ArrayList<>();
            for (Apple apple : apples) {
                if ("red".equals(apple.getColour())) {
                    apples1.add(apple);
                }
            }
            //step2:筛选最重的苹果
            Apple apple2 = apples1.get(0);
            for (Apple apple : apples) {
                if (apple.getWeight() > apple2.getWeight()) {
                    apple2 = apple;
                }
            }
            String s1 = JsonUtil.toJson(apple2).orElse("");

            return s1;
        });

    }

    @Override
    public CompletableFuture<String> getAppleFunction(ApplePredicate predicate) {
        List<Apple> apples = JsonUtil.toList(json, new TypeReference<List<Apple>>() {
        }, x -> x.get("apples"));

        List<Apple> collect = apples.stream()
                .filter(predicate::test)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(JsonUtil.toJson(collect).toString());
    }
}
