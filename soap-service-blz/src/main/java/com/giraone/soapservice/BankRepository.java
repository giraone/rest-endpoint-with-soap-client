package com.giraone.soapservice;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BankRepository {

    private static final Map<String, BankDetails> banks = new HashMap<>();

    @PostConstruct
    public void initData() {

        banks.put("10070024",
            new BankDetails(
                "Deutsche Bank Privat und Geschäftskunden F 700",
                "DEUTDEDBBER",
                "Berlin",
                "10883"
            ));
        banks.put("10089260",
            new BankDetails(
                "Dresdner Bank ITGK",
                "DRESDEFFI14",
                "Berlin",
                "10877"
            ));
    }

    public BankDetails findBank(String blz) {
        Assert.notNull(blz, "The blz must not be null");
        return banks.get(blz);
    }
}
