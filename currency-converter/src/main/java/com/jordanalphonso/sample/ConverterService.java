package com.jordanalphonso.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConverterService {

    private final ExchangeRatesClient exchangeRatesClient;

    @Autowired
    public ConverterService(ExchangeRatesClient exchangeRatesClient) {
        this.exchangeRatesClient = exchangeRatesClient;
    }

    public Map<String, ConvertedAmount> getConversions(String base, String date, BigDecimal amount) {
        RateResponse rates = exchangeRatesClient.byDateAndBase(date, base);

        Map<String, ConvertedAmount> amounts = new HashMap<>();

        rates.getRates().forEach((currency, rate) -> {
            ConvertedAmount amt = new ConvertedAmount();
            amt.setFromCurrency(base);
            amt.setFromAmount(amount.setScale(2, RoundingMode.CEILING));
            amt.setToCurrency(currency);
            amt.setToAmount(amount.multiply(BigDecimal.valueOf(rate)).setScale(2, RoundingMode.CEILING));
            amounts.put(currency, amt);
        });

        return amounts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o1, LinkedHashMap::new));
    }

}
