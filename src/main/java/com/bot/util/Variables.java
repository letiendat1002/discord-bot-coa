package com.bot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Variables {
    public static final String TOKEN_PSC = "MTE3NDI2MTkxMDE0NTg2MzY4MA.GSHBPU.GOkBhWgCMulRKJQE7xQb3HVrktocuG-mwK7GW8";

    public static final String COMMAND_PREFIX = "/";

    public static final String INS_CHANNEL_ID = "1164116381785137163";
    public static final String REGULAR_CATEGORY_ID = "1173921114183962684";
    public static final String VIP_REGULAR_CATEGORY_ID = "1174751714843299850";
    public static final String VVIP_REGULAR_CATEGORY_ID = "1174751859253182474";
    public static final String SELLER_SEARCH_CATEGORY_ID = "1173921222451531776";
    public static final String SELLER_STOCK_CATEGORY_ID = "1175017246507139123";
    public static final String WORKER_REGULAR_CATEGORY_ID = "1174679356208066660";
    public static final String WORKER_SELLER_SEARCH_CATEGORY_ID = "1174679617777438761";

    public static final List<String> validCategories = new ArrayList<String>(
            List.of(REGULAR_CATEGORY_ID,
                    VIP_REGULAR_CATEGORY_ID,
                    VVIP_REGULAR_CATEGORY_ID,
                    SELLER_SEARCH_CATEGORY_ID
            )
    );

    public static final String REGULAR_ORDER_NAME = "Regular Order";
    public static final String SELLER_SEARCH_ORDER_NAME = "Seller Search Order";

    public static final String WORKERS_PING = "<@&1172842755337236510>";
}
