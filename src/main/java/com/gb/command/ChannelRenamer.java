package com.gb.command;

import com.gb.product.ProductList;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.gb.util.CustomNumberFormat.shortenValue;

public class ChannelRenamer implements Command {
    private static final AtomicInteger GLOBAL_COUNTER = new AtomicInteger(0);
    private static final int MAX_TRIES = 2;
    private static final long COOLDOWN_DURATION = TimeUnit.MINUTES.toMillis(10);
    private static final Map<Long, ChannelData> channelDataMap = new ConcurrentHashMap<>();

    @Override
    public String getName() {
        return "rc";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        var commandArgs = event.getMessage().getContentRaw().split(" ");

        if (commandArgs.length != 3) {
            sendAndDelete(event, "Usage: b!rc <resource_code> <amount>");
            return;
        }

        var resourceCode = commandArgs[1];
        var product = ProductList.getProductByCode(resourceCode);

        if (product.isEmpty()) {
            sendAndDelete(event, "Invalid resource code.");
            return;
        }

        var resourceName = product.get().name();
        var unitCost = product.get().cost();

        int amount;
        try {
            amount = Math.abs(Integer.parseInt(commandArgs[2]));
        } catch (NumberFormatException e) {
            sendAndDelete(event, "Invalid quantity. Please provide a valid number.");
            return;
        }

        var suffix = String.format("%05d", GLOBAL_COUNTER.incrementAndGet());
        var newChannelName = String.format("%d-%s-%s-ea-%s",
                amount,
                resourceName,
                shortenValue(unitCost),
                suffix);

        var channelId = event.getChannel().getIdLong();
        var channelData = channelDataMap.computeIfAbsent(channelId, id -> new ChannelData());

        var currentTime = System.currentTimeMillis();

        if (channelData.getTries() == MAX_TRIES) {
            if (currentTime - channelData.getLastAttemptTimestamp() <= COOLDOWN_DURATION) {
                event.getMessage().delete().queue();
                sendCooldownWarning(event, channelData.getLastAttemptTimestamp() + COOLDOWN_DURATION - currentTime);
                return;
            }
            channelData.resetTries();
        }

        event.getMessage().delete().queue();
        var currentChannelID = event.getChannel().getId();
        Objects.requireNonNull(event.getGuild().getTextChannelById(currentChannelID))
                .getManager()
                .setName(newChannelName)
                .queue();

        channelData.update();

        if (channelData.getTries() == MAX_TRIES) {
            sendExceededWarning(event);
        }
    }

    private void sendAndDelete(MessageReceivedEvent event, String message) {
        event.getMessage().delete().queue();
        event.getChannel()
                .sendMessage(message)
                .queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
    }

    private void sendCooldownWarning(MessageReceivedEvent event, long remainingCooldown) {
        event.getChannel()
                .sendMessage("You are on cooldown. Please wait for " + formatCooldownTime(remainingCooldown) + ".")
                .queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
    }

    private void sendExceededWarning(MessageReceivedEvent event) {
        event.getChannel()
                .sendMessage("You have exceeded the command usage limit. Starting 10 minutes cooldown...")
                .queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
    }

    private String formatCooldownTime(long remainingCooldown) {
        var minutes = TimeUnit.MILLISECONDS.toMinutes(remainingCooldown);
        var seconds = TimeUnit.MILLISECONDS.toSeconds(remainingCooldown) - TimeUnit.MINUTES.toSeconds(minutes);
        return minutes + " minutes and " + seconds + " seconds";
    }

    private static class ChannelData {
        private int tries;
        private long lastAttemptTimestamp;

        ChannelData() {
            this.tries = 0;
            this.lastAttemptTimestamp = 0;
        }

        int getTries() {
            return tries;
        }

        long getLastAttemptTimestamp() {
            return lastAttemptTimestamp;
        }

        void resetTries() {
            tries = 0;
        }

        void update() {
            tries++;
            lastAttemptTimestamp = System.currentTimeMillis();
        }
    }
}
