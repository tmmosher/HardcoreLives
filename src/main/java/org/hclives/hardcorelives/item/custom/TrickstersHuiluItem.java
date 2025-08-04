package org.hclives.hardcorelives.item.custom;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.time.Instant;
import java.util.function.Consumer;

public class TrickstersHuiluItem extends Item {

    private Instant lastUsed = Instant.EPOCH;
    private Instant oneHourInFuture = null;

    public TrickstersHuiluItem(Settings settings) {
        super(settings);
    }

    public void addLogs(PlayerEntity user) {
        user.getInventory().insertStack(new ItemStack(Items.TNT, 8));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            long timeGap = Instant.now().minusSeconds(lastUsed.getEpochSecond()).getEpochSecond();
            if (timeGap >= 3600) {
                addLogs(user);
                lastUsed = Instant.now();
                oneHourInFuture = lastUsed.plusSeconds(3600);
                return ActionResult.SUCCESS;
            } else {
                int gapMinutes = Math.toIntExact(oneHourInFuture.minusSeconds(Instant.now().getEpochSecond()).getEpochSecond() / 60);
                user.sendMessage(Text.of("§l§eThe flute plays no notes, no matter how hard you try... §r" + gapMinutes + " §l§eminutes remain."), false);
                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }
}
