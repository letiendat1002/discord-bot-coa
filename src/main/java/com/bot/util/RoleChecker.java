package com.bot.util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RoleChecker {
    public static boolean isNotAdmin(MessageReceivedEvent event) {
        if (Objects.requireNonNull(event.getMember()).getRoles().stream().noneMatch(
                role -> role.getName().equals("Staff") || role.getName().equals("MANAGER"))) {
            event.getChannel()
                    .sendMessage("You are not allowed to use this command.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return true;
        }
        return false;
    }
}
