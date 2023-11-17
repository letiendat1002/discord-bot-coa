package com.bot.product;

import java.util.*;

public class ProductList {
    private static final Set<String> PRODUCT_CODES = new HashSet<>();
    private static List<Product> productList;

    public static List<Product> getAllProducts() {
        if (productList == null) {
            productList = new ArrayList<>();

            // Ore
            addProduct(productList, "Coal", "clo", 4000, ProductType.ORE.name());
            addProduct(productList, "Silver Ore", "sro", 4000, ProductType.ORE.name());
            addProduct(productList, "Gold Ore", "gdo", 4000, ProductType.ORE.name());
            addProduct(productList, "Mythan Ore", "mno", 7000, ProductType.ORE.name());
            addProduct(productList, "Cobalt Ore", "cto", 10_000, ProductType.ORE.name());
            addProduct(productList, "Varaxium Ore", "vmo", 14_000, ProductType.ORE.name());
            addProduct(productList, "Magic Ore", "mco", 5000, ProductType.ORE.name());

            // Salt
            addProduct(productList, "Salt", "sts", 3000, ProductType.SALT.name());
            addProduct(productList, "Pink Salt", "pks", 8000, ProductType.SALT.name());
            addProduct(productList, "Black Salt", "bks", 10_000, ProductType.SALT.name());

            // Bars
            addProduct(productList, "Silver Bars", "srb", 20_000, ProductType.BAR.name());
            addProduct(productList, "Gold Bars", "gdb", 1_000_000, ProductType.BAR.name());
            addProduct(productList, "Mythan Bars", "mnb", 90_000, ProductType.BAR.name());
            addProduct(productList, "Cobalt Bars", "ctb", 100_000, ProductType.BAR.name());
            addProduct(productList, "Varaxium Bars", "vmb", 140_000, ProductType.BAR.name());
            addProduct(productList, "Magic Bars", "mcb", 60_000, ProductType.BAR.name());

            // Logs
            addProduct(productList, "Pine Logs", "pel", 2000, ProductType.LOG.name());
            addProduct(productList, "Dead Logs", "ddl", 3000, ProductType.LOG.name());
            addProduct(productList, "Birch Logs", "bhl", 3000, ProductType.LOG.name());
            addProduct(productList, "Applewood", "adl", 3000, ProductType.LOG.name());
            addProduct(productList, "Willow Logs", "wwl", 5500, ProductType.LOG.name());
            addProduct(productList, "Oak Logs", "okl", 7000, ProductType.LOG.name());
            addProduct(productList, "Chestnut Logs", "ctl", 10_000, ProductType.LOG.name());
            addProduct(productList, "Maple Logs", "mel", 10_000, ProductType.LOG.name());
            addProduct(productList, "Magic Logs", "mcl", 2500, ProductType.LOG.name());
            addProduct(productList, "Olive Logs", "oel", 10_000, ProductType.LOG.name());
            addProduct(productList, "Palm Logs", "pml", 5000, ProductType.LOG.name());
            addProduct(productList, "Pear Logs", "prl", 8000, ProductType.LOG.name());
            addProduct(productList, "Lime Logs", "lel", 4000, ProductType.LOG.name());

            // Relics
            addProduct(productList, "Accuracy Relics", "ayr", 4000, ProductType.RELIC.name());
            addProduct(productList, "Guardian Relics", "gnr", 4000, ProductType.RELIC.name());
            addProduct(productList, "Healing Relics", "hgr", 4000, ProductType.RELIC.name());
            addProduct(productList, "Wealth Relics", "whr", 4000, ProductType.RELIC.name());
            addProduct(productList, "Power Relics", "prr", 7000, ProductType.RELIC.name());
            addProduct(productList, "Nature Relics", "ner", 8000, ProductType.RELIC.name());
            addProduct(productList, "Fire Relics", "fer", 14_000, ProductType.RELIC.name());
            addProduct(productList, "Damage Relics", "der", 8000, ProductType.RELIC.name());
            addProduct(productList, "Leeching Relics", "lgr", 6000, ProductType.RELIC.name());
            addProduct(productList, "Ice Relics", "ier", 10_000, ProductType.RELIC.name());
            addProduct(productList, "Experience Relics", "eer", 13_000, ProductType.RELIC.name());
            addProduct(productList, "Cursed Relics", "cdr", 4000, ProductType.RELIC.name());
            addProduct(productList, "Efficiency Relics", "eyr", 4000, ProductType.RELIC.name());
            addProduct(productList, "Affliction Relics", "anr", 6000, ProductType.RELIC.name());
            addProduct(productList, "Tablets", "ttr", 10_000, ProductType.RELIC.name());

            // Tailoring
            addProduct(productList, "Books", "bkt", 5000, ProductType.TAILORING.name());
            addProduct(productList, "Magical Essence", "met", 2000, ProductType.TAILORING.name());

            // Bait
            addProduct(productList, "Earth/Ice/Corpse/Toxic/Sandworm", "wob", 500, ProductType.BAIT.name());
            addProduct(productList, "Beetles", "beb", 2500, ProductType.BAIT.name());
            addProduct(productList, "Grasshoppers", "grb", 12_000, ProductType.BAIT.name());
            addProduct(productList, "Wasp", "wpb", 2500, ProductType.BAIT.name());
            addProduct(productList, "Scallops", "spb", 10_000, ProductType.BAIT.name());

            // Fish
            addProduct(productList, "Eel", "elf", 4000, ProductType.FISH.name());
            addProduct(productList, "Anglerfish", "ahf", 5000, ProductType.FISH.name());
            addProduct(productList, "Trout", "ttf", 9000, ProductType.FISH.name());
            addProduct(productList, "Bass", "bsf", 39_000, ProductType.FISH.name());

            // Cooking
            addProduct(productList, "Cooked Eel", "elc", 3500, ProductType.COOKED_FISH.name());
            addProduct(productList, "Cooked Anglerfish", "ahc", 4000, ProductType.COOKED_FISH.name());
            addProduct(productList, "Cooked Trout", "ttc", 6000, ProductType.COOKED_FISH.name());
            addProduct(productList, "Cooked Bass", "bsc", 12_000, ProductType.COOKED_FISH.name());

            // Miscellaneous
            addProduct(productList, "Sandstone", "sdm", 10_000, ProductType.MISC.name());
            addProduct(productList, "Magnetite", "mem", 180_000, ProductType.MISC.name());
        }
        return productList;
    }

    private static void addProduct(List<Product> productList, String name, String code, int cost, String type) {
        if (!isValidCode(code)) {
            throw new IllegalArgumentException("Invalid product code: " + code);
        }

        productList.add(new Product(name, code, cost, type));
        PRODUCT_CODES.add(code);
    }

    private static boolean isValidCode(String code) {
        return !PRODUCT_CODES.contains(code);
    }

    public static Optional<Product> getProductByName(String productName) {
        if (productName.trim().isEmpty()) {
            return Optional.empty();
        }
        var productList = getAllProducts();
        return productList.stream()
                .filter(product -> product.name().toLowerCase().contains(productName.strip().toLowerCase()))
                .findFirst();
    }

    public static Optional<Product> getProductByCode(String productCode) {
        if (productCode.trim().isEmpty()) {
            return Optional.empty();
        }
        var productList = getAllProducts();
        return productList.stream()
                .filter(product -> product.code().equals(productCode.strip().toLowerCase()))
                .findFirst();
    }

    private enum ProductType {
        ORE, SALT, BAR, LOG, RELIC, TAILORING, BAIT, FISH, COOKED_FISH, MISC
    }
}
