package com.bot.command;

import java.util.*;

public class CommandList {
    public static HashSet<String> validCommands = new HashSet<>(
            Arrays.asList(
                    "receipt", "confirm", "ustatus", "plist", "clist"
            )
    );

    public static Map<String, String> commandUsage = createCommandUsageMap();

    private static Map<String, String> createCommandUsageMap() {
        Map<String, String> map = new HashMap<>();
        map.put("receipt", OrderReceipt.COMMAND_USAGE);
        map.put("confirm", OrderConfirm.COMMAND_USAGE);
        map.put("ustatus", OrderUpdateStatus.COMMAND_USAGE);
        map.put("plist", ProductPrintAll.COMMAND_USAGE);
        map.put("clist", CommandPrintAll.COMMAND_USAGE);
        return map;
    }
}
