package com.jordanalphonso.sample;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExchangeRatesController {

    private final Environment environment;

    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeRatesController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/latest")
    public ResponseEntity<RateResponse> latest() {

        String url = String.format("%s/latest?base=%s", environment.getProperty("rates.base-url"), environment.getProperty("rates.default-base"));
        System.out.println(url);

        return restTemplate.getForEntity(url, RateResponse.class);
    }

}
