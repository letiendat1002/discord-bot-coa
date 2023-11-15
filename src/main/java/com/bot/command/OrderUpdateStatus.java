package com.bot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OrderUpdateStatus implements Command {
    @Override
    public String getName() {
        return "ustatus";
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
        if (payArgs.length != 3) {
            event.getChannel()
                    .sendMessage("Usage: /ustatus <message-id> <-/+>")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var messageId = payArgs[1];
        var argument_1 = payArgs[2];

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
            return "Unknown";
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
            case "On Progress" -> "Pending";
            case "Complete" -> "On Progress";
            default -> currentStatus;
        };
    }

    private String getNextStatus(String currentStatus) {
        return switch (currentStatus) {
            case "Pending" -> "On Progress";
            case "On Progress" -> "Complete";
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
            channel.sendMessage("Error updating status: Unexpected message format.")
                    .queue(mes -> mes.delete().queueAfter(5, TimeUnit.SECONDS));
        }
    }
}
