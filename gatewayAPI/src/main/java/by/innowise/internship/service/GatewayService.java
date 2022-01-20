package by.innowise.internship.service;

import by.innowise.internship.exception.MyErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GatewayService<I> {

    private RestTemplate restTemplate;

    @Autowired
    public GatewayService(RestTemplateBuilder restTemplateBuilder) {

        RestTemplate restTemplate = restTemplateBuilder
                .errorHandler(new MyErrorHandler())
                .build();
    }



}
