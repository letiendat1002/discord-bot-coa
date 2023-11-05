package com.gb;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.gb.util.CustomNumberFormat.shortenValue;

public class RenameChannel implements Command {
    @Override
    public String getName() {
        return "rc";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        var payArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (payArgs.length != 3) {
            event.getChannel()
                    .sendMessage("Usage: b!rc <resource_code> <amount>")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var resourceCode = payArgs[1];
        var resourceName = "";
        int amount;
        int unit;

        try {
            amount = Math.abs(Integer.parseInt(payArgs[2]));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid quantity. Please provide a valid number.").queue();
            return;
        }

        var product = ProductList.getProductByCode(resourceCode);
        if (product.isEmpty()) {
            event.getChannel().sendMessage("Invalid resource code.").queue();
            return;
        } else {
            resourceName = product.get().name();
            unit = product.get().cost();
        }

        var newChannelName = MessageFormat.format(
                "{0}-{1}-{2}",
                shortenValue(amount),
                resourceName,
                shortenValue(unit)
        );

        var currentChannelID = event.getChannel().getId();

        Objects.requireNonNull(event.getGuild()
                        .getTextChannelById(currentChannelID))
                .getManager()
                .setName(newChannelName)
                .queue();
    }
}
