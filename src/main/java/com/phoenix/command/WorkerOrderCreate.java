package com.phoenix.command;

import com.phoenix.product.ProductList;
import com.phoenix.util.Constants;
import com.phoenix.util.ValidateHelper;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.phoenix.Main.LOGGER;
import static com.phoenix.Main.getExecutorService;
import static com.phoenix.product.ProductList.getProductBasicInfo;
import static com.phoenix.util.ErrorHandler.sendErrorMessage;

public class WorkerOrderCreate implements Command {
    public static final String COMMAND_NAME = "worder";
    public static final String COMMAND_USAGE = "***Worker Order Create (admin)***\n> - __Usage__: `/worder <item_1> <amount_1> [<item_2> <amount_2> ...]`";
    private static final long COOLDOWN_DURATION = TimeUnit.MINUTES.toMillis(15);
    private static final List<PendingOrder> orderQueue = new ArrayList<>();
    private static String lastOrderCategoryId = "";
    private static long lastCommandExecutedTime = 0;

    private static void createWorkerOrderChannel(MessageReceivedEvent event,
                                                 Category workerOrderCategory,
                                                 String completedChannelName,
                                                 String innerMessage
    ) {
        event.getGuild().createTextChannel(completedChannelName)
                .setParent(workerOrderCategory)
                .flatMap(channel -> channel.sendMessage(innerMessage))
                .queue(
                        message -> event.getChannel().sendMessage(Constants.WORKERS_NOTIFIED_MESSAGE).queue()
                );
    }

    private static synchronized void processPendingOrders() {
        if (!orderQueue.isEmpty()) {
            Collections.sort(orderQueue);
            var elapsedTime = System.currentTimeMillis() - lastCommandExecutedTime;

            if (elapsedTime >= COOLDOWN_DURATION) {
                var pendingOrder = orderQueue.remove(0);
                createWorkerOrderChannel(pendingOrder.event,
                        pendingOrder.workerOrderCategory,
                        pendingOrder.completedChannelName,
                        pendingOrder.innerMessage
                );
                if (requiresCooldown(pendingOrder.channelCategoryId)) {
                    lastCommandExecutedTime = System.currentTimeMillis();
                }
                lastOrderCategoryId = pendingOrder.channelCategoryId;
                if (!pendingOrder.channelCategoryId.equals(Constants.REGULAR_CATEGORY_ID)) {
                    lastOrderCategoryId = pendingOrder.channelCategoryId;
                }
            } else {
                while (!orderQueue.isEmpty()) {
                    var pendingOrder = orderQueue.get(0);
                    if (pendingOrder.channelCategoryId.equals(Constants.REGULAR_CATEGORY_ID)) {
                        break;
                    }
                    if (lastOrderCategoryId.equals(Constants.VIP_REGULAR_CATEGORY_ID) &&
                            pendingOrder.channelCategoryId.equals(Constants.VIP_REGULAR_CATEGORY_ID)) {
                        orderQueue.remove(0);
                        createWorkerOrderChannel(pendingOrder.event,
                                pendingOrder.workerOrderCategory,
                                pendingOrder.completedChannelName,
                                pendingOrder.innerMessage
                        );
                        lastCommandExecutedTime = System.currentTimeMillis();
                        lastOrderCategoryId = pendingOrder.channelCategoryId;
                    }
                }
            }
        }
    }

