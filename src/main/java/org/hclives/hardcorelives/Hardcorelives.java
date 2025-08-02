package org.hclives.hardcorelives;

import net.fabricmc.api.ModInitializer;
import org.hclives.hardcorelives.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hardcorelives implements ModInitializer {
    public static String MOD_ID = "hardcorelives";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        ModItems.registerModItems();
    }
}
