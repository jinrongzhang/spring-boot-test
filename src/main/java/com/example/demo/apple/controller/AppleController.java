package com.example.demo.apple.controller;

import com.example.demo.apple.service.AppleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/hello")
public class AppleController {

    private static Logger logger = LoggerFactory.getLogger(AppleController.class);

    @Autowired
    private AppleService appleService;


    @RequestMapping(value = "/apple", method = RequestMethod.GET)
    @ResponseBody
    public CompletableFuture<String> test0() {
        logger.info("controller ");
        return appleService.getApple();
    }

    @RequestMapping(value = "/redApple", method = RequestMethod.GET)
    @ResponseBody
    public CompletableFuture<String> test1() {
        return appleService.getAppleFunction(x -> "red".equals(x.getColour()));
    }

    @RequestMapping(value = "/greenApple", method = RequestMethod.GET)
    @ResponseBody
    public CompletableFuture<String> test2() {
        return appleService.getAppleFunction(x -> "red".equals(x.getColour()));
    }

    @RequestMapping(value = "/heavyApple", method = RequestMethod.GET)
    @ResponseBody
    public CompletableFuture<String> test3() {
        return appleService.getAppleFunction(x -> x.getWeight() > 3);
    }
}