    private static boolean requiresCooldown(String channelCategoryId) {
        return Constants.VIP_REGULAR_CATEGORY_ID.equals(channelCategoryId) ||
                Constants.VVIP_REGULAR_CATEGORY_ID.equals(channelCategoryId);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (ValidateHelper.validateNotAdminRole(event) ||
                ValidateHelper.isCommandNotExecutedInAllowedCategory(event)) {
            return;
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length < 3 || commandArgs.length % 2 != 1) {
            sendErrorMessage(event.getChannel().asTextChannel(), COMMAND_USAGE);
            return;
        }

        var currentChannel = event.getChannel().asTextChannel();
        var channelCategory = currentChannel.getParentCategory();
        var channelCategoryId = Objects.requireNonNull(channelCategory).getId();

        var commandExecutedChannelName = currentChannel.getName();
        var countTag = commandExecutedChannelName.substring(
                commandExecutedChannelName.lastIndexOf("-") + 1
        );

        var completedChannelName = buildChannelName(commandArgs,
                currentChannel,
                channelCategoryId,
                countTag
        );
        if (completedChannelName.equals(Constants.ERROR_OCCURRED)) {
            return;
        }

        var innerMessage = buildInnerMessage(commandArgs,
                currentChannel,
                channelCategoryId
        );
        if (innerMessage.equals(Constants.ERROR_OCCURRED)) {
            return;
        }

        var workerOrderCategoryOptional = findWorkerOrderCategory(channelCategory);
        if (workerOrderCategoryOptional.isEmpty()) {
            sendErrorMessage(currentChannel, Constants.ERROR_WORKER_CATEGORY_NOT_FOUND_MESSAGE);
            return;
        }

        var workerOrderCategory = workerOrderCategoryOptional.get();
        var workerOrderChannelList = workerOrderCategory.getChannels().stream().toList();

        if (isWorkerOrderExistedInWorkerCategoryOrQueue(
                workerOrderChannelList,
                completedChannelName
        )) {
            sendErrorMessage(
                    currentChannel,
                    Constants.ERROR_WORKER_ORDER_EXISTS_MESSAGE
            );
            return;
        }

        var currentTime = System.currentTimeMillis();
        var isCooldownActive = (currentTime - lastCommandExecutedTime) < COOLDOWN_DURATION;

        if (isCooldownActive && !isVvipWorkerOrder(channelCategoryId)) {
            if (lastOrderCategoryId.equals(Constants.VIP_REGULAR_CATEGORY_ID) &&
                    channelCategoryId.equals(Constants.VIP_REGULAR_CATEGORY_ID)) {
                createWorkerOrderChannel(event,
                        workerOrderCategory,
                        completedChannelName,
                        innerMessage
                );
                lastCommandExecutedTime = currentTime;
                lastOrderCategoryId = channelCategoryId;
            } else {
                orderQueue.add(new PendingOrder(event,
                        channelCategoryId,
                        workerOrderCategory,
                        completedChannelName,
                        innerMessage)
                );
                sendCooldownMessage(currentChannel);
            }
        } else {
            createWorkerOrderChannel(event,
                    workerOrderCategory,
                    completedChannelName,
                    innerMessage
            );
            if (channelCategoryId.equals(Constants.VIP_REGULAR_CATEGORY_ID) ||
                    channelCategoryId.equals(Constants.VVIP_REGULAR_CATEGORY_ID)) {
                lastCommandExecutedTime = currentTime;
            }
            if (!channelCategoryId.equals(Constants.REGULAR_CATEGORY_ID)) {
                lastOrderCategoryId = channelCategoryId;
            }
        }
        getExecutorService().scheduleWithFixedDelay(() -> {
            try {
                processPendingOrders();
            } catch (Exception e) {
                LOGGER.error("Error occurred in processPendingOrders", e);
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    private String buildChannelName(String[] commandArgs,
                                    TextChannel currentChannel,
                                    String channelCategoryId,
                                    String countTag) {
        var channelName = new StringBuilder();

        var isMultipleOrder = (commandArgs.length - 1) / 2 > 1;
        if (isMultipleOrder) {
            channelName.append(Constants.MULTIPLE_ORDER_CHANNEL_PREFIX);
        }

        for (var i = 1; i < commandArgs.length; i += 2) {
            var resourceCode = commandArgs[i];
            int amount;

            try {
                amount = Math.abs(Integer.parseInt(commandArgs[i + 1]));
            } catch (NumberFormatException e) {
                return handleErrorOccurred(currentChannel, Constants.INVALID_AMOUNT_MESSAGE);
            }

            var result = getProductBasicInfo(resourceCode);
            if (result.isEmpty() && !channelCategoryId.equals(Constants.SELLER_SEARCH_CATEGORY_ID)) {
                return handleErrorOccurred(
                        currentChannel,
                        Constants.INVALID_RESOURCE_CODE_MESSAGE
                );
            }

            var resourceName = result.map(ProductList.ProductBasicInfo::resourceName)
                    .orElse(Constants.UNKNOWN);

            channelName.append(amount)
                    .append("-")
                    .append(resourceName.toLowerCase().replace(" ", "-"))
                    .append("-");
        }

        channelName.deleteCharAt(channelName.length() - 1);
        channelName.append("-").append(countTag);

        return channelName.toString();
    }

    private String buildInnerMessage(String[] commandArgs,
                                     TextChannel currentChannel,
                                     String channelCategoryId) {
        var innerMessage = new StringBuilder("# ♡New Work Order♡\n");

        if (channelCategoryId.equals(Constants.SELLER_SEARCH_CATEGORY_ID)) {
            innerMessage.append("*")
                    .append(Constants.SELLER_SEARCH_ORDER_NAME).append("*\n");
        } else {
            innerMessage.append("*")
                    .append(Constants.REGULAR_ORDER_NAME).append("*\n");
        }

        innerMessage.append("```Order(s):\n");

        var isMultipleOrder = (commandArgs.length - 1) / 2 > 1;

        for (var i = 1; i < commandArgs.length; i += 2) {
            var resourceCode = commandArgs[i];
            int amount;

            try {
                amount = Math.abs(Integer.parseInt(commandArgs[i + 1]));
            } catch (NumberFormatException e) {
                return handleErrorOccurred(
                        currentChannel,
                        Constants.INVALID_AMOUNT_MESSAGE
                );
            }

            var result = getProductBasicInfo(resourceCode);
            if (result.isEmpty() && !channelCategoryId.equals(Constants.SELLER_SEARCH_CATEGORY_ID)) {
                return handleErrorOccurred(
                        currentChannel,
                        Constants.INVALID_RESOURCE_CODE_MESSAGE
                );
            }

            var resourceName = result.map(ProductList.ProductBasicInfo::resourceName)
                    .orElseGet(() -> resourceCode.replace("-", " "));

            innerMessage.append(resourceName).append(" - ").append(amount).append("\n");
        }

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

        return innerMessage.toString();
    }

    private String handleErrorOccurred(TextChannel currentChannel, String errorMessage) {
        sendErrorMessage(currentChannel, errorMessage);
        return Constants.ERROR_OCCURRED;
    }

    private boolean isVvipWorkerOrder(String channelCategoryId) {
        return channelCategoryId.equals(Constants.VVIP_REGULAR_CATEGORY_ID);
    }

    private void sendCooldownMessage(TextChannel channel) {
        var timeRemaining = TimeUnit.MILLISECONDS.toSeconds(lastCommandExecutedTime + COOLDOWN_DURATION - System.currentTimeMillis());
        var cooldownMessage = String.format(
                "Worker order is successfully queued (%d seconds remaining).",
                timeRemaining
        );
        sendErrorMessage(channel, cooldownMessage);
    }

    private Optional<Category> findWorkerOrderCategory(Category currentChannelCategory) {
        var workerCategory = currentChannelCategory.getGuild().getCategoryById(
                currentChannelCategory.getId().equals(Constants.SELLER_SEARCH_CATEGORY_ID)
                        ? Constants.WORKER_SELLER_SEARCH_CATEGORY_ID
                        : Constants.WORKER_REGULAR_CATEGORY_ID
        );

        return Optional.ofNullable(workerCategory);
    }

    private boolean isWorkerOrderExistedInWorkerCategoryOrQueue(List<GuildChannel> workerOrders,
                                                                String completedChannelName
    ) {
        return workerOrders.stream().anyMatch(channel -> channel.getName().equals(completedChannelName))
                || orderQueue.stream().anyMatch(pendingOrder -> pendingOrder.completedChannelName.equals(completedChannelName));
    }

    private record PendingOrder(MessageReceivedEvent event,
                                String channelCategoryId,
                                Category workerOrderCategory,
                                String completedChannelName,
                                String innerMessage
    ) implements Comparable<PendingOrder> {
        @Override
        public int compareTo(PendingOrder other) {
            return getCategoryPriority(other.channelCategoryId) - getCategoryPriority(this.channelCategoryId);
        }

        private int getCategoryPriority(String categoryId) {
            return Constants.VVIP_REGULAR_CATEGORY_ID.equals(categoryId) ? 3 :
                    Constants.VIP_REGULAR_CATEGORY_ID.equals(categoryId) ? 2 : 1;
        }
    }
}
