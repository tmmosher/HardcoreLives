package org.hclives.hardcorelives.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.hclives.hardcorelives.Hardcorelives;

public class ModItems {
    // increase lives by two
    public static final Item PHOENIX_FLOWER = registerItem("phoenix_flower", new Item(new Item.Settings()));
    // throw and teleport to the location where it lands, after a brief delay
    public static final Item ROD_OF_NARSUS  = registerItem("rod_of_narsus", new Item(new Item.Settings()));
    // every hour, summon a stack of oak wood logs
    public static final Item WOODLAND_HEART = registerItem("woodland_heart", new Item(new Item.Settings()));
    // pulls a player toward you from great distance
    public static final Item CHAIN_OF_FATE = registerItem("chain_of_fate", new Item(new Item.Settings()));
    // chance of parrying an opponent's attack
    public static final Item FARRON_DAGGER = registerItem("farron_dagger", new Item(new Item.Settings()));
    // sunder your lungs with unholy fury, slowing down every player around you and speeding yourself up
    public static final Item CELERITAL_SCROLL = registerItem("celerital_scroll", new Item(new Item.Settings()));
    // freeze time, reducing tick speed to 0 and giving all other players slowness X for two seconds
    public static final Item ERGO_RUNE = registerItem("ergo_rune", new Item(new Item.Settings()));
    // and many more...

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(PHOENIX_FLOWER);
        entries.add(ROD_OF_NARSUS);
        entries.add(WOODLAND_HEART);
        entries.add(CHAIN_OF_FATE);
        entries.add(FARRON_DAGGER);
        entries.add(CELERITAL_SCROLL);
        entries.add(ERGO_RUNE);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM,  Identifier.of(Hardcorelives.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Hardcorelives.LOGGER.info("Registering mod items for {}", Hardcorelives.MOD_ID);
        ModItemsGroup.registerItemGroups();
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
