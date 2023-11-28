package com.bot.command;

import com.bot.util.Constants;
import com.bot.util.RoleChecker;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class CommandPrintAll implements Command {
    public static final String COMMAND_USAGE = "***List all Phoenix's commands (admin)***\n> - __Usage__: `/clist`";

    static boolean validatePrintAllCommand(MessageReceivedEvent event, String commandUsage2) {
        if (!RoleChecker.validateAdminRole(event)) {
            return false;
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length != 1) {
            event.getChannel()
                    .sendMessage(commandUsage2)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return "clist";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (!validatePrintAllCommand(event, COMMAND_USAGE)) return;

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
