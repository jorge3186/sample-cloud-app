package com.jordanalphonso.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class CurrencyConverterController {

    private final ConverterService converterService;

    public CurrencyConverterController(ConverterService converterService) {
        this.converterService = converterService;
    }

    @GetMapping("/all/{amount}/{base}")
    public ResponseEntity<Map<String, ConvertedAmount>> all(@PathVariable String amount, @PathVariable String base) {
        BigDecimal fromAmt = new BigDecimal(amount.replace(",", ""));
        return ResponseEntity.ok(converterService.getConversions(base, LocalDate.now().toString(), fromAmt));
    }

    @GetMapping("/all/{amount}/{base}/{date")
    public ResponseEntity<Map<String, ConvertedAmount>> allByDate(@PathVariable String amount,
                                                                  @PathVariable String base, @PathVariable String date) {
        BigDecimal fromAmt = new BigDecimal(amount.replace(",", ""));
        return ResponseEntity.ok(converterService.getConversions(base, date, fromAmt));
    }

    @GetMapping("/specific/{to}/{amount}/{base}")
    public ResponseEntity<ConvertedAmount> specific(@PathVariable String to, @PathVariable String amount, @PathVariable String base) {
        BigDecimal fromAmt = new BigDecimal(amount.replace(",", ""));
        Map<String, ConvertedAmount> map = converterService.getConversions(base, LocalDate.now().toString(), fromAmt);
        return ResponseEntity.ok(map.get(to));
    }

    @GetMapping("/specific/{to}/{amount}/{base}/{date}")
    public ResponseEntity<ConvertedAmount> specificFromDate(@PathVariable String to, @PathVariable String amount,
                                                            @PathVariable String base, @PathVariable String date) {
        BigDecimal fromAmt = new BigDecimal(amount.replace(",", ""));
        Map<String, ConvertedAmount> map = converterService.getConversions(base, date, fromAmt);
        return ResponseEntity.ok(map.get(to));
    }

}
