package org.hclives.hardcorelives.item.custom;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.hclives.hardcorelives.sound.ModSounds;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrickstersHuiluItem extends Item {

    private static final int ONE_HOUR_IN_SECONDS = 3600;
    private final Map<UUID, CooldownEntry> perPlayerCooldowns = new HashMap<>();
    private final ItemStack selfItemStack = new ItemStack(this);

    public TrickstersHuiluItem(Settings settings) {
        super(settings);
    }

    public void addTnt(PlayerEntity user) {
        boolean inserted = user.getInventory().insertStack(new ItemStack(Items.TNT, 8));
        if (!inserted) {
            ItemEntity droppedIfFull = new ItemEntity(user.getWorld(),
                    user.getX(),
                    user.getY()+0.5,
                    user.getZ(),
                    new ItemStack(Items.TNT, 8));
            user.getWorld().spawnEntity(droppedIfFull);
        }
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && !user.getItemCooldownManager().isCoolingDown(selfItemStack)) {
            CooldownEntry cooldowns = perPlayerCooldowns.computeIfAbsent(user.getUuid(), k -> new CooldownEntry());
            if (Instant.now().minusSeconds(cooldowns.getLastUsedTime().getEpochSecond()).getEpochSecond()
                    >= ONE_HOUR_IN_SECONDS) {
                addTnt(user);
                cooldowns.setLastUsedTime(Instant.now());
                cooldowns.setNextUseTime(cooldowns.getLastUsedTime().plusSeconds(ONE_HOUR_IN_SECONDS));
                float MAX_PITCH = 1.1f;
                float MIN_PITCH = 0.9f;
                float randomPitch = (MIN_PITCH + (MAX_PITCH - MIN_PITCH) * user.getRandom().nextFloat());
                world.playSound(null, user.getBlockPos(), ModSounds.TRICKSTERS_HUILU_FLUTE_PLAYS,
                        SoundCategory.PLAYERS, 1f, randomPitch);
                user.getItemCooldownManager().set(selfItemStack, 90);
                return ActionResult.SUCCESS;
            } else {
                int gapMinutes = Math.toIntExact(cooldowns.getNextUseTime().minusSeconds(Instant.now().getEpochSecond()).getEpochSecond() / 60);
                world.playSound(null, user.getBlockPos(), ModSounds.TRICKSTERS_HUILU_FLUTE_FAILS,
                        SoundCategory.PLAYERS, 1f, 1f);
                user.getItemCooldownManager().set(selfItemStack, 40);
                user.sendMessage(Text.of("§l§eThe flute plays no notes, no matter how hard you try... §r" + gapMinutes + " §l§eminutes remain."), false);
                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }
}
