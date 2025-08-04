package org.hclives.hardcorelives.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.time.Instant;

public class WoodlandHeartItem extends Item {

    private Instant lastUsed = Instant.EPOCH;
    private Instant oneHourInFuture = null;

    public WoodlandHeartItem(Settings settings) {
        super(settings);
    }

    public void addLogs(PlayerEntity user) {
        user.getInventory().insertStack(new ItemStack(Items.OAK_LOG, 64));
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
                user.sendMessage(Text.of("§l§6The Woodland Heart rejects you... §r" + gapMinutes + " §l§6minutes remain."), false);
                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }
}
