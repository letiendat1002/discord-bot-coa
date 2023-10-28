package com.gb;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class MyBot extends ListenerAdapter {
    private final CommandHandler commandHandler = new CommandHandler();

    public MyBot() {
        commandHandler.registerCommand(new InsCalculator());
    }

    public static void main(String[] args) {
        var jda = JDABuilder
                .createDefault(Variables.TOKEN)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.addEventListener(new MyBot());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage()
                .getContentRaw()
                .equals(Variables.COMMAND_PREFIX + "ping")
        ) {
            event.getChannel().sendMessage("pong").queue();
        }

        if (event.getMessage()
                .getContentRaw()
                .contains(Variables.COMMAND_PREFIX + "ic")
        ) {
            commandHandler.handle(event);
        }
    }
}
