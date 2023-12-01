package com.bot;

import com.bot.command.*;
import com.bot.util.Constants;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Main extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private final CommandHandler commandHandler = new CommandHandler();

    public Main() {
        commandHandler.registerCommand(new OrderReceipt());
        commandHandler.registerCommand(new OrderConfirm());
        commandHandler.registerCommand(new OrderUpdateStatus());
        commandHandler.registerCommand(new ProductPrintAll());
        commandHandler.registerCommand(new CommandPrintAll());
        commandHandler.registerCommand(new WorkerOrderCreate());
        commandHandler.registerCommand(new OrderPickup());
    }

    public static void main(String[] args) {
        var props = loadProperties();

        if (props == null) {
            LOGGER.error("Failed to load properties");
            return;
        }

        var jda = JDABuilder
                .createDefault(props.getProperty("DISCORD_TOKEN"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.addEventListener(new Main());
    }

    private static Properties loadProperties() {
        try (var input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                return null;
            }
            prop.load(input);
            return prop;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        var validCommands = CommandList.VALID_COMMANDS;
        var messageContent = event.getMessage().getContentRaw();
        var commandPrefix = Constants.COMMAND_PREFIX;

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
