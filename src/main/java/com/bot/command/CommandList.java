package com.bot.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandList {
    public static List<String> validCommands = new ArrayList<>(
            List.of(
                    "clist", "plist", "receipt", "confirm", "ustatus"
            )
    );

    public static Map<String, String> commandUsage = createCommandUsageMap();

    private static Map<String, String> createCommandUsageMap() {
        var map = new HashMap<String, String>();
        map.put("clist", CommandPrintAll.COMMAND_USAGE);
        map.put("plist", ProductPrintAll.COMMAND_USAGE);
        map.put("receipt", OrderReceipt.COMMAND_USAGE);
        map.put("confirm", OrderConfirm.COMMAND_USAGE);
        map.put("ustatus", OrderUpdateStatus.COMMAND_USAGE);
        return map;
    }
}
