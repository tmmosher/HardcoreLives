package org.hclives.hardcorelives.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import org.hclives.hardcorelives.Hardcorelives;
import net.minecraft.util.Identifier;


public class ModItemsGroup {
    public static final ItemGroup ARTIFACT_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Hardcorelives.MOD_ID, "celerital_scroll"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.artifacts"))
                    .icon(() -> new ItemStack(ModItems.CELERITAL_SCROLL)).entries(((displayContext, entries) -> {
                        entries.add(ModItems.PHOENIX_FLOWER);
                        entries.add(ModItems.ROD_OF_NARSUS);
                        entries.add(ModItems.WOODLAND_HEART);
                        entries.add(ModItems.CHAIN_OF_FATE);
                        entries.add(ModItems.FARRON_DAGGER);
                        entries.add(ModItems.CELERITAL_SCROLL);
                        entries.add(ModItems.ERGO_RUNE);
                    })).build());

    public static void registerItemGroups() {
        Hardcorelives.LOGGER.info("Registering item group for {}", Hardcorelives.MOD_ID);
    }
}
