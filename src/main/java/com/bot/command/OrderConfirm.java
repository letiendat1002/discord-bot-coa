package com.bot.command;

import com.bot.util.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OrderConfirm implements Command {
    public static final String COMMAND_USAGE = "***Order Confirm***\n> - __Usage__: `/confirm`";

    @Override
    public String getName() {
        return "confirm";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (Objects.requireNonNull(event.getMember()).getRoles().stream().anyMatch(
                role -> role.getName().equals("Shoppers"))) {
            var currentCategoryId = event.getChannel().asTextChannel().getParentCategoryId();

            var allowedCategoryIds = new HashSet<>(List.of(
                    Variables.REGULAR_CATEGORY_ID,
                    Variables.VIP_REGULAR_CATEGORY_ID,
                    Variables.VVIP_REGULAR_CATEGORY_ID,
                    Variables.SELLER_SEARCH_CATEGORY_ID,
                    Variables.SELLER_STOCK_CATEGORY_ID
            ));

            if (currentCategoryId == null || !allowedCategoryIds.contains(currentCategoryId)
            ) {
                event.getChannel().sendMessage("You are not allowed to use this command in this channel.").queue();
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

        var confirmMessage_1 = "# ♡Order Confirmed♡ %s\nThank you for your confirmation ".formatted(Variables.STAFF_PING) +
                event.getAuthor().getAsMention() +
                ", your order is now added to the Order Queue List.\n" +
                "We will contact you again once your order is ready for pickup. ♡⁠(⁠Ӧ⁠ｖ⁠Ӧ⁠｡⁠)\n";
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
