package com.bot.command;

import com.bot.product.ProductList;
import com.bot.util.AdminRoleChecker;
import com.bot.util.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class WorkerOrderCreate implements Command {
    public static final String COMMAND_USAGE = "***Worker Order Create (admin)***\n> - __Usage__: `/worker-order <item_1> <amount_1> [<item_2> <amount_2> ...]`";

    private static Optional<Result> getProductNameAndUnitPrice(MessageReceivedEvent event, String resourceCode) {
        String resourceName;
        int unit;
        var product = ProductList.getProductByCode(resourceCode);
        if (product.isEmpty()) {
            event.getChannel().sendMessage("Invalid resource code.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return Optional.empty();
        } else {
            resourceName = product.get().name();
            unit = product.get().cost();
        }
        return Optional.of(new Result(resourceName, unit));
    }

    @Override
    public String getName() {
        return "worker-order";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (AdminRoleChecker.isNotAdmin(event)) {
            return;
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length < 3 || commandArgs.length % 2 != 1) {
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(7, TimeUnit.SECONDS));
            return;
        }

        var currentChannel = event.getChannel().asTextChannel();
        var channelCategory = currentChannel.getParentCategory();

        if (channelCategory == null) {
            event.getChannel().sendMessage("Invalid channel category.").queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return;
        }

        var channelCategoryId = channelCategory.getId();

        if (!Variables.validCategories.contains(channelCategoryId)) {
            event.getChannel().sendMessage("The command can't be used in here.").queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return;
        }


    }

    private record Result(String resourceName, int unit) {
    }
}
