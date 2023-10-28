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
        double commission;

        if (payArgs.length < 4) {
            event.getChannel()
                    .sendMessage("Usage: b!ic <resource_code> <quantity> <commission>")
                    .queue();
            return;
        }

        try {
            quantity = Integer.parseInt(payArgs[2]);
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid quantity. Please provide a valid number.").queue();
            return;
        }

        try {
            commission = Double.parseDouble(payArgs[3]);
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Invalid commission. Please provide a valid number.").queue();
            return;
        }

        switch (resourceCode) {
            case "coal" -> {
                resourceName = "Coal";
                unit = 3000;
            }
            case "sio" -> {
                resourceName = "Silver Ore";
                unit = 4000;
            }
            case "goo" -> {
                resourceName = "Gold Ore";
                unit = 4000;
            }
            case "myo" -> {
                resourceName = "Mythan Ore";
                unit = 7000;
            }
            case "coo" -> {
                resourceName = "Cobalt Ore";
                unit = 10_000;
            }
            case "vao" -> {
                resourceName = "Varaxium Ore";
                unit = 14_000;
            }
            case "mao" -> {
                resourceName = "Magic Ore";
                unit = 5000;
            }
            case "salt" -> {
                resourceName = "Salt";
                unit = 3000;
            }
            case "psalt" -> {
                resourceName = "Pink Salt";
                unit = 8000;
            }
            case "bsalt" -> {
                resourceName = "Black Salt";
                unit = 10_000;
            }
            case "sib" -> {
                resourceName = "Silver Bars";
                unit = 20_000;
            }
            case "gob" -> {
                resourceName = "Gold Bars";
                unit = 1_000_000;
            }
            case "myb" -> {
                resourceName = "Mythan Bars";
                unit = 90_000;
            }
            case "cob" -> {
                resourceName = "Cobalt Bars";
                unit = 100_000;
            }
            case "vab" -> {
                resourceName = "Varaxium Bars";
                unit = 140_000;
            }
            case "mab" -> {
                resourceName = "Magic Bars";
                unit = 60_000;
            }
            case "ss" -> {
                resourceName = "Sandstones";
                unit = 10_000;
            }
            case "mag" -> {
                resourceName = "Magnetite";
                unit = 150_000;
            }
            case "mac" -> {
                resourceName = "Magnetites";
                unit = 400_000;
            }
            case "acr" -> {
                resourceName = "Accuracy Relics";
                unit = 3000;
            }
            case "gur" -> {
                resourceName = "Guardian Relics";
                unit = 4000;
            }
            case "her" -> {
                resourceName = "Healing Relics";
                unit = 4000;
            }
            case "wer" -> {
                resourceName = "Wealth Relics";
                unit = 4000;
            }
            case "por" -> {
                resourceName = "Power Relics";
                unit = 6000;
            }
            case "nar" -> {
                resourceName = "Nature Relics";
                unit = 6000;
            }
            case "fir" -> {
                resourceName = "Fire Relics";
                unit = 8000;
            }
            case "dar" -> {
                resourceName = "Damage Relics";
                unit = 8000;
            }
            case "ler" -> {
                resourceName = "Leeching Relics";
                unit = 6000;
            }
            case "icr" -> {
                resourceName = "Ice Relics";
                unit = 6000;
            }
            case "exr" -> {
                resourceName = "Experience Relics";
                unit = 12_000;
            }
            case "cur" -> {
                resourceName = "Cursed Relics";
                unit = 4000;
            }
            case "book" -> {
                resourceName = "Books";
                unit = 4000;
            }
            case "mess" -> {
                resourceName = "Magical Essence";
                unit = 1000;
            }
        }

        event.getChannel()
                .sendMessage(
                        MessageFormat.format(
                                "Sold *{0} {1}*, commission {2}% (Profit **{3}**)",
                                new CustomNumberFormat().format(quantity),
                                resourceName,
                                commission,
                                profitCalc(unit, quantity, commission)
                        )
                )
                .queue();

        event.getMessage().delete().queue();
    }

    private double profitCalc(double unit, int quantity, double commission) {
        return (unit * quantity) * (1 - (commission / 100));
    }
}

class CustomNumberFormat extends DecimalFormat {
    public CustomNumberFormat() {
        super("###,###");
    }
}
