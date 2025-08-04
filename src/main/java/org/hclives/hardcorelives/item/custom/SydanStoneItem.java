package org.hclives.hardcorelives.item.custom;

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

public class SydanStoneItem extends Item {

    private static final int THIRTY_MINUTES_IN_SECONDS = 1800;
    private final Map<UUID, CooldownEntry> perPlayerCooldowns = new HashMap<>();
    private final ItemStack selfItemStack = new ItemStack(this);

    public SydanStoneItem(Settings settings) {
        super(settings);
    }

    public void addBeef(PlayerEntity user) {
        user.getInventory().insertStack(new ItemStack(Items.COOKED_BEEF, 16));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && !user.getItemCooldownManager().isCoolingDown(selfItemStack)) {
            CooldownEntry cooldowns = perPlayerCooldowns.computeIfAbsent(user.getUuid(), k -> new CooldownEntry());
            if (Instant.now().minusSeconds(cooldowns.getLastUsedTime().getEpochSecond()).getEpochSecond()
                    >= THIRTY_MINUTES_IN_SECONDS) {
                addBeef(user);
                cooldowns.setLastUsedTime(Instant.now());
                cooldowns.setNextUseTime(cooldowns.getLastUsedTime().plusSeconds(THIRTY_MINUTES_IN_SECONDS));
                world.playSound(null, user.getBlockPos(), ModSounds.SYDAN_STONE_PLAYS,
                        SoundCategory.PLAYERS, .75f, 0.65f);
                user.getItemCooldownManager().set(selfItemStack, 50);
                return ActionResult.SUCCESS;
            } else {
                int gapMinutes = Math.toIntExact(cooldowns.getNextUseTime().minusSeconds(Instant.now().getEpochSecond()).getEpochSecond() / 60);
                user.getItemCooldownManager().set(selfItemStack, 40);
                user.sendMessage(Text.of("§l§3You feel the guilt subside... §r" + gapMinutes + " §l§3minutes remain."), false);
                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }
}
