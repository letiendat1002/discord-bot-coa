package com.bot.util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class RoleChecker {
    public static boolean validateAdminRole(MessageReceivedEvent event) {
        if (event == null) {
            return false;
        }
        if (event.getMember() == null) {
            event.getChannel().sendMessage(Constants.GET_MEMBER_ERROR_MESSAGE).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return false;
        }

        if (event.getMember().getRoles().stream().noneMatch(
                role -> Constants.adminRoles.contains(role.getName()))) {
            event.getChannel()
                    .sendMessage(Constants.UNALLOWED_COMMAND_EXECUTION_MESSAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return false;
        }
        return true;
    }

    public static boolean isShopper(MessageReceivedEvent event) {
        return event != null &&
                event.getMember() != null &&
                event.getMember().getRoles().stream().anyMatch(
                        role -> role.getName().equals(Constants.SHOPPERS_ROLE_NAME)
                );
    }
}
