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
            addProduct(productList, "Iron Ore", "ino", 4_000, ProductType.ORE.name());
            addProduct(productList, "Crimsteel Ore", "cro", 4_000, ProductType.ORE.name());
            addProduct(productList, "Silver Ore", "sro", 4000, ProductType.ORE.name());
            addProduct(productList, "Gold Ore", "gdo", 4000, ProductType.ORE.name());
            addProduct(productList, "Mythan Ore", "mno", 7000, ProductType.ORE.name());
            addProduct(productList, "Cobalt Ore", "cto", 10_000, ProductType.ORE.name());
            addProduct(productList, "Varaxium Ore", "vmo", 13_000, ProductType.ORE.name());
            addProduct(productList, "Magic Ore", "mco", 5000, ProductType.ORE.name());

            // Salt
            addProduct(productList, "Salt", "sts", 4000, ProductType.SALT.name());
            addProduct(productList, "Pink Salt", "pks", 9000, ProductType.SALT.name());
            addProduct(productList, "Black Salt", "bks", 10_000, ProductType.SALT.name());

            // Bars
            addProduct(productList, "Crimsteel Bars", "clb", 20_000, ProductType.BAR.name());
            addProduct(productList, "Silver Bars", "srb", 20_000, ProductType.BAR.name());
            addProduct(productList, "Gold Bars", "gdb", 1_000_000, ProductType.BAR.name());
            addProduct(productList, "Mythan Bars", "mnb", 90_000, ProductType.BAR.name());
            addProduct(productList, "Cobalt Bars", "ctb", 100_000, ProductType.BAR.name());
            addProduct(productList, "Varaxium Bars", "vmb", 140_000, ProductType.BAR.name());
            addProduct(productList, "Magic Bars", "mcb", 60_000, ProductType.BAR.name());

            // Logs
            addProduct(productList, "Pine Logs", "pel", 2000, ProductType.LOGS.name());
            addProduct(productList, "Dead Logs", "ddl", 3000, ProductType.LOGS.name());
            addProduct(productList, "Birch Logs", "bhl", 3000, ProductType.LOGS.name());
            addProduct(productList, "Applewood", "adl", 3000, ProductType.LOGS.name());
            addProduct(productList, "Willow Logs", "wwl", 5500, ProductType.LOGS.name());
            addProduct(productList, "Oak Logs", "okl", 7000, ProductType.LOGS.name());
            addProduct(productList, "Chestnut Logs", "ctl", 10_000, ProductType.LOGS.name());
            addProduct(productList, "Maple Logs", "mel", 10_000, ProductType.LOGS.name());
            addProduct(productList, "Olive Logs", "oel", 10_000, ProductType.LOGS.name());
            addProduct(productList, "Magic Logs", "mcl", 2500, ProductType.LOGS.name());
            addProduct(productList, "Palm Logs", "pml", 5000, ProductType.LOGS.name());
            addProduct(productList, "Pear Logs", "prl", 9000, ProductType.LOGS.name());
            addProduct(productList, "Lime Logs", "lel", 4000, ProductType.LOGS.name());

            // Relics
            addProduct(productList, "Accuracy Relics", "ayr", 4000, ProductType.RELICS.name());
            addProduct(productList, "Guardian Relics", "gnr", 4000, ProductType.RELICS.name());
            addProduct(productList, "Healing Relics", "hgr", 4000, ProductType.RELICS.name());
            addProduct(productList, "Wealth Relics", "whr", 4000, ProductType.RELICS.name());
            addProduct(productList, "Power Relics", "prr", 7000, ProductType.RELICS.name());
            addProduct(productList, "Nature Relics", "ner", 8000, ProductType.RELICS.name());
            addProduct(productList, "Fire Relics", "fer", 13_000, ProductType.RELICS.name());
            addProduct(productList, "Damage Relics", "der", 8000, ProductType.RELICS.name());
            addProduct(productList, "Leeching Relics", "lgr", 7000, ProductType.RELICS.name());
            addProduct(productList, "Ice Relics", "ier", 10_000, ProductType.RELICS.name());
            addProduct(productList, "Experience Relics", "eer", 12_000, ProductType.RELICS.name());
            addProduct(productList, "Cursed Relics", "cdr", 6000, ProductType.RELICS.name());
            addProduct(productList, "Efficiency Relics", "eyr", 4000, ProductType.RELICS.name());
            addProduct(productList, "Affliction Relics", "anr", 6000, ProductType.RELICS.name());
            addProduct(productList, "Tablets", "ttr", 10_000, ProductType.RELICS.name());

            // Tailoring
            addProduct(productList, "Books", "bkt", 5000, ProductType.TAILORING.name());
            addProduct(productList, "Magic Essence", "met", 2000, ProductType.TAILORING.name());
            addProduct(productList, "Leathers", "lrt", 2500, ProductType.TAILORING.name());

            // Bait
            addProduct(productList, "Earthworm", "emb", 1000, ProductType.BAITS.name());
            addProduct(productList, "Iceworm", "imb", 1000, ProductType.BAITS.name());
            addProduct(productList, "Corpseworm", "cmb", 1000, ProductType.BAITS.name());
            addProduct(productList, "Toxic Worm", "tmb", 1000, ProductType.BAITS.name());
            addProduct(productList, "Sandworm", "smb", 1000, ProductType.BAITS.name());
            addProduct(productList, "Beetles", "beb", 2500, ProductType.BAITS.name());
            addProduct(productList, "Grasshoppers", "grb", 12_000, ProductType.BAITS.name());
            addProduct(productList, "Wasp", "wpb", 2500, ProductType.BAITS.name());
            addProduct(productList, "Scallops", "spb", 10_000, ProductType.BAITS.name());

            // Fish
            addProduct(productList, "Raw Eel", "elf", 4000, ProductType.FISHING.name());
            addProduct(productList, "Raw Anglerfish", "ahf", 6000, ProductType.FISHING.name());
            addProduct(productList, "Raw Trout", "ttf", 8000, ProductType.FISHING.name());
            addProduct(productList, "Raw Bass", "bsf", 39_000, ProductType.FISHING.name());
            addProduct(productList, "Raw Tuna", "taf", 6000, ProductType.FISHING.name());

            // Cooking
            addProduct(productList, "Cooked Eel", "elc", 3500, ProductType.COOKED_FISH.name());
            addProduct(productList, "Cooked Anglerfish", "ahc", 4000, ProductType.COOKED_FISH.name());
            addProduct(productList, "Cooked Trout", "ttc", 6000, ProductType.COOKED_FISH.name());
            addProduct(productList, "Cooked Tuna", "tac", 6000, ProductType.COOKED_FISH.name());

            // Monster Drop
            addProduct(productList, "Sandstone", "sem", 10_000, ProductType.MONSTER_DROP.name());
            addProduct(productList, "Magnetite", "mem", 180_000, ProductType.MONSTER_DROP.name());
            addProduct(productList, "Ruby", "rym", 60_000, ProductType.MONSTER_DROP.name());
            addProduct(productList, "Arosite", "aem", 80_000, ProductType.MONSTER_DROP.name());
            addProduct(productList, "Sapphire", "sam", 80_000, ProductType.MONSTER_DROP.name());
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

    public static Optional<ProductBasicInfo> getProductBasicInfo(String resourceCode) {
        String resourceName;
        int unit;
        var product = ProductList.getProductByCode(resourceCode);
        if (product.isEmpty()) {
            return Optional.empty();
        }
        resourceName = product.get().name();
        unit = product.get().cost();
        return Optional.of(new ProductBasicInfo(resourceName, unit));
    }

    private enum ProductType {
        ORE, SALT, BAR, LOGS, RELICS, TAILORING, BAITS, FISHING, COOKED_FISH, MONSTER_DROP
    }

    public record ProductBasicInfo(String resourceName, int unit) {
    }
}
