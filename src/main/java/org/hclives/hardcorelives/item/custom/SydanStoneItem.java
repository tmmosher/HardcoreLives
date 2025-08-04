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

public class SydanStoneItem extends Item {

    private Instant lastUsed = Instant.EPOCH;
    private Instant thirtyMinInFuture = null;

    public SydanStoneItem(Settings settings) {
        super(settings);
    }

    public void addLogs(PlayerEntity user) {
        user.getInventory().insertStack(new ItemStack(Items.COOKED_BEEF, 16));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            long timeGap = Instant.now().minusSeconds(lastUsed.getEpochSecond()).getEpochSecond();
            if (timeGap >= 1800) {
                addLogs(user);
                lastUsed = Instant.now();
                thirtyMinInFuture = lastUsed.plusSeconds(1800);
                return ActionResult.SUCCESS;
            } else {
                int gapMinutes = Math.toIntExact(thirtyMinInFuture.minusSeconds(Instant.now().getEpochSecond()).getEpochSecond() / 30);
                user.sendMessage(Text.of("§l§mYou feel the guilt subside... §r" + gapMinutes + " §l§1minutes remain."), false);
                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }
}
