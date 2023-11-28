package com.bot.command;

import com.bot.util.Constants;
import com.bot.util.RoleChecker;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class OrderUpdateStatus implements Command {
    public static final String COMMAND_USAGE = "***Update Order Status (admin)***\n> - __Usage__: `/ustatus <message-id> <-/+>`";

    @Override
    public String getName() {
        return "ustatus";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (!RoleChecker.validateAdminRole(event)) {
            return;
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length != 3) {
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var messageId = commandArgs[1];
        var argument_1 = commandArgs[2];

        var currentStatus = getCurrentStatus(event, messageId);

        var updatedStatus = updateStatus(currentStatus, argument_1);

        updateStatusMessage(event, messageId, updatedStatus);
    }

    private String getCurrentStatus(MessageReceivedEvent event, String messageId) {
        var message = event.getChannel().retrieveMessageById(messageId).complete();

        var content = message.getContentDisplay();
        int startIndex = content.indexOf('[');
        int endIndex = content.indexOf(']');
        if (startIndex != -1 && endIndex != -1) {
            return content.substring(startIndex + 1, endIndex);
        } else {
            return Constants.UNKNOWN_STATUS;
        }
    }

    private String updateStatus(String currentStatus, String argument) {
        if ("-".equals(argument)) {
            return getPreviousStatus(currentStatus);
        } else if ("+".equals(argument)) {
            return getNextStatus(currentStatus);
        } else {
            return currentStatus;
        }
    }

    private String getPreviousStatus(String currentStatus) {
        return switch (currentStatus) {
            case Constants.ON_PROGRESS_STATUS -> Constants.PENDING_STATUS;
            case Constants.COMPLETED_STATUS -> Constants.ON_PROGRESS_STATUS;
            default -> currentStatus;
        };
    }

    private String getNextStatus(String currentStatus) {
        return switch (currentStatus) {
            case Constants.PENDING_STATUS -> Constants.ON_PROGRESS_STATUS;
            case Constants.ON_PROGRESS_STATUS -> Constants.COMPLETED_STATUS;
            default -> currentStatus;
        };
    }

    private void updateStatusMessage(MessageReceivedEvent event, String messageId, String updatedStatus) {
        var channel = event.getChannel();

        var message = channel.retrieveMessageById(messageId).complete();

        var content = message.getContentDisplay();
        int startIndex = content.indexOf('[');
        int endIndex = content.indexOf(']');

        if (startIndex != -1 && endIndex != -1) {
            var newContent = "```STATUS: [" + updatedStatus + "]```";

            message.editMessage(newContent).queue();
        } else {
            channel.sendMessage(Constants.ERROR_UPDATE_STATUS_MESSAGE)
                    .queue(mes -> mes.delete().queueAfter(5, TimeUnit.SECONDS));
        }
    }
}
