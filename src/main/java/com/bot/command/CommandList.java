package com.bot.command;

import java.util.List;
import java.util.Map;

public class CommandList {
    public static final List<String> VALID_COMMANDS = List.of(
            "clist", "plist", "receipt", "confirm", "ustatus", "worker-order"
    );

    public static final Map<String, String> COMMAND_USAGE = Map.of(
            "clist", CommandPrintAll.COMMAND_USAGE,
            "plist", ProductPrintAll.COMMAND_USAGE,
            "receipt", OrderReceipt.COMMAND_USAGE,
            "confirm", OrderConfirm.COMMAND_USAGE,
            "ustatus", OrderUpdateStatus.COMMAND_USAGE,
            "worker-order", WorkerOrderCreate.COMMAND_USAGE
    );
}
