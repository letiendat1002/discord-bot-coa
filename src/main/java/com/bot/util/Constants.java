package com.bot.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constants {
    public static final String TOKEN_PSC = "MTE3NDI2MTkxMDE0NTg2MzY4MA.GSHBPU.GOkBhWgCMulRKJQE7xQb3HVrktocuG-mwK7GW8";
    public static final String COMMAND_PREFIX = "/";
    public static final String REGULAR_CATEGORY_ID = "1173921114183962684";
    public static final String VIP_REGULAR_CATEGORY_ID = "1174751714843299850";
    public static final String VVIP_REGULAR_CATEGORY_ID = "1174751859253182474";
    public static final String SELLER_SEARCH_CATEGORY_ID = "1173921222451531776";
    public static final String SELLER_STOCK_CATEGORY_ID = "1175017246507139123";
    public static final String WORKER_REGULAR_CATEGORY_ID = "1174679356208066660";
    public static final String WORKER_SELLER_SEARCH_CATEGORY_ID = "1174679617777438761";
    public static final String REGULAR_ORDER_NAME = "Regular Order";
    public static final String SELLER_SEARCH_ORDER_NAME = "Seller Search Order";
    public static final String SHOPPERS_ROLE_NAME = "Shoppers";
    public static final String WORKERS_PING = "<@&1172842755337236510>";
    public static final String STAFF_PING = "<@&1173903842795065354>";
    public static final String UNALLOWED_COMMAND_EXECUTION_MESSAGE = "You are not allowed to use this command.";
    public static final String UNALLOWED_CHANNEL_FOR_COMMAND_EXECUTION_MESSAGE = "The command can't be used in here.";
    public static final String ERROR_MESSAGE = "Failed while fetching executor info, please try again...";

    public static final Set<String> allowedCategoryIds = new HashSet<>(List.of(
            REGULAR_CATEGORY_ID,
            VIP_REGULAR_CATEGORY_ID,
            VVIP_REGULAR_CATEGORY_ID,
            SELLER_SEARCH_CATEGORY_ID,
            SELLER_STOCK_CATEGORY_ID
    ));

    public static final List<String> adminRoles = List.of("Staff", "MANAGER");
}
