package com.bot.command;

import com.bot.product.ProductList;
import com.bot.util.Constants;
import com.bot.util.CustomNumberFormat;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class ProductPrintAll implements Command {
    public static final String COMMAND_USAGE = "***List all products (admin)***\n> - __Usage__: `/plist`";

    @Override
    public String getName() {
        return "plist";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (CommandPrintAll.isNotValidPrintAllCommand(event, COMMAND_USAGE)) return;

        var productList = ProductList.getAllProducts();
        if (productList == null || productList.isEmpty()) {
            event.getChannel().sendMessage(Constants.ERROR_PRODUCT_NOT_FOUND_MESSAGE).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return;
        }


        var productMessage = new StringBuilder("```");

        productMessage.append("Product List```\n");

        var currentCategory = "";
        for (var product : productList) {
            if (!currentCategory.equals(product.type()) && currentCategory.isEmpty()) {
                currentCategory = product.type();
                productMessage.append("***")
                        .append(currentCategory.toUpperCase().replace("_", " "))
                        .append("***:\n");
            } else if (!currentCategory.equals(product.type())) {
                currentCategory = product.type();
                event.getChannel().sendMessage(productMessage.toString()).queue();

                productMessage.setLength(0);
                productMessage.append("***")
                        .append(currentCategory.toUpperCase().replace("_", " "))
                        .append("***:\n");
            }

            productMessage
                    .append("- ")
                    .append(String.format("%s - %s - %s\n",
                                    product.name(),
                                    product.code(),
                                    CustomNumberFormat.shortenValue(product.cost())
                            )
                    );

            if (productMessage.length() >= Constants.MAX_MESSAGE_LENGTH) {
                event.getChannel().sendMessage(productMessage.toString()).queue();

                productMessage.setLength(0);
                productMessage.append("```***").append(currentCategory.toUpperCase()).append("***:\n");
            }
        }

        event.getChannel().sendMessage(productMessage.toString()).queue();
    }
}
