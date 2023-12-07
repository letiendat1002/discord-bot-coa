package com.phoenix.util;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.util.concurrent.TimeUnit;

public class ErrorHandler {
    public static void sendErrorMessage(TextChannel channel, String errorMessage) {
        channel.sendMessage(errorMessage)
                .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
    }

    public static void sendErrorMessage(MessageChannelUnion channel, String errorMessage) {
        channel.sendMessage(errorMessage)
                .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
    }
}
