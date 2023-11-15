package com.bot.command;

import com.bot.product.ProductList;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class ProductPrintAll implements Command {
    @Override
    public String getName() {
        return "plist";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        var productList = ProductList.getAllProducts();
        var productMessage = """
                ```
                Product List
                ```
                """;
        for (var product : productList) {

        }
        event.getChannel().sendMessage().queue();
    }
}
