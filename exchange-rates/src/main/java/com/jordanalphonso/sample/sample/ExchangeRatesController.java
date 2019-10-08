package com.jordanalphonso.sample.sample;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
public class ExchangeRatesController {

    private final Environment environment;

    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeRatesController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/today")
    public ResponseEntity<RateResponse> latest() {
        String url = String.format("%s/latest?base=%s",
                environment.getProperty("rates.base-url"),
                environment.getProperty("rates.default-base"));
        return restTemplate.getForEntity(url, RateResponse.class);
    }


    @GetMapping("/{date}")
    public ResponseEntity<RateResponse> byDate(@PathVariable String date) {
        String url = String.format("%s/%s?base=%s",
                environment.getProperty("rates.base-url"),
                date,
                environment.getProperty("rates.default-base"));
        return restTemplate.getForEntity(url, RateResponse.class);
    }

    @GetMapping("/{date}/{base}")
    public ResponseEntity<RateResponse> byDateAndBase(@PathVariable String date, @PathVariable String base) {
        String url = String.format("%s/%s?base=%s", environment.getProperty("rates.base-url"), date, base);
        return restTemplate.getForEntity(url, RateResponse.class);
    }

}
