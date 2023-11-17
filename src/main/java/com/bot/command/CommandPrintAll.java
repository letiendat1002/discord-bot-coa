package com.bot.command;

import com.bot.util.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CommandPrintAll implements Command {
    public static final String COMMAND_USAGE = "***List all Phoenix's commands (admin)*** - __Usage__: /clist";

    @Override
    public String getName() {
        return "clist";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (Objects.requireNonNull(event.getMember()).getRoles().stream().noneMatch(
                role -> role.getName().equals("Staff") || role.getName().equals("MANAGER"))) {
            event.getChannel()
                    .sendMessage("You are not allowed to use this command.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var payArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (payArgs.length != 1) {
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var commands = CommandList.validCommands;
        var commandUsage = CommandList.commandUsage;

        var commandMessage = new StringBuilder("# Command List\n");

        for (var command : commands) {
            commandMessage
                    .append("## ")
                    .append(Variables.COMMAND_PREFIX)
                    .append(command)
                    .append("\n> ")
                    .append(commandUsage.getOrDefault(command, "No usage information available"))
                    .append("\n");
        }

        event.getChannel().sendMessage(commandMessage.toString()).queue();
    }
}
