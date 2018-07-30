package com.example.demo.apple.service;

import com.example.demo.apple.utils.ApplePredicate;

import java.util.concurrent.CompletableFuture;

public interface AppleService {
    CompletableFuture<String> getApple();

    CompletableFuture<String> getAppleFunction(ApplePredicate predicate);
}
