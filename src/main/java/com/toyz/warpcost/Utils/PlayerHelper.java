package com.toyz.warpcost.Utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Travis on 10/23/2014.
 */
public class PlayerHelper {
    public static String MessageBuilder(String message, String name, ConfigurationSection cs){
        message = message.replace("%cost%", cs.getInt("cost") + "").replace("%name%", name).replace("%item%", StringUtils.capitalize(cs.getString("item").replace("_", " ")));
        return message;
    }
}
