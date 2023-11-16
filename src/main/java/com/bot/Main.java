package com.bot;

import com.bot.command.*;
import com.bot.util.Variables;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main extends ListenerAdapter {
    private final CommandHandler commandHandler = new CommandHandler();

    public Main() {
//        commandHandler.registerCommand(new InsCalculator());
//        commandHandler.registerCommand(new ChannelRenamer());
        commandHandler.registerCommand(new OrderReceipt());
        commandHandler.registerCommand(new OrderConfirm());
        commandHandler.registerCommand(new OrderUpdateStatus());
        commandHandler.registerCommand(new ProductPrintAll());
        commandHandler.registerCommand(new CommandPrintAll());
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
        var validCommands = CommandList.validCommands;
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