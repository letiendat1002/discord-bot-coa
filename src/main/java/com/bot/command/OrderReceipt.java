package com.bot.command;

import com.bot.product.ProductList;
import com.bot.util.CustomNumberFormat;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.bot.util.CustomNumberFormat.shortenValue;

public class OrderReceipt implements Command {
    @Override
    public String getName() {
        return "receipt";
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
        if (payArgs.length != 4) {
            event.getChannel()
                    .sendMessage("Usage: /receipt <resource_code> <amount> <@customer>")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var resourceCode = payArgs[1];
        var resourceName = "";
        int unit;
        int amount;

        var product = ProductList.getProductByCode(resourceCode);
        if (product.isEmpty()) {
            event.getChannel().sendMessage("Invalid resource code.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        } else {
            resourceName = product.get().name();
            unit = product.get().cost();
        }

        try {
            amount = Math.abs(Integer.parseInt(payArgs[2]));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid quantity. Please provide a valid number.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var mention = payArgs[3];

        var totalCost = unit * amount;
        var customNumberFormat = new CustomNumberFormat();

        var receiptMessage = mention + " here's the receipt\n" +
                "# ♡Order Receipt♡\n" +
                "```\n" +
                "Kindly confirm your order(s):```\n" +
                "- `" + customNumberFormat.format(amount) + "` " + resourceName + " `[" + customNumberFormat.format(unit) + " ea]`\n" +
                "`Total Cost of " + shortenValue(totalCost) + "`\n" +
                "```Reply [/confirm] if the order list is correct and the prices are acceptable. Thank you (⁠◍⁠•⁠ᴗ⁠•⁠◍⁠)```";

        event.getChannel().sendMessage(receiptMessage).queue();
    }
}
