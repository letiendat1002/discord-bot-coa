package com.phoenix.command;

import com.phoenix.util.Constants;
import com.phoenix.util.ErrorHandler;
import com.phoenix.util.ValidateHelper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandPrintAll implements Command {
    public static final String COMMAND_NAME = "clist";
    public static final String COMMAND_USAGE = "***List all Phoenix's commands (admin)***\n> - __Usage__: `/clist`";

    static boolean isNotValidPrintAllCommand(MessageReceivedEvent event, String commandUsage) {
        if (ValidateHelper.validateNotAdminRole(event)) {
            return true;
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length != 1) {
            ErrorHandler.sendErrorMessage(event.getChannel(), commandUsage);
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (isNotValidPrintAllCommand(event, COMMAND_USAGE)) {
            return;
        }

        var commands = CommandList.VALID_COMMANDS;
        var commandUsage = CommandList.COMMAND_USAGE;

        var commandMessage = new StringBuilder("# Command List\n");

        for (var command : commands) {
            commandMessage
                    .append("## ")
                    .append(Constants.COMMAND_PREFIX)
                    .append(command)
                    .append("\n> ")
                    .append(commandUsage.getOrDefault(command, "No usage information available"))
                    .append("\n\n");
        }

        event.getChannel().sendMessage(commandMessage.toString()).queue();
    }
}
