package com.jordanalphonso.sample;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("sample-exchange-rates")
public interface ExchangeRatesClient {

    @RequestMapping(value = "/{date}/{base}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    RateResponse byDateAndBase(@PathVariable String date, @PathVariable String base);

}
