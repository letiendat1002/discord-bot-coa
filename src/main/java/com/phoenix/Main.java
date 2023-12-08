package com.phoenix;

import com.phoenix.command.*;
import com.phoenix.user.User;
import com.phoenix.user.UserDAO;
import com.phoenix.user.UserDaoImpl;
import com.phoenix.util.Constants;
import com.phoenix.util.PropertyLoader;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends ListenerAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private final CommandHandler commandHandler = new CommandHandler();
    private final UserDAO userDAO = new UserDaoImpl();

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
        PropertyLoader.loadProperties().ifPresentOrElse(props -> {
            var jda = JDABuilder
                    .createDefault(props.getProperty("DISCORD_TOKEN"))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build();
            jda.addEventListener(new Main());
        }, () -> LOGGER.error("Failed to load properties"));
    }

    public static ScheduledExecutorService getExecutorService() {
        return executorService;
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

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        var member = event.getMember();
        var user = new User(
                BigInteger.valueOf(member.getIdLong()),
                BigInteger.ZERO,
                BigInteger.ZERO,
                0
        );

        userDAO.insertUser(user);
    }
}
