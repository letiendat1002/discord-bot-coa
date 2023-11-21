package com.bot.command;

import com.bot.util.RoleChecker;
import com.bot.util.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.bot.product.ProductList.getProductBasicInfo;

public class WorkerOrderCreate implements Command {
    public static final String COMMAND_USAGE = "***Worker Order Create (admin)***\n> - __Usage__: `/worker-order <item_1> <amount_1> [<item_2> <amount_2> ...]`";

    @Override
    public String getName() {
        return "worker-order";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (RoleChecker.isNotAdmin(event)) {
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

        var commandExecutedChannelName = currentChannel.getName();
        var countTag = commandExecutedChannelName.substring(commandExecutedChannelName.lastIndexOf("-") + 1);
        var channelName = new StringBuilder();

        var innerMessage = new StringBuilder();
        innerMessage.append("# ♡Work Order♡");

        if (channelCategoryId.equals(Variables.VIP_REGULAR_CATEGORY_ID) ||
                channelCategoryId.equals(Variables.VVIP_REGULAR_CATEGORY_ID)) {
            innerMessage.append(" ").append(Variables.WORKERS_PING);
        }

        innerMessage.append("\n");

        if (channelCategoryId.equals(Variables.SELLER_SEARCH_CATEGORY_ID)) {
            innerMessage.append("*")
                    .append(Variables.SELLER_SEARCH_ORDER_NAME).append("*\n");
        } else {
            innerMessage.append("*")
                    .append(Variables.REGULAR_ORDER_NAME).append("*\n");
        }

        innerMessage.append("```Order(s):\n");

        if ((commandArgs.length - 1) / 2 > 1) {
            channelName.append("mo-");
        }

        int amount;
        String resourceName;
        for (var i = 1; i < commandArgs.length; i += 2) {
            var resourceCode = commandArgs[i];

            try {
                amount = Math.abs(Integer.parseInt(commandArgs[i + 1]));
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("Invalid quantity. Please provide a valid number.")
                        .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            }

            var result = getProductBasicInfo(resourceCode);
            if (result.isEmpty() && !channelCategoryId.equals(Variables.SELLER_SEARCH_CATEGORY_ID)) {
                event.getChannel().sendMessage("Invalid resource code.")
                        .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            } else if (result.isEmpty()) {
                resourceName = resourceCode.replace("-", " ");
            } else {
                var productInfo = result.get();
                resourceName = productInfo.resourceName();
            }

            channelName
                    .append(amount)
                    .append("-")
                    .append(resourceName.toLowerCase().replace(" ", "-"))
                    .append("-");

            innerMessage.append(resourceName).append(" - ").append(amount).append("\n");
        }

        channelName.deleteCharAt(channelName.length() - 1);
        channelName.append("-")
                .append(countTag);

        innerMessage.append("```");

        var completedChannelName = channelName.toString();
        if (checkIfCategoryIsExisted(event, channelCategoryId, completedChannelName)) {
            event.getChannel().sendMessage(
                    "The order with name: {%s} already exists.".formatted(completedChannelName)
            ).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return;
        }

        var workerRegularCategory = event.getGuild().getCategoryById(Variables.WORKER_REGULAR_CATEGORY_ID);
        var workerSellerSearchCategory = event.getGuild().getCategoryById(Variables.WORKER_SELLER_SEARCH_CATEGORY_ID);

        if (channelCategoryId.equals(Variables.REGULAR_CATEGORY_ID) ||
                channelCategoryId.equals(Variables.VIP_REGULAR_CATEGORY_ID) ||
                channelCategoryId.equals(Variables.VVIP_REGULAR_CATEGORY_ID)) {
            event.getGuild().createTextChannel(completedChannelName)
                    .setParent(workerRegularCategory)
                    .queue(
                            channel -> channel.sendMessage(innerMessage.toString()).queue()
                    );
        } else {
            event.getGuild().createTextChannel(completedChannelName)
                    .setParent(workerSellerSearchCategory)
                    .queue(
                            channel -> channel.sendMessage(innerMessage.toString()).queue()
                    );
        }
    }

    private boolean checkIfCategoryIsExisted(MessageReceivedEvent event, String categoryId, String channelName) {
        var workerRegularOrders = Objects.requireNonNull(event.getGuild().getCategoryById(Variables.WORKER_REGULAR_CATEGORY_ID)).getChannels().stream().toList();
        var workerSellerSearchOrders = Objects.requireNonNull(event.getGuild().getCategoryById(Variables.WORKER_SELLER_SEARCH_CATEGORY_ID)).getChannels().stream().toList();

        if (categoryId.equals(Variables.REGULAR_CATEGORY_ID)
                || categoryId.equals(Variables.VIP_REGULAR_CATEGORY_ID)
                || categoryId.equals(Variables.VVIP_REGULAR_CATEGORY_ID)) {
            return workerRegularOrders.stream().anyMatch(channel -> channel.getName().equals(channelName));
        } else {
            return workerSellerSearchOrders.stream().anyMatch(channel -> channel.getName().equals(channelName));
        }
    }
}
