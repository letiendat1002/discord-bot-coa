package com.phoenix.command;

import com.phoenix.util.Constants;
import com.phoenix.util.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.phoenix.util.ValidateHelper.isShopper;

public class OrderConfirm implements Command {
    public static final String COMMAND_USAGE = "***Order Confirm***\n> - __Usage__: `/confirm`";

    @Override
    public String getName() {
        return "confirm";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (isShopper(event)) {
            var currentCategoryId = event.getChannel().asTextChannel().getParentCategoryId();

            if (currentCategoryId == null || !Constants.allowedCategoryIds.contains(currentCategoryId)
            ) {
                event.getChannel().sendMessage(Constants.DISALLOWED_CHANNEL_FOR_COMMAND_EXECUTION_MESSAGE).queue();
                return;
            }
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        if (commandArgs.length != 1) {
            event.getMessage().delete().queue();
            ErrorHandler.sendErrorMessage(event.getChannel(), COMMAND_USAGE);
            return;
        }

        var confirmMessage_1 = String.format("# ♡Order Confirmed♡\nThank you for your confirmation %s, your order is now added to the Order Queue List.\nWe will contact you again once your order is ready for pickup. ♡⁠(⁠Ӧ⁠ｖ⁠Ӧ⁠｡⁠) %s\n",
                event.getAuthor().getAsMention(), Constants.STAFF_PING);

        var confirmMessage_2 = """
                ```
                STATUS: [Pending]
                ```
                """;

        event.getChannel().sendMessage(confirmMessage_1).queue();
        event.getChannel().sendMessage(confirmMessage_2).queue(
                message -> message.pin().queue()
        );
    }
}
