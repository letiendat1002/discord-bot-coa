package com.phoenix.product;

import java.util.*;

public class ProductList {
    private static final String ORE_TYPE = ProductType.ORE.name();
    private static final String SALT_TYPE = ProductType.SALT.name();
    private static final String BAR_TYPE = ProductType.BARS.name();
    private static final String LOGS_TYPE = ProductType.LOGS.name();
    private static final String RELICS_TYPE = ProductType.RELICS.name();
    private static final String TAILORING_TYPE = ProductType.TAILORING.name();
    private static final String BAITS_TYPE = ProductType.BAITS.name();
    private static final String FISHING_TYPE = ProductType.FISHING.name();
    private static final String COOKED_FISH_TYPE = ProductType.COOKED_FISH.name();
    private static final String MONSTER_DROP_TYPE = ProductType.MONSTER_DROP.name();
    private static final String MISCELLANEOUS_TYPE = ProductType.MISCELLANEOUS.name();
    private static final Set<String> PRODUCT_CODES = new HashSet<>();
    private static List<Product> productList;

    public static List<Product> getAllProducts() {
        if (productList == null) {
            initializeProductList();
        }
        return productList;
    }

    private static synchronized void initializeProductList() {
        if (productList == null) {
            productList = new ArrayList<>();

            initializeProducts(ORE_TYPE,
                    new Product("Coal", "clo", 4000, ORE_TYPE),
                    new Product("Iron Ore", "ino", 4000, ORE_TYPE),
                    new Product("Crimsteel Ore", "cro", 5000, ORE_TYPE),
                    new Product("Silver Ore", "sro", 4000, ORE_TYPE),
                    new Product("Gold Ore", "gdo", 4000, ORE_TYPE),
                    new Product("Mythan Ore", "mno", 8000, ORE_TYPE),
                    new Product("Cobalt Ore", "cto", 12_000, ORE_TYPE),
                    new Product("Varaxium Ore", "vmo", 13_000, ORE_TYPE),
                    new Product("Magic Ore", "mco", 4500, ORE_TYPE)
            );

            initializeProducts(SALT_TYPE,
                    new Product("Salt", "sts", 4000, SALT_TYPE),
                    new Product("Pink Salt", "pks", 9000, SALT_TYPE),
                    new Product("Black Salt", "bks", 10_000, SALT_TYPE)
            );

            initializeProducts(BAR_TYPE,
                    new Product("Crimsteel Bars", "clb", 22_000, BAR_TYPE),
                    new Product("Silver Bars", "srb", 20_000, BAR_TYPE),
                    new Product("Gold Bars", "gdb", 1_500_000, BAR_TYPE),
                    new Product("Mythan Bars", "mnb", 105_000, BAR_TYPE),
                    new Product("Cobalt Bars", "ctb", 120_000, BAR_TYPE),
                    new Product("Varaxium Bars", "vmb", 150_000, BAR_TYPE),
                    new Product("Magic Bars", "mcb", 60_000, BAR_TYPE)
            );

            initializeProducts(LOGS_TYPE,
                    new Product("Pine Logs", "pel", 2000, LOGS_TYPE),
                    new Product("Dead Logs", "ddl", 3000, LOGS_TYPE),
                    new Product("Birch Logs", "bhl", 3000, LOGS_TYPE),
                    new Product("Applewood", "adl", 3000, LOGS_TYPE),
                    new Product("Willow Logs", "wwl", 5500, LOGS_TYPE),
                    new Product("Oak Logs", "okl", 6000, LOGS_TYPE),
                    new Product("Chestnut Logs", "ctl", 9_000, LOGS_TYPE),
                    new Product("Maple Logs", "mel", 10_000, LOGS_TYPE),
                    new Product("Olive Logs", "oel", 12_000, LOGS_TYPE),
                    new Product("Magic Logs", "mcl", 2500, LOGS_TYPE),
                    new Product("Palm Logs", "pml", 5000, LOGS_TYPE),
                    new Product("Pear Logs", "prl", 9000, LOGS_TYPE)
            );

            initializeProducts(RELICS_TYPE,
                    new Product("Accuracy Relics", "ayr", 4000, RELICS_TYPE),
                    new Product("Guardian Relics", "gnr", 4000, RELICS_TYPE),
                    new Product("Healing Relics", "hgr", 4000, RELICS_TYPE),
                    new Product("Wealth Relics", "whr", 4000, RELICS_TYPE),
                    new Product("Power Relics", "prr", 7000, RELICS_TYPE),
                    new Product("Nature Relics", "ner", 7000, RELICS_TYPE),
                    new Product("Fire Relics", "fer", 11_000, RELICS_TYPE),
                    new Product("Damage Relics", "der", 9000, RELICS_TYPE),
                    new Product("Leeching Relics", "lgr", 7000, RELICS_TYPE),
                    new Product("Ice Relics", "ier", 8_000, RELICS_TYPE),
                    new Product("Experience Relics", "eer", 12_000, RELICS_TYPE),
                    new Product("Cursed Relics", "cdr", 4000, RELICS_TYPE),
                    new Product("Efficiency Relics", "eyr", 4000, RELICS_TYPE)
            );

            initializeProducts(TAILORING_TYPE,
                    new Product("Books", "bkt", 4500, TAILORING_TYPE),
                    new Product("Magic Essence", "met", 1500, TAILORING_TYPE),
                    new Product("Leathers", "lrt", 2500, TAILORING_TYPE)
            );

            initializeProducts(BAITS_TYPE,
                    new Product("Earthworm", "emb", 1000, BAITS_TYPE),
                    new Product("Iceworm", "imb", 1000, BAITS_TYPE),
                    new Product("Corpseworm", "cmb", 1000, BAITS_TYPE),
                    new Product("Toxic Worm", "tmb", 1000, BAITS_TYPE),
                    new Product("Sandworm", "smb", 1000, BAITS_TYPE),
                    new Product("Beetles", "beb", 2500, BAITS_TYPE),
                    new Product("Grasshoppers", "grb", 12_000, BAITS_TYPE),
                    new Product("Wasp", "wpb", 2500, BAITS_TYPE),
                    new Product("Scallops", "spb", 10_000, BAITS_TYPE)
            );

            initializeProducts(FISHING_TYPE,
                    new Product("Raw Eel", "elf", 4000, FISHING_TYPE),
                    new Product("Raw Anglerfish", "ahf", 6000, FISHING_TYPE),
                    new Product("Raw Trout", "ttf", 8000, FISHING_TYPE),
                    new Product("Raw Bass", "bsf", 39_000, FISHING_TYPE),
                    new Product("Raw Tuna", "taf", 6000, FISHING_TYPE)
            );

            initializeProducts(COOKED_FISH_TYPE,
                    new Product("Cooked Eel", "elc", 3500, COOKED_FISH_TYPE),
                    new Product("Cooked Anglerfish", "ahc", 4000, COOKED_FISH_TYPE),
                    new Product("Cooked Trout", "ttc", 6000, COOKED_FISH_TYPE),
                    new Product("Cooked Tuna", "tac", 6000, COOKED_FISH_TYPE)
            );

            initializeProducts(MONSTER_DROP_TYPE,
                    new Product("Sandstone", "sem", 10_000, MONSTER_DROP_TYPE),
                    new Product("Magnetite", "mem", 200_000, MONSTER_DROP_TYPE),
                    new Product("Ruby", "rym", 60_000, MONSTER_DROP_TYPE),
                    new Product("Arosite", "aem", 100_000, MONSTER_DROP_TYPE),
                    new Product("Sapphire", "sam", 80_000, MONSTER_DROP_TYPE)
            );

            initializeProducts(MISCELLANEOUS_TYPE,
                    new Product("Glass", "gsm", 135_000, MISCELLANEOUS_TYPE)
            );
        }
    }

