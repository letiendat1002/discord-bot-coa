package com.phoenix.util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class ValidateHelper {
    public static boolean validateNotAdminRole(MessageReceivedEvent event) {
        if (event == null) {
            return true;
        }
        if (event.getMember() == null) {
            event.getChannel().sendMessage(Constants.ERROR_GET_MEMBER_MESSAGE).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return true;
        }

        if (event.getMember().getRoles().stream().noneMatch(
                role -> Constants.adminRoles.contains(role.getName()))) {
            event.getChannel()
                    .sendMessage(Constants.DISALLOWED_COMMAND_EXECUTION_MESSAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return true;
        }
        return false;
    }

    public static boolean isShopper(MessageReceivedEvent event) {
        return event != null &&
                event.getMember() != null &&
                event.getMember().getRoles().stream().anyMatch(
                        role -> role.getName().equals(Constants.SHOPPERS_ROLE_NAME)
                );
    }

    public static boolean isCommandNotExecutedInAllowedCategory(MessageReceivedEvent event) {
        var currentChannel = event.getChannel().asTextChannel();
        var channelCategory = currentChannel.getParentCategory();

        if (channelCategory == null) {
            event.getChannel().sendMessage(Constants.INVALID_CHANNEL_CATEGORY_MESSAGE).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return true;
        }

        var channelCategoryId = channelCategory.getId();

        if (!Constants.allowedCategoryIds.contains(channelCategoryId)) {
            event.getChannel().sendMessage(Constants.DISALLOWED_CHANNEL_FOR_COMMAND_EXECUTION_MESSAGE).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return true;
        }
        return false;
    }
}
