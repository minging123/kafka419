/*

package com.sunyard.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
@Slf4j
public class bairongTest {
    private final static String url="http://localhost:8040/bairong";
    private static RestTemplate restTemplate=new RestTemplate();
    @Test
    public void test(){
        JSONObject json=new JSONObject();
        json.put("name","渤海测试一");
        json.put("idCardNo","522324197612286576");
        json.put("phone","18641579553");
        json.put("barCode","11111111111111222");
        HttpHeaders httpHeaders=new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        httpHeaders.setContentType(type);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), httpHeaders);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url,formEntity,String.class);
        log.info("???????{}",responseEntity.getBody().toString());

    }

    @Test
    public void test2(){
        JSONObject json=new JSONObject();
        json.put("name","????");
        json.put("idCardNo","140502198811102233");
        json.put("phone","13986679999");
        json.put("barCode","2019071522");
        HttpHeaders httpHeaders=new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        httpHeaders.setContentType(type);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), httpHeaders);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url,formEntity,String.class);
        log.info("???????{}",responseEntity.getBody().toString());

    }
}

*/
