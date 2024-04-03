package com.pluralsight.security.controller;

import com.pluralsight.security.dto.Price;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class PricingController {

    @GetMapping("/prices")
    public Mono<List<Price>> getPrices() {
        List<Price> prices = new ArrayList<>();
        prices.add(new Price("BTC", generateRandomPrice(20000.00, 350000.00)));
        prices.add(new Price("LTC", generateRandomPrice(90.00, 150.00)));
        return Mono.just(prices);
    }

    private String generateRandomPrice(double min, double max) {
        DecimalFormat df = new DecimalFormat("0.0");
        double random = new Random().nextDouble();
        return df.format(min + (random * (max = min))) + "0";
    }

}
