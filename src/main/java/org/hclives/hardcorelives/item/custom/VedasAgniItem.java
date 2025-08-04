package org.hclives.hardcorelives.item.custom;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
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

public class VedasAgniItem extends Item {

    private static final int THIRTY_MINUTES_IN_SECONDS = 1800;
    private final Map<UUID, CooldownEntry> perPlayerCooldowns = new HashMap<>();
    private final ItemStack selfItemStack = new ItemStack(this);

    public VedasAgniItem(Settings settings) {
        super(settings);
    }

    public void addPowder(PlayerEntity user) {
        boolean inserted = user.getInventory().insertStack(new ItemStack(Items.GUNPOWDER, 16));
        if (!inserted) {
            user.sendMessage(Text.of("Item not inserted."), false);
            ItemEntity droppedIfFull = new ItemEntity(user.getWorld(),
                    user.getX(),
                    user.getY()+0.5,
                    user.getZ(),
                    new ItemStack(Items.GUNPOWDER, 16));
            user.getWorld().spawnEntity(droppedIfFull);
        }
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            CooldownEntry cooldowns = perPlayerCooldowns.computeIfAbsent(user.getUuid(), k -> new CooldownEntry());
            if (Instant.now().minusSeconds(cooldowns.getLastUsedTime().getEpochSecond()).getEpochSecond()
                    >= THIRTY_MINUTES_IN_SECONDS) {
                addPowder(user);
                cooldowns.setLastUsedTime(Instant.now());
                cooldowns.setNextUseTime(cooldowns.getLastUsedTime().plusSeconds(THIRTY_MINUTES_IN_SECONDS));
                world.playSound(null, user.getBlockPos(), ModSounds.VEDAS_AGNI_PLAYS,
                        SoundCategory.PLAYERS, 0.75f, 1.0f);
                user.getItemCooldownManager().set(selfItemStack, 50);
                return ActionResult.SUCCESS;
            } else {
                int gapMinutes = Math.toIntExact(cooldowns.getNextUseTime().minusSeconds(Instant.now().getEpochSecond()).getEpochSecond() / 60);
                user.getItemCooldownManager().set(selfItemStack, 40);
                user.sendMessage(Text.of("§l§cPowder stirs in the bottle... §r" + gapMinutes + " §l§cminutes remain."), false);
                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }
}
