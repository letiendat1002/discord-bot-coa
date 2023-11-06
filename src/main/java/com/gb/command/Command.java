package com.gb.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {
    String getName();

    void execute(MessageReceivedEvent event);
}
