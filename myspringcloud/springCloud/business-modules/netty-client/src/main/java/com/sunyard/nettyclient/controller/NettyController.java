package com.sunyard.nettyclient.controller;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/")
@Api(tags = "netty-server")
public class NettyController {
    @Value("${bairong.strategy_id_package}")
    private String strategyIdPackage;
    @Value("${bairong.strategy_id_money}")
    private String confIdsMoney;

}