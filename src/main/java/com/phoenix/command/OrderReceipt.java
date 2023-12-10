package com.phoenix.command;

import com.phoenix.product.ProductList;
import com.phoenix.util.Constants;
import com.phoenix.util.CustomNumberFormat;
import com.phoenix.util.ErrorHandler;
import com.phoenix.util.ValidateHelper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.phoenix.util.CustomNumberFormat.shortenValue;

public class OrderReceipt implements Command {
    public static final String COMMAND_NAME = "receipt";
    public static final String COMMAND_USAGE = "***Order Receipt (admin)***\n> - __Usage__: `/receipt <@customer> <item_1> <amount_1> [<item_2> <amount_2> ...]`";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (ValidateHelper.validateNotAdminRole(event)) {
            return;
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length < 4 || commandArgs.length % 2 != 0) {
            ErrorHandler.sendErrorMessage(event.getChannel(), COMMAND_USAGE);
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
                ErrorHandler.sendErrorMessage(
                        event.getChannel(),
                        Constants.INVALID_RESOURCE_CODE_MESSAGE
                );
                return;
            }
            var productInfo = result.get();

            try {
                amount = Math.abs(Integer.parseInt(commandArgs[i + 1]));
            } catch (NumberFormatException e) {
                ErrorHandler.sendErrorMessage(event.getChannel(), Constants.INVALID_AMOUNT_MESSAGE);
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
