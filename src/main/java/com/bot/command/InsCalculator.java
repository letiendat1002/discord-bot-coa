package com.bot.command;

import com.bot.product.ProductList;
import com.bot.util.CustomNumberFormat;
import com.bot.util.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static com.bot.util.CustomNumberFormat.shortenValue;

public class InsCalculator implements Command {
    @Override
    public String getName() {
        return "ic";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        var payArgs = event.getMessage().getContentRaw().split(" ");

        event.getMessage().delete().queue();
        if (payArgs.length != 5) {
            event.getChannel()
                    .sendMessage("Usage: b!ic <resource_code> <amount> <cost> <commission>")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        var resourceCode = payArgs[1];
        var resourceName = "";
        int unit;
        int amount;
        long cost;
        double commission;

        var product = ProductList.getProductByCode(resourceCode);
        if (product.isEmpty()) {
            event.getChannel().sendMessage("Invalid resource code.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        } else {
            resourceName = product.get().name();
            unit = product.get().cost();
        }

        try {
            amount = Math.abs(Integer.parseInt(payArgs[2]));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid quantity. Please provide a valid number.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        try {
            cost = Math.abs(Long.parseLong(payArgs[3]));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid cost. Please provide a valid number.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        try {
            commission = Math.abs(Double.parseDouble(payArgs[4]));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid commission. Please provide a valid number.")
                    .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }


        var staticChannel = event.getJDA().getTextChannelById(Variables.INS_CHANNEL_ID);

        if (staticChannel != null) {
            staticChannel
                    .sendMessage(
                            MessageFormat.format(
                                    "Sold *{0} {1}*, cost {2}, commission {3}% (Profit **{4}**)",
                                    new CustomNumberFormat().format(amount),
                                    resourceName,
                                    shortenValue(cost),
                                    commission,
                                    shortenValue(profitCalc(unit, amount, cost, commission))
                            )
                    )
                    .queue();
        } else {
            event.getChannel().sendMessage("#ins channel not found.").queue();
        }

    }

    private double profitCalc(double unit, int quantity, long cost, double commission) {
        return ((unit * quantity) * (1 - (commission / 100))) - cost;
    }
}
