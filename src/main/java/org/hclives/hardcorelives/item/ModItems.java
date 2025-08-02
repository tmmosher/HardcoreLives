package org.hclives.hardcorelives.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.hclives.hardcorelives.Hardcorelives;

public class ModItems {
    // increase lives by two
    public static final Item PHOENIX_FLOWER = registerItem("phoenix_flower");
    // throw and teleport to the location where it lands, after a brief delay
    public static final Item ROD_OF_NARSUS  = registerItem("rod_of_narsus");
    // every hour, summon a stack of oak wood logs
    public static final Item WOODLAND_HEART = registerItem("woodland_heart");
    // pulls a player toward you from great distance
    public static final Item CHAIN_OF_FATE = registerItem("chain_of_fate");
    // chance of parrying an opponent's attack
    public static final Item FARRON_DAGGER = registerItem("farron_dagger");
    // sunder your lungs with unholy fury, slowing down every player around you and speeding yourself up
    public static final Item CELERITAL_SCROLL = registerItem("celerital_scroll");
    // freeze time, reducing tick speed to 0 and giving all other players slowness X for two seconds
    public static final Item ERGO_RUNE = registerItem("ergo_rune");
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

    private static Item registerItem(String name) {
        // may need to refactor the 'settings' handling as per https://fabricmc.net/2024/10/14/1212.html
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Hardcorelives.MOD_ID, name));
        Item item = new Item(new Item.Settings().registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems() {
        Hardcorelives.LOGGER.info("Registering mod items for {}", Hardcorelives.MOD_ID);
        ModItemsGroup.registerItemGroups();
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
