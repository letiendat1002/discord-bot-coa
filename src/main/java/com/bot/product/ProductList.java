package com.bot.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductList {
    private static final List<Product> productList = new ArrayList<>();

    static {
        // Ore
        productList.add(new Product("Coal", "coal", 3000, "ore"));
        productList.add(new Product("Silver Ore", "sio", 4000, "ore"));
        productList.add(new Product("Gold Ore", "goo", 4000, "ore"));
        productList.add(new Product("Mythan Ore", "myo", 7000, "ore"));
        productList.add(new Product("Cobalt Ore", "coo", 10_000, "ore"));
        productList.add(new Product("Varaxium Ore", "vao", 14_000, "ore"));
        productList.add(new Product("Magic Ore", "mao", 5000, "ore"));
        productList.add(new Product("Salt", "salt", 3000, "ore"));
        productList.add(new Product("Pink Salt", "pis", 8000, "ore"));
        productList.add(new Product("Black Salt", "bls", 10_000, "ore"));

        // Bars
        productList.add(new Product("Silver Bars", "sib", 20_000, "bar"));
        productList.add(new Product("Gold Bars", "gob", 1_000_000, "bar"));
        productList.add(new Product("Mythan Bars", "myb", 90_000, "bar"));
        productList.add(new Product("Cobalt Bars", "cob", 100_000, "bar"));
        productList.add(new Product("Varaxium Bars", "vab", 140_000, "bar"));
        productList.add(new Product("Magic Bars", "mab", 60_000, "bar"));
        // Logs
        productList.add(new Product("Pine Logs", "pil", 2000, "log"));
        productList.add(new Product("Dead Logs", "del", 3000, "log"));
        productList.add(new Product("Birch Logs", "bil", 3000, "log"));
        productList.add(new Product("Apple Woods", "apw", 3000, "log"));
        productList.add(new Product("Willow Logs", "wil", 6000, "log"));
        productList.add(new Product("Oak Logs", "oak", 7000, "log"));
        productList.add(new Product("Chestnut Logs", "chl", 10_000, "log"));
        productList.add(new Product("Maple Logs", "map", 10_000, "log"));
        productList.add(new Product("Magic Logs", "mal", 2500, "log"));
        productList.add(new Product("Olive Logs", "oll", 10_000, "log"));
        productList.add(new Product("Palm Logs", "pal", 5000, "log"));
        productList.add(new Product("Pear Logs", "pel", 8000, "log"));
        productList.add(new Product("Lime Logs", "lil", 4000, "log"));

        // Relics
        productList.add(new Product("Accuracy Relics", "acr", 3000, "relic"));
        productList.add(new Product("Guardian Relics", "gur", 4000, "relic"));
        productList.add(new Product("Healing Relics", "her", 4000, "relic"));
        productList.add(new Product("Wealth Relics", "wer", 4000, "relic"));
        productList.add(new Product("Power Relics", "por", 6000, "relic"));
        productList.add(new Product("Nature Relics", "nar", 8000, "relic"));
        productList.add(new Product("Fire Relics", "fir", 14_000, "relic"));
        productList.add(new Product("Damage Relics", "dar", 8000, "relic"));
        productList.add(new Product("Leeching Relics", "ler", 6000, "relic"));
        productList.add(new Product("Ice Relics", "icr", 10_000, "relic"));
        productList.add(new Product("Experience Relics", "exr", 12_000, "relic"));
        productList.add(new Product("Cursed Relics", "cur", 4000, "relic"));
        productList.add(new Product("Efficiency Relics", "efr", 4000, "relic"));
        productList.add(new Product("Affliction Relics", "afr", 6000, "relic"));
        productList.add(new Product("Tablets", "tab", 10_000, "relic"));

        // Tailoring
        productList.add(new Product("Books", "book", 5000, "tailoring"));
        productList.add(new Product("Magical Essence", "mess", 2000, "tailoring"));

        // Miscellaneous
        productList.add(new Product("Sandstone", "san", 10_000, "misc"));
        productList.add(new Product("Magnetite", "mag", 150_000, "misc"));
    }

    public static List<Product> getAllProducts() {
        return productList;
    }

    public static Optional<Product> getProductByName(String productName) {
        return productList.stream()
                .filter(product -> product.name().toLowerCase().contains(productName.strip().toLowerCase()))
                .findFirst();
    }

    public static Optional<Product> getProductByCode(String productCode) {
        return productList.stream()
                .filter(product -> product.code().equalsIgnoreCase(productCode.strip().toLowerCase()))
                .findFirst();
    }
}
