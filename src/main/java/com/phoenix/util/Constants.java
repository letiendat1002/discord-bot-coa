package com.phoenix.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constants {
    public static final String COMMAND_PREFIX = "/";
    public static final String MULTIPLE_ORDER_CHANNEL_PREFIX = "mo-";
    public static final int MAX_MESSAGE_LENGTH = 1900;
    public static final String PENDING_STATUS = "Pending";
    public static final String ON_PROGRESS_STATUS = "On Progress";
    public static final String COMPLETED_STATUS = "Completed";
    public static final String UNKNOWN = "Unknown";
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
    public static final String BANKER_PING = "<@&1174233159253102642>";
    public static final String DISALLOWED_COMMAND_EXECUTION_MESSAGE = "You are not allowed to use this command.";
    public static final String DISALLOWED_CHANNEL_FOR_COMMAND_EXECUTION_MESSAGE = "The command can't be used in here.";
    public static final String INVALID_RESOURCE_CODE_MESSAGE = "Invalid resource code.";
    public static final String INVALID_CHANNEL_CATEGORY_MESSAGE = "Invalid channel category.";
    public static final String INVALID_AMOUNT_MESSAGE = "Invalid amount. Please enter a valid number.";
    public static final String ERROR_GET_MEMBER_MESSAGE = "Failed while fetching executor info, please try again...";
    public static final String ERROR_UPDATE_STATUS_MESSAGE = "Error updating status: Unexpected message format.";
    public static final String ERROR_WORKER_CATEGORY_NOT_FOUND_MESSAGE = "Worker category not found.";
    public static final String ERROR_PRODUCT_NOT_FOUND_MESSAGE = "Product not found.";
    public static final String ERROR_WORKER_ORDER_EXISTS_MESSAGE = "Worker order is already in progress or waiting in the queue.";
    public static final String WORKER_NOTICE_BOARD_CHANNEL_PING = "<#1174330607560568893>";
    public static final String WORKERS_NOTIFIED_MESSAGE = "✅ Workers have been notified";
    public static final String ERROR_OCCURRED = "ERROR_OCCURRED";

    public static final Set<String> allowedCategoryIds = new HashSet<>(List.of(
            REGULAR_CATEGORY_ID,
            VIP_REGULAR_CATEGORY_ID,
            VVIP_REGULAR_CATEGORY_ID,
            SELLER_SEARCH_CATEGORY_ID,
            SELLER_STOCK_CATEGORY_ID
    ));

    public static final List<String> adminRoles = List.of("Staff", "MANAGER");
}
