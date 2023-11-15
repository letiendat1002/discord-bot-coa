package com.bot;

import com.bot.command.CommandHandler;
import com.bot.command.OrderConfirm;
import com.bot.command.OrderReceipt;
import com.bot.command.OrderUpdateStatus;
import com.bot.util.Variables;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;
import java.util.HashSet;

public class Main extends ListenerAdapter {
    private final CommandHandler commandHandler = new CommandHandler();

    public Main() {
//        commandHandler.registerCommand(new InsCalculator());
//        commandHandler.registerCommand(new ChannelRenamer());
        commandHandler.registerCommand(new OrderReceipt());
        commandHandler.registerCommand(new OrderConfirm());
        commandHandler.registerCommand(new OrderUpdateStatus());
    }

    public static void main(String[] args) {
        var jda = JDABuilder
                .createDefault(Variables.TOKEN_PSC)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.addEventListener(new Main());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        var validCommands = new HashSet<>(
                Arrays.asList(
                        "ic", "rc", "receipt", "confirm", "ustatus"
                )
        );
        var messageContent = event.getMessage().getContentRaw();
        var commandPrefix = Variables.COMMAND_PREFIX;

        if (messageContent.startsWith(commandPrefix)) {
            var command = messageContent.substring(commandPrefix.length());
            for (var c : validCommands) {
                if (command.startsWith(c)) {
                    commandHandler.handle(event);
                    break;
                }
            }
        }
    }
}
