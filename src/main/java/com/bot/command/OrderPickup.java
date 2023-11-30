package com.bot.command;

import com.bot.util.Constants;
import com.bot.util.ValidateHelper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class OrderPickup implements Command {
    public static final String COMMAND_USAGE = "***Order Pickup***\n> - __Usage__: `/opickup <@user>`";

    @Override
    public String getName() {
        return "opickup";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        if (ValidateHelper.validateNotAdminRole(event)) {
            return;
        }

        var commandArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (commandArgs.length != 2) {
            event.getMessage().delete().queue();
            event.getChannel()
                    .sendMessage(COMMAND_USAGE)
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        if (ValidateHelper.isCommandNotExecutedInAllowedCategory(event)) {
            return;
        }
        var mention = commandArgs[1];

        var pickupMessage = "# ♡<:gift:1178648532039778395>Ready For PickUp<:gift:1178648532039778395>♡\n" +
                "Good day " + mention + ", your order is now **ready for pick up**! " +
                "Kindly `ping` " + Constants.BANKER_PING + " together with `your in-game name(IGN)` once you are ready to pick up your order. " +
                "Thank you for your support in **Phoenix Center** and have a nice day! <:pepe_heart:1176230584775884800>";

        event.getChannel().sendMessage(pickupMessage).queue();

    }
}
