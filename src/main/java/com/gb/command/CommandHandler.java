package com.gb.command;

import com.gb.util.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();

    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public void handle(MessageReceivedEvent event) {
        var content = event.getMessage().getContentRaw();
        var args = content.split(" ");
        var commandName = args[0].substring(Variables.COMMAND_PREFIX.length());

        var command = commands.get(commandName);
        if (command != null) {
            command.execute(event);
        }
    }
}
