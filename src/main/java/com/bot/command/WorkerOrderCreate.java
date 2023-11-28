package com.bot.command;

import com.bot.util.Constants;
import com.bot.util.RoleChecker;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Optional;
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
        if (!RoleChecker.validateAdminRole(event)) {
            return;
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length < 3 || commandArgs.length % 2 != 1) {
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var currentChannel = event.getChannel().asTextChannel();
        var channelCategory = currentChannel.getParentCategory();

        if (channelCategory == null) {
            event.getChannel().sendMessage(Constants.INVALID_CHANNEL_CATEGORY_MESSAGE).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return;
        }

        var channelCategoryId = channelCategory.getId();

        if (!Constants.allowedCategoryIds.contains(channelCategoryId)) {
            event.getChannel().sendMessage(Constants.DISALLOWED_CHANNEL_FOR_COMMAND_EXECUTION_MESSAGE).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return;
        }

        var commandExecutedChannelName = currentChannel.getName();
        var countTag = commandExecutedChannelName.substring(commandExecutedChannelName.lastIndexOf("-") + 1);
        var channelName = new StringBuilder();

        var innerMessage = new StringBuilder();
        innerMessage.append("# ♡New Work Order♡");

        if (channelCategoryId.equals(Constants.VIP_REGULAR_CATEGORY_ID) ||
                channelCategoryId.equals(Constants.VVIP_REGULAR_CATEGORY_ID)) {
            innerMessage.append(" ").append(Constants.WORKERS_PING);
        }

        innerMessage.append("\n");

        if (channelCategoryId.equals(Constants.SELLER_SEARCH_CATEGORY_ID)) {
            innerMessage.append("*")
                    .append(Constants.SELLER_SEARCH_ORDER_NAME).append("*\n");
        } else {
            innerMessage.append("*")
                    .append(Constants.REGULAR_ORDER_NAME).append("*\n");
        }

        innerMessage.append("```Order(s):\n");

        var isMultipleOrder = (commandArgs.length - 1) / 2 > 1;

        if (isMultipleOrder) {
            channelName.append(Constants.MULTIPLE_ORDER_CHANNEL_PREFIX);
        }

        int amount;
        String resourceName;
        for (var i = 1; i < commandArgs.length; i += 2) {
            var resourceCode = commandArgs[i];

            try {
                amount = Math.abs(Integer.parseInt(commandArgs[i + 1]));
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage(Constants.INVALID_AMOUNT_MESSAGE)
                        .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
                return;
            }

            var result = getProductBasicInfo(resourceCode);
            if (result.isEmpty() && !channelCategoryId.equals(Constants.SELLER_SEARCH_CATEGORY_ID)) {
                event.getChannel().sendMessage(Constants.INVALID_RESOURCE_CODE_MESSAGE)
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

        innerMessage.append("```")
                .append("\n")
                .append(Constants.WORKERS_PING);
        if (!isMultipleOrder) {
            innerMessage.append(", kindly type: `Claim <amount>` *to claim an amount* that you wish to provide in the order.\n");
        } else {
            innerMessage.append(", kindly type: `Claim <amount> <item>` *to claim an amount* that you wish to provide in the order.\n");
        }
        innerMessage.append("> - Make sure to keep in mind your __***claim limits per order***__.\n> - **Read:** %s to see your claim limits per order *depending on your worker rank*."
                .formatted(Constants.WORKER_NOTICE_BOARD_CHANNEL_PING)
        );

        var completedChannelName = channelName.toString();

        var workerOrderCategoryOptional = getWorkerOrderCategory(channelCategory);
        if (workerOrderCategoryOptional.isEmpty()) {
            event.getChannel().sendMessage(Constants.ERROR_WORKER_CATEGORY_NOT_FOUND_MESSAGE).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var workerOrderCategory = workerOrderCategoryOptional.get();
        var workerOrderChannelList = workerOrderCategory.getChannels().stream().toList();
        var workerOrderValidationResult = isOrderExistedInWorkerCategory(
                workerOrderChannelList,
                completedChannelName
        );

        if (workerOrderValidationResult) {
            event.getChannel().sendMessage(
                    "The order with name: {%s} already exists.".formatted(completedChannelName)
            ).queue(
                    message -> message.delete().queueAfter(5, TimeUnit.SECONDS)
            );
            return;
        }

        event.getGuild().createTextChannel(completedChannelName)
                .setParent(workerOrderCategory)
                .queue(
                        channel -> channel.sendMessage(innerMessage.toString()).queue()
                );
    }

    private Optional<Category> getWorkerOrderCategory(Category currentChannelCategory) {
        var workerCategory = currentChannelCategory.getGuild().getCategoryById(
                currentChannelCategory.getId().equalsIgnoreCase(Constants.SELLER_SEARCH_CATEGORY_ID)
                        ? Constants.WORKER_SELLER_SEARCH_CATEGORY_ID
                        : Constants.WORKER_REGULAR_CATEGORY_ID
        );

        if (workerCategory == null) {
            return Optional.empty();
        }

        return Optional.of(workerCategory);
    }

    private boolean isOrderExistedInWorkerCategory(List<GuildChannel> workerOrders,
                                                   String completedChannelName
    ) {
        return workerOrders.stream().anyMatch(channel -> channel.getName().equals(completedChannelName));
    }
}
