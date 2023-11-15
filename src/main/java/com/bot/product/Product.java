package com.bot.product;

import com.bot.util.CustomNumberFormat;

public record Product(String name, String code, int cost, String type) {
    @Override
    public String toString() {
        return name + '\t' +
                code + '\t' +
                CustomNumberFormat.shortenValue(cost) +
                "\t" + type;
    }
}
