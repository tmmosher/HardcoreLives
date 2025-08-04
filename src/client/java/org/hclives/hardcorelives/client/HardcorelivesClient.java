package org.hclives.hardcorelives.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import org.hclives.hardcorelives.item.ModItems;

public class HardcorelivesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.isOf(ModItems.TRICKSTERS_HUILU)) {
                list.add(Text.translatable("tooltip.hardcorelives.tricksters.huilu.1"));
                list.add(Text.translatable("tooltip.hardcorelives.tricksters.huilu.2"));
                list.add(Text.translatable("tooltip.hardcorelives.tricksters.huilu.3"));
                list.add(Text.translatable("tooltip.hardcorelives.tricksters.huilu.4"));
                return;
            }
        });
    }
}
