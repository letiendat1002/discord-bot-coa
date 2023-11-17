package com.bot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class OrderConfirm implements Command {
    public static final String COMMAND_USAGE = "***Order Confirm***\n> - __Usage__: /confirm";

    @Override
    public String getName() {
        return "confirm";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        var payArgs = event.getMessage().getContentRaw().split(" ");

        if (payArgs.length != 1) {
            event.getMessage().delete().queue();
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var confirmMessage_1 = "# ♡Order Confirmed♡\nThank you for your confirmation " +
                event.getAuthor().getAsMention() +
                ", your order is now added to the Order Queue List.\n" +
                "We will contact you again once your order is ready for pickup. ♡⁠(⁠Ӧ⁠ｖ⁠Ӧ⁠｡⁠)\n";
        var confirmMessage_2 = """
                ```
                STATUS: [Pending]
                ```
                """;

        event.getChannel().sendMessage(confirmMessage_1).queue();
        event.getChannel().sendMessage(confirmMessage_2).queue();
    }
}
