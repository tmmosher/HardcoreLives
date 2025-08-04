package org.hclives.hardcorelives.item.custom;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.hclives.hardcorelives.sound.ModSounds;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ErgoRuneItem extends Item {

    private static class FrozenTimer implements ServerTickEvents.EndTick {
        public static final FrozenTimer INSTANCE = new FrozenTimer();
        private long ticks;

        public void setTimer(long ticks) {
            this.ticks = ticks;
        }

        @Override
        public void onEndTick(MinecraftServer minecraftServer) {
            if (--ticks == 0L) {
                ServerCommandSource serverCommandSource = minecraftServer.getCommandSource().withLevel(3);
                minecraftServer.getCommandManager().executeWithPrefix(serverCommandSource, "tick unfreeze");
            }
        }
        public static void register() {
            ServerTickEvents.END_SERVER_TICK.register(INSTANCE);
        }
    }

    private final int COOLDOWN = 600;
    private final Map<UUID, CooldownEntry> perPlayerCooldowns = new HashMap<>();
    private final ItemStack selfItemStack = new ItemStack(this);

    public ErgoRuneItem(Settings settings) {
        super(settings);
        FrozenTimer.register();
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && !user.getItemCooldownManager().isCoolingDown(selfItemStack)) {
            MinecraftServer serverWorld = world.getServer();
            if (serverWorld != null) {
                CommandManager commandManager = serverWorld.getCommandManager();
                ServerCommandSource serverCommandSource = serverWorld.getCommandSource().withLevel(3);
                commandManager.executeWithPrefix(serverCommandSource, "tick freeze");
                world.getPlayers().forEach(player ->
                        world.playSound(null,
                                player.getBlockPos(),
                                ModSounds.ERGO_RUNE_PLAYS,
                                SoundCategory.PLAYERS,
                                0.9f,
                                1.0f));
                FrozenTimer.INSTANCE.setTimer(100L);
                user.getItemCooldownManager().set(selfItemStack, COOLDOWN);
            }
            return ActionResult.PASS;
        }
        return ActionResult.PASS;
    }

}
