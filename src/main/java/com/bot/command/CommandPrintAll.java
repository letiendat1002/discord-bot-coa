package com.bot.command;

import com.bot.util.Constants;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandPrintAll implements Command {
    public static final String COMMAND_USAGE = "***List all Phoenix's commands (admin)***\n> - __Usage__: `/clist`";

    @Override
    public String getName() {
        return "clist";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (!ProductPrintAll.isValidCommand(event, COMMAND_USAGE)) {
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