    private static void initializeProducts(String type, Product... products) {
        for (Product product : products) {
            addProduct(productList, product, type);
        }
    }

    private static void addProduct(List<Product> productList, Product product, String type) {
        var code = product.code();
        if (!isValidCode(code)) {
            throw new IllegalArgumentException("Invalid product code: " + code);
        }

        productList.add(new Product(product.name(), code, product.cost(), type));
        PRODUCT_CODES.add(code);
    }

    private static boolean isValidCode(String code) {
        return !PRODUCT_CODES.contains(code.toLowerCase());
    }

    public static Optional<Product> getProductByCode(String productCode) {
        if (productCode == null || productCode.trim().isEmpty()) {
            return Optional.empty();
        }
        var productList = getAllProducts();
        return productList.stream()
                .filter(product -> product.code().equalsIgnoreCase(productCode.strip()))
                .findFirst();
    }

    public static Optional<ProductBasicInfo> getProductBasicInfo(String resourceCode) {
        return getProductByCode(resourceCode)
                .map(product -> new ProductBasicInfo(product.name(), product.cost()));
    }

    public enum ProductType {
        ORE, SALT, BARS, LOGS, RELICS, TAILORING, BAITS, FISHING, COOKED_FISH, MONSTER_DROP, MISCELLANEOUS
    }

    public record ProductBasicInfo(String resourceName, int unit) {
        public ProductBasicInfo {
            if (unit < 0) {
                throw new IllegalArgumentException("Unit cannot be negative");
            }
        }
    }
}
