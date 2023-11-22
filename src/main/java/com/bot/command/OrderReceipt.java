package com.bot.command;

import com.bot.product.ProductList;
import com.bot.util.RoleChecker;
import com.bot.util.CustomNumberFormat;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

import static com.bot.util.CustomNumberFormat.shortenValue;

public class OrderReceipt implements Command {
    public static final String COMMAND_USAGE = "***Order Receipt (admin)***\n> - __Usage__: `/receipt <@customer> <item_1> <amount_1> [<item_2> <amount_2> ...]`";

    @Override
    public String getName() {
        return "receipt";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (!RoleChecker.validateAdminRole(event)) {
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
                "```Kindly confirm your order(s):```");

        var totalCost = 0;
        var customNumberFormat = new CustomNumberFormat();

        for (var i = 2; i < commandArgs.length; i += 2) {
            var resourceCode = commandArgs[i];
            int amount;

            var result = ProductList.getProductBasicInfo(resourceCode);
            if (result.isEmpty()) {
                event.getChannel().sendMessage("Invalid resource code.")
                        .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            }
            var productInfo = result.get();

            try {
                amount = Math.abs(Integer.parseInt(commandArgs[i + 1]));
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("Invalid quantity. Please provide a valid number.")
                        .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            }

            var itemCost = productInfo.unit() * amount;
            totalCost += itemCost;

            receiptMessage.append("\n- `")
                    .append(customNumberFormat.format(amount))
                    .append("` ")
                    .append(productInfo.resourceName())
                    .append(" `[")
                    .append(customNumberFormat.format(productInfo.unit()))
                    .append(" ea]`");
        }

        receiptMessage.append("\n`Total Cost of ")
                .append(shortenValue(totalCost))
                .append("`\n")
                .append("```Reply [/confirm] if the order list is correct and the prices are acceptable. Thank you (⁠◍⁠•⁠ᴗ⁠•⁠◍⁠)```");

        event.getChannel().sendMessage(receiptMessage.toString()).queue(
                message -> message.pin().queue()
        );
    }
}
