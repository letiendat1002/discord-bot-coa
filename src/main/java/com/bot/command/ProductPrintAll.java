package com.bot.command;

import com.bot.product.ProductList;
import com.bot.util.CustomNumberFormat;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ProductPrintAll implements Command {
    public static final String COMMAND_USAGE = "Usage: /plist";

    @Override
    public String getName() {
        return "plist";
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
        if (payArgs.length != 1) {
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var productList = ProductList.getAllProducts();
        var productMessage = new StringBuilder("```");

        productMessage.append("Product List```\n");

        var currentCategory = "";
        for (var product : productList) {
            if (!currentCategory.equals(product.type()) && currentCategory.isEmpty()) {
                currentCategory = product.type();
                productMessage.append("***").append(currentCategory.toUpperCase()).append("***:\n");
            } else if (!currentCategory.equals(product.type())) {
                currentCategory = product.type();
                event.getChannel().sendMessage(productMessage.toString()).queue();

                productMessage.setLength(0);
                productMessage.append("***").append(currentCategory.toUpperCase()).append("***:\n");
            }

            productMessage
                    .append("- ")
                    .append(String.format("%s - %s- %s\n",
                                    product.name(),
                                    product.code(),
                                    CustomNumberFormat.shortenValue(product.cost())
                            )
                    );

            if (productMessage.length() >= 1900) {
                event.getChannel().sendMessage(productMessage.toString()).queue();

                productMessage.setLength(0);
                productMessage.append("```***").append(currentCategory.toUpperCase()).append("***:\n");
            }
        }

        event.getChannel().sendMessage(productMessage.toString()).queue();
    }
}
