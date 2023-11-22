package com.bot.command;

import com.bot.util.Constants;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

import static com.bot.util.RoleChecker.isShopper;

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
                event.getChannel().sendMessage(Constants.UNALLOWED_CHANNEL_FOR_COMMAND_EXECUTION_MESSAGE).queue();
                return;
            }
        }

        var payArgs = event.getMessage().getContentRaw().split(" ");

        if (payArgs.length != 1) {
            event.getMessage().delete().queue();
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }


        var confirmMessage_1 = String.format("# ♡Order Confirmed♡ %s\nThank you for your confirmation %s, your order is now added to the Order Queue List.\nWe will contact you again once your order is ready for pickup. ♡⁠(⁠Ӧ⁠ｖ⁠Ӧ⁠｡⁠)\n",
                Constants.STAFF_PING, event.getAuthor().getAsMention());

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
