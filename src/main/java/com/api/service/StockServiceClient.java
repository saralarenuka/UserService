package com.api.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.model.StockDto;

@Service
public class StockServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    public List<StockDto> getStocksForUser(Integer userId) {
        String url = "http://localhost:8081/stock/user/" + userId; // StockService URL
        StockDto[] stocks = restTemplate.getForObject(url, StockDto[].class);
        return Arrays.asList(stocks);
    }
}
