package com.gb;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class InsCalculator implements Command {
    @Override
    public String getName() {
        return "ic";
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        var payArgs = event.getMessage().getContentRaw().split(" ");
        var resourceCode = payArgs[1];
        var resourceName = "";
        int unit = 0;
        int quantity;
        long cost;
        double commission;

        if (payArgs.length < 5) {
            event.getChannel()
                    .sendMessage("Usage: b!ic <resource_code> <quantity> <cost> <commission>")
                    .queue();
            return;
        }

        try {
            quantity = Math.abs(Integer.parseInt(payArgs[2]));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid quantity. Please provide a valid number.").queue();
            return;
        }

        try {
            cost = Math.abs(Long.parseLong(payArgs[3]));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid cost. Please provide a valid number.").queue();
            return;
        }

        try {
            commission = Math.abs(Double.parseDouble(payArgs[4]));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid commission. Please provide a valid number.").queue();
            return;
        }

        var product = ProductList.getProductByCode(resourceCode);
        if (product.isEmpty()) {
            event.getChannel().sendMessage("Invalid resource code.").queue();
            return;
        }
        else {
            resourceName = product.get().name();
            unit = product.get().cost();
        }

        var staticChannel = event.getJDA().getTextChannelById(Variables.INS_CHANNEL_ID);

        if (staticChannel != null) {
            staticChannel
                    .sendMessage(
                            MessageFormat.format(
                                    "Sold *{0} {1}*, cost {2}, commission {3}% (Profit **{4}**)",
                                    new CustomNumberFormat().format(quantity),
                                    resourceName,
                                    shortenValue(cost),
                                    commission,
                                    shortenValue(profitCalc(unit, quantity, cost, commission))
                            )
                    )
                    .queue();
        } else {
            event.getChannel().sendMessage("#ins channel not found.").queue();
            return;
        }

        event.getMessage().delete().queue();
    }

    public String shortenValue(Number value) {
        double numericValue = value.doubleValue();

        if (numericValue >= 1e9) {
            return formatShortenedValue(numericValue / 1e9, "B");
        } else if (numericValue >= 1e6) {
            return formatShortenedValue(numericValue / 1e6, "M");
        } else if (numericValue >= 1e3) {
            return formatShortenedValue(numericValue / 1e3, "K");
        } else {
            return formatShortenedValue(numericValue, "");
        }
    }

    private String formatShortenedValue(Number value, String suffix) {
        var formattedValue = String.format("%.3f", value.doubleValue());

        // Remove trailing zeros from the decimal portion
        formattedValue = formattedValue.replaceAll("\\.0*$", "");

        // If there are more than 1 decimal place remaining, remove the excess
        formattedValue = formattedValue.replaceAll("(\\.\\d*?)0+$", "$1");

        return formattedValue + suffix;
    }

    private double profitCalc(double unit, int quantity, long cost, double commission) {
        return ((unit * quantity) * (1 - (commission / 100))) - cost;
    }

    private static class CustomNumberFormat extends DecimalFormat {
        public CustomNumberFormat() {
            super("###,###");
        }
    }
}

