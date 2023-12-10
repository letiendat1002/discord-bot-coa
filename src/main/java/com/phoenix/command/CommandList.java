package com.phoenix.command;

import java.util.List;
import java.util.Map;

public class CommandList {
    public static final List<String> VALID_COMMANDS = List.of(
            CommandPrintAll.COMMAND_NAME,
            ProductPrintAll.COMMAND_NAME,
            OrderReceipt.COMMAND_NAME,
            OrderConfirm.COMMAND_NAME,
            OrderUpdateStatus.COMMAND_NAME,
            WorkerOrderCreate.COMMAND_NAME,
            OrderPickup.COMMAND_NAME
    );

    public static final Map<String, String> COMMAND_USAGE = Map.of(
            CommandPrintAll.COMMAND_NAME, CommandPrintAll.COMMAND_USAGE,
            ProductPrintAll.COMMAND_NAME, ProductPrintAll.COMMAND_USAGE,
            OrderReceipt.COMMAND_NAME, OrderReceipt.COMMAND_USAGE,
            OrderConfirm.COMMAND_NAME, OrderConfirm.COMMAND_USAGE,
            OrderUpdateStatus.COMMAND_NAME, OrderUpdateStatus.COMMAND_USAGE,
            WorkerOrderCreate.COMMAND_NAME, WorkerOrderCreate.COMMAND_USAGE,
            OrderPickup.COMMAND_NAME, OrderPickup.COMMAND_USAGE
    );
}
