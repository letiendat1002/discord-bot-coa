package com.bot.command;

import com.bot.product.ProductList;
import com.bot.util.CustomNumberFormat;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.bot.util.CustomNumberFormat.shortenValue;

public class OrderReceipt implements Command {
    public static final String COMMAND_USAGE = "***Order Receipt (admin)***\n> - __Usage__: /receipt <@customer> <item_1> <amount_1> [<item_2> <item_3> ...]";

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

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length < 4 || commandArgs.length % 2 != 0) {
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(7, TimeUnit.SECONDS));
            return;
        }

        var mention = commandArgs[1];

        var receiptMessage = new StringBuilder(mention + " here's the receipt\n" +
                "# ♡Order Receipt♡\n" +
                "```Kindly confirm your order(s):```\n");

        var totalCost = 0;
        var customNumberFormat = new CustomNumberFormat();

        for (var i = 2; i < commandArgs.length; i += 2) {
            var resourceCode = commandArgs[i];
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
                amount = Math.abs(Integer.parseInt(commandArgs[i + 1]));
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("Invalid quantity. Please provide a valid number.")
                        .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            }

            var itemCost = unit * amount;
            totalCost += itemCost;

            receiptMessage.append("\n- `").append(customNumberFormat.format(amount)).append("` ").append(resourceName).append(" `[").append(customNumberFormat.format(unit)).append(" ea]`");
        }

        receiptMessage.append("\n`Total Cost of ").append(shortenValue(totalCost)).append("`\n").append("```Reply [/confirm] if the order list is correct and the prices are acceptable. Thank you (⁠◍⁠•⁠ᴗ⁠•⁠◍⁠)```");

        event.getChannel().sendMessage(receiptMessage.toString()).queue();
    }
}
